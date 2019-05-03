package br.com.objetive.biblioteca.autor;

import java.util.Date;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
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
@RequestMapping("v1/autores")
public class AutorController {

    @Autowired
    private AutorRepository repository;

    @GetMapping
    @ApiOperation(value = "Retorna uma lista com todos os autores de maneira paginada", response = Autor[].class)
    public ResponseEntity<?> get(Pageable pageable) {
        return new ResponseEntity<>(repository.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping(path = "/{uuid}")
    @ApiOperation(value = "Retorna um autor filtrando por id", response = Autor.class)
    public ResponseEntity<?> getPorUuId(@PathVariable("uuid") String uuid, Authentication auth) {
        //      Sstem.out.println(auth.getPrincipal()); assim pega o usuário que vem na requisição.
        Optional<Autor> autor = this.repository.findById(uuid);
        return new ResponseEntity<>(autor, HttpStatus.OK);
    }

    @GetMapping(path = "/porNome/{nome}")
    @ApiOperation(value = "Retorna um autor filtrando por nome", response = Autor.class)
    public ResponseEntity<?> getPorNome(@PathVariable String nome) {
        return new ResponseEntity<>(this.repository.findByNomeContainingIgnoreCase(nome), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{uuid}")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Exclui um autor através do id. Permitido somente usuários com privilégio 'Admin'")
    public ResponseEntity<?> delete(@PathVariable String uuid) {
        this.repository.deleteById(uuid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping
    @Transactional(rollbackFor = Exception.class)
    @ApiOperation(value = "Cria um novo Autor.", response = Autor.class)
    public ResponseEntity<?> save(@Valid @RequestBody Autor autor, Authentication auth) {
        autor.setUserLastUpdate((((User) auth.getPrincipal()).getUsername()));
        if (autor.getCreatedAt() == null) {
            autor.setCreatedAt(new Date());
        }
        return new ResponseEntity<>(this.repository.save(autor), HttpStatus.CREATED);
    }

    @PutMapping
    @ApiOperation(value = "Atualiza um Autor.", response = Autor.class)
    public ResponseEntity<?> update(@RequestBody Autor autor, Authentication auth) {
        autor.setUserLastUpdate((((User) auth.getPrincipal()).getUsername()));
        if (autor.getUpdatedAt() == null) {
            autor.setUpdatedAt(new Date());
        }
        return new ResponseEntity<>(this.repository.save(autor), HttpStatus.OK);
    }

}
