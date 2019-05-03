package br.com.objetive.biblioteca.emprestimo;

import java.util.ArrayList;
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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.objetive.biblioteca.estatistica.EstatisticaService;
import br.com.objetive.biblioteca.livro.Livro;
import br.com.objetive.biblioteca.livro.LivroRepository;
import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping("v1/emprestimos")
public class EmprestimoController {

	@Autowired
	private EmprestimoRepository repository;

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    EstatisticaService estatisticaService;
	 
	@GetMapping
    @ApiOperation(value = "Retorna uma lista com todos os emprestimos de maneira paginada", response = Emprestimo[].class)
    public ResponseEntity<?> get(Pageable pageable) {
        return new ResponseEntity<>(repository.findByStatus(pageable, StatusEmprestimo.EMPRESTADO), HttpStatus.OK);
    }

    @GetMapping(path = "/{uuid}")
    @ApiOperation(value = "Retorna um emprestimo filtrando por id", response = Emprestimo.class)
    public ResponseEntity<?> getPorUuId(@PathVariable("uuid") String uuid, Authentication auth) {
        Optional<Emprestimo> emprestimo = this.repository.findById(uuid);
        return new ResponseEntity<>(emprestimo, HttpStatus.OK);
    }

    @GetMapping(path = "/pendentes")
    @ApiOperation(value = "Retorna ema lista de empréstimos que estão pendentes de devolução", response = Emprestimo.class)
    public ResponseEntity<?> getAllEmprestados(Pageable pageable) {
        return new ResponseEntity<>(this.repository.findAllEmprestados(StatusEmprestimo.EMPRESTADO.getId()), HttpStatus.OK);
    }

    @GetMapping(path = "/por/{searchTerm}")
    @ApiOperation(value = "Retorna uma lista de empréstimos filtrando pelo valor do parâmetro", response = Emprestimo.class)
    public ResponseEntity<?> getBy(Pageable pageable, @PathVariable String searchTerm) {
        return new ResponseEntity<>(this.repository.findByTurmaPessoa(StatusEmprestimo.EMPRESTADO.getId(), getLike(searchTerm), getLike(searchTerm), pageable), HttpStatus.OK);
    }

    private String getLike(String searchTerm) {
        return "%" + searchTerm + "%";
    }

    @DeleteMapping(path = "/{uuid}")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Exclui um emprestimo através do id. Permitido somente usuários com privilégio 'Admin'")
    public ResponseEntity<?> delete(@PathVariable String uuid) {
        this.repository.deleteById(uuid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping
    @Transactional(rollbackFor = Exception.class)
    @ApiOperation(value = "Cria um novo Emprestimo.", response = Emprestimo.class)
    public ResponseEntity<?> save(@Valid @RequestBody Emprestimo emprestimo, Authentication auth) {
        String usuario = (((User) auth.getPrincipal()).getUsername());
        if (emprestimo.getStatus() == null) {
            emprestimo.setStatus(StatusEmprestimo.EMPRESTADO);
        }
        Date date = new Date();
        if (emprestimo.getCreatedAt() == null) {
            emprestimo.setCreatedAt(date);
        }
        if (emprestimo.getTurma() != null && StringUtils.isEmpty(emprestimo.getTurma().getUuid())) {
            emprestimo.setTurma(null);
        }
        emprestimo.setUserLastUpdate(usuario);
        emprestimo.getLivros().forEach(item -> {
            item.setUsuarioEmprestimo(usuario);
            if (item.getCreatedAt() == null) {
                item.setCreatedAt(date);
            }
            if (item.getEmprestimo() == null) {
            	item.setEmprestimo(emprestimo);
            }
        });
        return new ResponseEntity<>(novoEmprestimo(emprestimo), HttpStatus.CREATED);
    }

    private Emprestimo novoEmprestimo(Emprestimo emprestimo) {
        estatisticaService.atualizarEmprestimoPorUsuario(emprestimo.getUserLastUpdate(), 1);
        this.livroRepository.saveAll(getLivrosEmprestimo(emprestimo));
        return this.repository.save(emprestimo);
    }
    
    private List<Livro> getLivrosEmprestimo(Emprestimo emprestimo) {
        List<Livro> result = new ArrayList<>(); 
        emprestimo.getLivros().forEach(item -> {
            Livro livro = this.livroRepository.findById(item.getLivro().getUuid()).get();
            livro.setEmprestado(true);
            result.add(livro);
        });
        return result;
    }

    @PutMapping
    @Transactional(rollbackFor = Exception.class)
    @ApiOperation(value = "Atualiza um Emprestimo.", response = Emprestimo.class)
    public ResponseEntity<?> update(@RequestBody Emprestimo emprestimo, Authentication auth) {
        emprestimo.setUserLastUpdate((((User) auth.getPrincipal()).getUsername()));
        if (emprestimo.getUpdatedAt() == null) {
            emprestimo.setUpdatedAt(new Date());
        }
        emprestimo.setStatus(StatusEmprestimo.DEVOLVIDO);
        emprestimo.getLivros().forEach(item -> {
            if (item.getUpdatedAt() == null) {
                item.setUpdatedAt(new Date());
            }
            if (item.getEmprestimo() == null) {
                item.setEmprestimo(emprestimo);
            }
            if (item.getStatus() == StatusEmprestimo.EMPRESTADO) {
                emprestimo.setStatus(StatusEmprestimo.EMPRESTADO);
            }
        });
        return new ResponseEntity<>(atualizarEmprestimo(emprestimo), HttpStatus.OK);
    }

    private Emprestimo atualizarEmprestimo(Emprestimo emprestimo) {
        this.livroRepository.saveAll(getLivrosDevolucao(emprestimo));
        return this.repository.save(emprestimo);
    }

    private List<Livro> getLivrosDevolucao(Emprestimo emprestimo) {
        List<Livro> result = new ArrayList<>();
        emprestimo.getLivros().forEach(item -> {
            if (item.getStatus() == StatusEmprestimo.DEVOLVIDO) {
                Livro livro = this.livroRepository.findById(item.getLivro().getUuid()).get();
                livro.setEmprestado(false);
                result.add(livro);
            }
        });
        return result;
    }
}
