package br.com.objetive.biblioteca.pessoa;

import java.util.Date;
import java.util.List;
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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
@RequestMapping("v1/pessoas")
//v1 serve para versionarmos os endpoints
public class PessoaController {

    @Autowired
    private PessoaRepository repository;
    
    @GetMapping
    @ApiOperation(value = "Retorna uma lista com todos as pessoas de maneira paginada", response = Pessoa[].class)
    public ResponseEntity<?> get(Pageable pageable) {
		return new ResponseEntity<>(repository.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping(path = "/{uuid}")
    @ApiOperation(value = "Retorna uma pessoa filtrando por id", response = Pessoa.class)
    public ResponseEntity<?> getPorUuId(@PathVariable("uuid") String uuid, Authentication auth) {
//    	Sstem.out.println(auth.getPrincipal()); assim pega o usuário que vem na requisição.
		Optional<Pessoa> pessoa = this.repository.findById(uuid);
		return new ResponseEntity<>(pessoa, HttpStatus.OK);
    }
    
    @GetMapping(path = "/porNome/{nome}")
    @ApiOperation(value = "Retorna uma pessoa filtrando por nome", response = Pessoa.class)
    public ResponseEntity<?> getPorNome(@PathVariable String nome) {
        return new ResponseEntity<>(this.repository.findByNomeContainingIgnoreCase(nome), HttpStatus.OK) ;
    }

    @DeleteMapping(path = "/{uuid}")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Exclui uma pessoa através do id. Permitido somente usuários com privilégio 'Admin'")
    public ResponseEntity<?> delete(@PathVariable String uuid) {
        this.repository.deleteById(uuid);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @PostMapping
    @Transactional(rollbackFor = Exception.class)
    @ApiOperation(value = "Cria uma nova pessoa.", response = Pessoa.class)
    public ResponseEntity<?> save(@Valid @RequestBody Pessoa pessoa, Authentication auth) {
        pessoa.setUserLastUpdate((((User) auth.getPrincipal()).getUsername()));
        if (pessoa.getCreatedAt() == null) {
            pessoa.setCreatedAt(new Date());
        }
		return new ResponseEntity<>(this.repository.save(pessoa), HttpStatus.CREATED);
    }
    
    @PostMapping(path = "/lote")
    @Transactional(rollbackFor = Exception.class)
    @ApiOperation(value = "Cria pessoas em lote.", response = Pessoa.class)
    public ResponseEntity<?> savePeople(@RequestBody List<Pessoa> pessoas, Authentication auth) {
        String user = (((User) auth.getPrincipal()).getUsername());
        pessoas.forEach(pessoa -> {
        	pessoa.setUserLastUpdate(user);
        	if (pessoa.getCreatedAt() == null) {
        		pessoa.setCreatedAt(new Date());
        	}
        });
		return new ResponseEntity<>(this.repository.saveAll(pessoas), HttpStatus.CREATED);
    }
    
    @PutMapping
    @Transactional(rollbackFor = Exception.class)
    @ApiOperation(value = "Atualiza uma pessoa.", response = Pessoa.class)
    public ResponseEntity<?> update(@RequestBody Pessoa pessoa, Authentication auth) {
        pessoa.setUserLastUpdate((((User) auth.getPrincipal()).getUsername()));
        if (pessoa.getUpdatedAt() == null) {
            pessoa.setUpdatedAt(new Date());
        }
        return new ResponseEntity<>(this.repository.save(pessoa), HttpStatus.OK);
    }
}
