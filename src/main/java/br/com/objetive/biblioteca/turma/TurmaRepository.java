package br.com.objetive.biblioteca.turma;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface TurmaRepository extends CrudRepository<Turma, String> {

	List<Turma> findByNomeContainingIgnoreCase(String nome);
}
