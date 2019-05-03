package br.com.objetive.biblioteca.pessoa;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface PessoaRepository extends PagingAndSortingRepository<Pessoa, String> {

    List<Pessoa> findByNomeContainingIgnoreCase(String nome);
    
    
}
