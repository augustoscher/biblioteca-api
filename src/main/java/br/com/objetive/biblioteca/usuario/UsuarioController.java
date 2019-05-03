package br.com.objetive.biblioteca.usuario;

import java.util.Date;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.objetive.biblioteca.multitenant.TenantContext;
import br.com.objetive.biblioteca.utils.PasswordEncoder;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("v1/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;

    @GetMapping
    @ApiOperation(value = "Retorna uma lista com todos os usuarios", response = Usuario[].class)
    public ResponseEntity<?> get() {
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }

    @GetMapping(path = "/{uuid}")
    @ApiOperation(value = "Retorna um usuario filtrando por id", response = Usuario.class)
    public ResponseEntity<?> getPorUuId(@PathVariable("uuid") String uuid, Authentication auth) {
        Optional<Usuario> usuario = this.repository.findById(uuid);
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    @GetMapping(path = "/porLogin/{login}")
    @ApiOperation(value = "Retorna um usuario filtrando por login", response = Usuario.class)
    public ResponseEntity<?> getPorLogin(@PathVariable String login) {
        return new ResponseEntity<>(this.repository.findByLogin(login), HttpStatus.OK);
    }
    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional(rollbackFor = Exception.class)
    @ApiOperation(value = "Cria um novo usuario.", response = Usuario.class)
    public ResponseEntity<?> save(@Valid @RequestBody Usuario usuario, Authentication auth) {
        Usuario user = this.repository.findByLogin((((User) auth.getPrincipal()).getUsername()));
       
        usuario.setUserLastUpdate(user.getLogin());
        usuario.setTenantId(user.getTenantId());
        usuario.setSenha(PasswordEncoder.encode(usuario.getSenha()));
        if (usuario.getCreatedAt() == null) {
            usuario.setCreatedAt(new Date());
        }
        TenantContext.setTenantSchema(null);//seta o tenant schema para cair no schema public 
		return new ResponseEntity<>(this.repository.save(usuario), HttpStatus.CREATED);
    }
}
