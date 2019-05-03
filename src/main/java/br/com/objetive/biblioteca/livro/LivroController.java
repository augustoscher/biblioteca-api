package br.com.objetive.biblioteca.livro;

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

import br.com.objetive.biblioteca.estatistica.EstatisticaService;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("v1/livros")
public class LivroController {

    @Autowired
    private LivroRepository repository;
    
    @Autowired
    EstatisticaService estatisticaService;

    @GetMapping
    @ApiOperation(value = "Retorna uma lista com todos os livros de maneira paginada", response = Livro[].class)
    public ResponseEntity<?> get(Pageable pageable) {
		return new ResponseEntity<>(repository.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping(path = "/{uuid}")
    @ApiOperation(value = "Retorna um livro filtrando por id", response = Livro.class)
    public ResponseEntity<?> getPorUuId(@PathVariable("uuid") String uuid, Authentication auth) {
//    	Sstem.out.println(auth.getPrincipal()); assim pega o usuário que vem na requisição.
		Optional<Livro> livro = this.repository.findById(uuid);
		return new ResponseEntity<>(livro, HttpStatus.OK);
    }
    
    @GetMapping(path = "/por/{searchTerm}")
    @ApiOperation(value = "Retorna um livro filtrando pelo valor passado por parâmetro", response = Livro[].class)
    public ResponseEntity<?> getPor(Pageable pageable, @PathVariable String searchTerm) {
        return new ResponseEntity<>(this.repository.findByAutorNomeContainingOrCodigoLivreContainingOrTituloContainingAllIgnoreCase(pageable, searchTerm, searchTerm, searchTerm), HttpStatus.OK) ;
    }

    @DeleteMapping(path = "/{uuid}")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Exclui um livro através do id. Permitido somente usuários com privilégio 'Admin'")
    public ResponseEntity<?> delete(@PathVariable String uuid) {
        this.repository.deleteById(uuid);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @PostMapping
    @Transactional(rollbackFor = Exception.class)
    @ApiOperation(value = "Cria um novo livro.", response = Livro.class)
    public ResponseEntity<?> save(@Valid @RequestBody Livro livro, Authentication auth) {
        livro.setUserLastUpdate((((User) auth.getPrincipal()).getUsername()));
        if (livro.getCreatedAt() == null) {
            livro.setCreatedAt(new Date());
        }
        return new ResponseEntity<>(novoLivro(livro), HttpStatus.CREATED);
    }

    private Livro novoLivro(Livro livro) {
        estatisticaService.atualizarLivros(1);
        return this.repository.save(livro);
    }

    @PutMapping
    @Transactional(rollbackFor = Exception.class)
    @ApiOperation(value = "Atualiza um livro.", response = Livro.class)
    public ResponseEntity<?> update(@RequestBody Livro livro, Authentication auth) {
        livro.setUserLastUpdate((((User) auth.getPrincipal()).getUsername()));
        if (livro.getUpdatedAt() == null) {
            livro.setUpdatedAt(new Date());
        }
        return new ResponseEntity<>(this.repository.save(livro), HttpStatus.OK);
    }

}
