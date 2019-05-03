package br.com.objetive.biblioteca.estatistica;

import java.util.List;

import org.springframework.data.repository.CrudRepository;


public interface EstatisticaEmprestimoUsuarioRepository extends CrudRepository<EstatisticaEmprestimoUsuario, String> {

    List<EstatisticaEmprestimoUsuario> findByUsuarioIgnoringCase(String user);
}
