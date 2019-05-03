package br.com.objetive.biblioteca.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import br.com.objetive.biblioteca.usuario.CustomUsuarioDetailService;
import io.jsonwebtoken.Jwts;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	private final CustomUsuarioDetailService customUsuarioDetailService;
	
	public JWTAuthorizationFilter(AuthenticationManager authenticationManager, CustomUsuarioDetailService customUsuarioDetailService) {
		super(authenticationManager);
		this.customUsuarioDetailService = customUsuarioDetailService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		String header = request.getHeader(SecurityConstants.HEADER_STRING);
		if (header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
			chain.doFilter(request, response);
			return;
		}
		UsernamePasswordAuthenticationToken authenticationToken = getAuthenticationToken(request);
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		chain.doFilter(request, response);
	}
	
	private UsernamePasswordAuthenticationToken getAuthenticationToken(HttpServletRequest request) {
		String token = request.getHeader(SecurityConstants.HEADER_STRING);
		if (token == null) {
			return null;
		}
		//pegar o userName do token
		String userName = Jwts
				.parser()//
				.setSigningKey(SecurityConstants.SECRET)//
				.parseClaimsJws(token.replace(SecurityConstants.TOKEN_PREFIX, ""))//
				.getBody()//
				.getSubject();
		
		UserDetails userDetails = customUsuarioDetailService.loadUserByUsername(userName);
		return userName != null ? new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()) : null;
	}

}
