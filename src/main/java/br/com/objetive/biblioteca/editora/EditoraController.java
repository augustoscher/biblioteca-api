package br.com.objetive.biblioteca.editora;

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
@RequestMapping("v1/editoras")
public class EditoraController {

    @Autowired
    private EditoraRepository repository;

    @GetMapping
    @ApiOperation(value = "Retorna uma lista com todos as editoras de maneira paginada", response = Editora[].class)
    public ResponseEntity<?> get(Pageable pageable) {
        return new ResponseEntity<>(repository.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping(path = "/{uuid}")
    @ApiOperation(value = "Retorna uma editora filtrando por id", response = Editora.class)
    public ResponseEntity<?> getPorUuId(@PathVariable("uuid") String uuid, Authentication auth) {
        //      Sstem.out.println(auth.getPrincipal()); assim pega o usuário que vem na requisição.
        Optional<Editora> editora = this.repository.findById(uuid);
        return new ResponseEntity<>(editora, HttpStatus.OK);
    }

    @GetMapping(path = "/porNome/{nome}")
    @ApiOperation(value = "Retorna uma editora filtrando por nome", response = Editora.class)
    public ResponseEntity<?> getPorNome(@PathVariable String nome) {
        return new ResponseEntity<>(this.repository.findByNomeContainingIgnoreCase(nome), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{uuid}")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Exclui uma editora através do id. Permitido somente usuários com privilégio 'Admin'")
    public ResponseEntity<?> delete(@PathVariable String uuid) {
        this.repository.deleteById(uuid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping
    @Transactional(rollbackFor = Exception.class)
    @ApiOperation(value = "Cria uma nova Editora.", response = Editora.class)
    public ResponseEntity<?> save(@Valid @RequestBody Editora editora, Authentication auth) {
        editora.setUserLastUpdate((((User) auth.getPrincipal()).getUsername()));
        if (editora.getCreatedAt() == null) {
            editora.setCreatedAt(new Date());
        }
        return new ResponseEntity<>(this.repository.save(editora), HttpStatus.CREATED);
    }

    @PutMapping
    @Transactional(rollbackFor = Exception.class)
    @ApiOperation(value = "Atualiza uma Editora.", response = Editora.class)
    public ResponseEntity<?> update(@RequestBody Editora editora, Authentication auth) {
        editora.setUserLastUpdate((((User) auth.getPrincipal()).getUsername()));
        if (editora.getUpdatedAt() == null) {
            editora.setUpdatedAt(new Date());
        }
        return new ResponseEntity<>(this.repository.save(editora), HttpStatus.OK);
    }
}
