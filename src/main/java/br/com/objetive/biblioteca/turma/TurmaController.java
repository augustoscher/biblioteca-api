package br.com.objetive.biblioteca.turma;

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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("v1/turmas")
public class TurmaController {

	@Autowired
    private TurmaRepository repository;

    @GetMapping
    @ApiOperation(value = "Retorna uma lista com todos as turmas", response = Turma[].class)
    public ResponseEntity<?> get() {
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }

    @GetMapping(path = "/{uuid}")	
    @ApiOperation(value = "Retorna uma turma filtrando por id", response = Turma.class)
    public ResponseEntity<?> getPorUuId(@PathVariable("uuid") String uuid, Authentication auth) {
        //      Sstem.out.println(auth.getPrincipal()); assim pega o usuário que vem na requisição.
        Optional<Turma> turma = this.repository.findById(uuid);
        return new ResponseEntity<>(turma, HttpStatus.OK);
    }

    @GetMapping(path = "/porNome/{nome}")
    @ApiOperation(value = "Retorna uma turma filtrando por nome", response = Turma.class)
    public ResponseEntity<?> getPorNome(@PathVariable String nome) {
        return new ResponseEntity<>(this.repository.findByNomeContainingIgnoreCase(nome), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{uuid}")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Exclui uma turma através do id. Permitido somente usuários com privilégio 'Admin'")
    public ResponseEntity<?> delete(@PathVariable String uuid) {
        this.repository.deleteById(uuid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping
    @Transactional(rollbackFor = Exception.class)
    @ApiOperation(value = "Cria uma nova turma.", response = Turma.class)
    public ResponseEntity<?> save(@Valid @RequestBody Turma turma, Authentication auth) {
        turma.setUserLastUpdate((((User) auth.getPrincipal()).getUsername()));
        if (turma.getCreatedAt() == null) {
            turma.setCreatedAt(new Date());
        }
        return new ResponseEntity<>(this.repository.save(turma), HttpStatus.CREATED);
    }

    @PutMapping
    @ApiOperation(value = "Atualiza uma Turma.", response = Turma.class)
    public ResponseEntity<?> update(@RequestBody Turma turma, Authentication auth) {
        turma.setUserLastUpdate((((User) auth.getPrincipal()).getUsername()));
        if (turma.getUpdatedAt() == null) {
            turma.setUpdatedAt(new Date());
        }
        return new ResponseEntity<>(this.repository.save(turma), HttpStatus.OK);
    }
}
