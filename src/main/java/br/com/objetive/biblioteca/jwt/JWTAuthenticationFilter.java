package br.com.objetive.biblioteca.jwt;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import br.com.objetive.biblioteca.usuario.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authenticationManager;
	private static Logger LOGGER = LoggerFactory.getLogger(JWTAuthenticationFilter.class);
	
	public JWTAuthenticationFilter(AuthenticationManager authentication) {
		this.authenticationManager = authentication;
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		try {
            Usuario user = new ObjectMapper().readValue(request.getInputStream(), Usuario.class);
            LOGGER.info("attemptAuthentication user {} and password {} ", user.getLogin(), user.getSenha());
            Authentication auth = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getLogin(), user.getSenha()));
            return auth;
		} catch (IOException e) {
			throw new RuntimeException(e); 
		}
	}
	
    /**
     * Geração de token com base no username e tenantid
     */
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
		//gerar token com username
        User user = ((User) authResult.getPrincipal());
		String token = Jwts
				.builder()//
                .setSubject(user.getUsername())//
				.setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))//
				.signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET)
				.compact();
		
		String bearerToken = SecurityConstants.TOKEN_PREFIX + token;
        String tenant = getUserTenant(user.getAuthorities());
        String jsonString = new Gson().toJson(new TokenObject(bearerToken, tenant));
		
		response.getWriter().write(jsonString); //escreve o token no body da requisição.
		response.addHeader(SecurityConstants.HEADER_STRING, bearerToken); //adiciona o token no header da requisição.
        response.addHeader(SecurityConstants.TENANT_STRING, tenant);
	}

    private String getUserTenant(Collection<GrantedAuthority> authorities) {
        StringBuilder sb = new StringBuilder();
        authorities.forEach(item -> {
            if (item.getAuthority().startsWith("TENANT:")) {
                sb.append(item.getAuthority().substring(7).trim());
            }
        });
        return sb.toString();
    }
}
