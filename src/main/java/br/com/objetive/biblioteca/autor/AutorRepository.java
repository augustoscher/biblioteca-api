package br.com.objetive.biblioteca.autor;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;


public interface AutorRepository extends PagingAndSortingRepository<Autor, String> {

    List<Autor> findByNomeContainingIgnoreCase(String nome);

}
