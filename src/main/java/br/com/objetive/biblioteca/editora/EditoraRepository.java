package br.com.objetive.biblioteca.editora;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;


public interface EditoraRepository extends PagingAndSortingRepository<Editora, String> {

    List<Editora> findByNomeContainingIgnoreCase(String nome);
}
