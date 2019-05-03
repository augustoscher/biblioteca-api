package br.com.objetive.biblioteca.jwt;

/**
 * 
 * @author augusto
 *
 */
public class SecurityConstants {
	
	public static final String SECRET = "./augusto";
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";
    public static final String TENANT_STRING = "Tenant";
    public static final String ALLOWED_CORS_ORIGIN_PROD = "https://biblioteca-app.netlify.com";
    public static final String ALLOWED_CORS_ORIGIN_DEV = "http://localhost:4200";
    //	public static final String SIGN_UP_URL = "users/signup";
    public static final String SIGN_UP_URL = "/login";
	public static final long EXPIRATION_TIME = 86400000L; //1dia
	

}
