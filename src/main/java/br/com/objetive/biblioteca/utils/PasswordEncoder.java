package br.com.objetive.biblioteca.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.objetive.biblioteca.jwt.SecurityConstants;

public class PasswordEncoder {

	public static void main(String[] args) {
		BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
        System.out.println(bCrypt.encode(SecurityConstants.SECRET));
	}
	
	public static String encode(String str) {
		BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
		return bCrypt.encode(str);
	}
	
}

