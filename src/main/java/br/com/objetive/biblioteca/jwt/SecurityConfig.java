package br.com.objetive.biblioteca.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;

import br.com.objetive.biblioteca.usuario.CustomUsuarioDetailService;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomUsuarioDetailService detailService;

//	basic authentication
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http.authorizeRequests()
//		.anyRequest().authenticated()
//		//todas as requisiçoes autenticadas. Paara configurar por URL, usar antMatcher()
//		.and()
//		.httpBasic()
//		.and()
//		.csrf().disable();
//	}	
	
	//jwt authentication
	//Permite CORS de qqer lugar: http.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues())
	protected void configure(HttpSecurity http) throws Exception {
		//pesquisar como faz pra liberar somente uma URL.
        CorsConfiguration cors = new CorsConfiguration();
        cors.applyPermitDefaultValues();
        cors.addAllowedOrigin(SecurityConstants.ALLOWED_CORS_ORIGIN_PROD);
        cors.addAllowedOrigin(SecurityConstants.ALLOWED_CORS_ORIGIN_DEV);
        cors.addAllowedHeader("*");
        cors.addAllowedMethod("*");
        http.cors().configurationSource(request -> cors)
                //		  http.cors().disable()
        .and()
	    .csrf().disable().authorizeRequests()
//		.antMatchers(HttpMethod.POST, "/login").permitAll()
//		.antMatchers(HttpMethod.GET, "/login").permitAll()
		.antMatchers(HttpMethod.GET, "/v2/api-docs").permitAll()
		    .antMatchers(HttpMethod.GET, SecurityConstants.SIGN_UP_URL).permitAll()
		    .antMatchers(HttpMethod.POST, SecurityConstants.SIGN_UP_URL).permitAll()
		    .anyRequest().authenticated()
		    .and()
		    .addFilter(new JWTAuthenticationFilter(authenticationManager()))
		    .addFilter(new JWTAuthorizationFilter(authenticationManager(), this.detailService));
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(this.detailService).passwordEncoder(new BCryptPasswordEncoder());
	}
	
	
	//falta refatorar os outros endpoints pra ficar padrão
	//falta colocar flyway pra migrar o banco
	//ver exemplo de conexão do front-end, armazenando o token na sessionStorage ou localStorage e comunicando com backe-end
	//multi-tenant - ver
	//https - mais pra frente
	
	
//	authentication em memoria
//	@Autowired
//	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//		auth.inMemoryAuthentication()
//		.withUser("augusto").password("{noop}222").roles("USER")
//		.and()
//		.withUser("admin").password("{noop}admin").roles("USER", "ADMIN");
//	}
}
