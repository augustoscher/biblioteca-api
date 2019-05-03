package br.com.objetive.biblioteca.usuario;

import org.springframework.data.repository.CrudRepository;

public interface UsuarioRepository extends CrudRepository<Usuario, String> {

	public Usuario findByLogin(String login);

}
