package br.com.objetive.biblioteca.tenant;

import java.util.Date;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.objetive.biblioteca.usuario.Usuario;
import br.com.objetive.biblioteca.usuario.UsuarioRepository;

@RestController
@RequestMapping(value = "v1/tenants")
public class TenantController {

    private TenantRepository repository;
    private DataSource dataSource;

    @Autowired
    private UsuarioRepository usuarioRepo;

    public TenantController(TenantRepository repository, DataSource dataSource) {
        this.repository = repository;
        this.dataSource = dataSource;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public Tenant createTenant(@RequestBody Tenant tenant, Authentication auth) {
        tenant.setUserLastUpdate((((User) auth.getPrincipal()).getUsername()));
        tenant = repository.save(tenant);
        String schema = tenant.getSchemaName();
        if (schema == null) throw new RuntimeException("schema is null");
        Flyway flyway = new Flyway();
        flyway.setLocations("db/migration/tenants");
        flyway.setDataSource(dataSource);
        flyway.setSchemas(schema);
        flyway.migrate();
        criarUsuarioAdministrador(tenant);
        return tenant;
    }

    private void criarUsuarioAdministrador(Tenant tenant) {
        //        if (DeveloperUtils.isDeveloperMode()) {
            Usuario user = new Usuario();
            user.setLogin("admin-" + tenant.getTenantName());
            user.setNome("Administrador");
            user.setAdmin(true);
            Date date = new Date();
            user.setCreatedAt(date);
            user.setUpdatedAt(date);
            user.setSenha("$2a$10$JiHT.l5j/UbpGnNNKJhV4uvOSeCXD8abwOq3aJrBME1ovcpiNkYX.");
            user.setTenantId(tenant.getUuid());
            usuarioRepo.save(user);
        //        }
    }

    @DeleteMapping("/{uuid}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTenant(@RequestParam String uuid) {
        repository.deleteById(uuid);
    }

    @GetMapping
    public Page<Tenant> getTenants(Pageable pageable) {
        return repository.findAll(pageable);
    }

}