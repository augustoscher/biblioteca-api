package br.com.objetive.biblioteca.usuario;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUsuarioDetailService implements UserDetailsService {

	private final UsuarioRepository userRepository;
	
	@Autowired
	public CustomUsuarioDetailService(UsuarioRepository repo) {
		this.userRepository = repo;
	}
	
	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		Usuario user = Optional.ofNullable(this.userRepository.findByLogin(login)).//
				orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
		
        List<GrantedAuthority> authorityListAdmin = getAuthorityListAdmin(user);
        List<GrantedAuthority> authorityListUsers = getAuthorityListUsers(user);
		return new User(user.getLogin(), user.getSenha(), user.isAdmin() ? authorityListAdmin : authorityListUsers);
	}

    private List<GrantedAuthority> getAuthorityListAdmin(Usuario user) {
        String tenantID = getTenantId(user);
        if (tenantID != null) {
            return AuthorityUtils.createAuthorityList("ROLE_USER", "ROLE_ADMIN", tenantID);
        }
        return AuthorityUtils.createAuthorityList("ROLE_USER", "ROLE_ADMIN");
    }

    private List<GrantedAuthority> getAuthorityListUsers(Usuario user) {
        String tenantID = getTenantId(user);
        if (tenantID != null) {
            return AuthorityUtils.createAuthorityList("ROLE_USER", tenantID);
        }
        return AuthorityUtils.createAuthorityList("ROLE_USER");
    }

    private String getTenantId(Usuario user) {
        return user.getTenantId() != null ? "TENANT: " + user.getTenantId() : null;
    }
}
