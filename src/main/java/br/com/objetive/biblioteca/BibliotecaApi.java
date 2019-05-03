package br.com.objetive.biblioteca;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import br.com.objetive.biblioteca.jwt.JWTAuthenticationFilter;
import br.com.objetive.biblioteca.jwt.SecurityConstants;
import br.com.objetive.biblioteca.tenant.TenantRepository;
import br.com.objetive.biblioteca.usuario.Usuario;
import br.com.objetive.biblioteca.usuario.UsuarioRepository;
import br.com.objetive.biblioteca.utils.PasswordEncoder;

/**
 * Para fazer login, deve-se:
 * Fazer um post em localhost:8080/login com o seguinte conteúdo: {"login": "usera", "senha": "./augusto"}
 * Isso gerará um token que deverá ser utilizado nas requisições.
 * 
 * @author Augusto Scher
 */

@SpringBootApplication
public class BibliotecaApi {

	private static Logger LOGGER = LoggerFactory.getLogger(BibliotecaApi.class);
	
	public static void main(String[] args) {
        SpringApplication.run(BibliotecaApi.class);
	}

    @Bean
    public CommandLineRunner demo(UsuarioRepository usuarioRepo, TenantRepository tenantRepo) {
        return (args) -> {
            if (usuarioRepo.count() <= 0) {
            	LOGGER.info("initializing users...");
            	String pass = PasswordEncoder.encode(SecurityConstants.SECRET);
                Usuario user = new Usuario();
                user.setLogin("usera");
                user.setNome("usera");
                user.setAdmin(true);
                Date date = new Date();
                user.setCreatedAt(date);
                user.setUpdatedAt(date);
                user.setSenha(pass);
                usuarioRepo.save(user);
                LOGGER.info("user created: {}", user);

                Usuario user2 = new Usuario();
                user2.setLogin("userb");
                user2.setNome("userb");
                user2.setAdmin(false);
                user2.setCreatedAt(date);
                user2.setUpdatedAt(date);
                user2.setSenha(pass);
                usuarioRepo.save(user2);
                LOGGER.info("user2 created: {}", user2);
                LOGGER.info("finish initializing users");
            } else {
            	LOGGER.info("users already initialized...");
            }
            
        };
    }
}
