package at.ac.fhsalzburg.swd.spring.security;

import org.springframework.security.core.userdetails.UserDetails;

import at.ac.fhsalzburg.swd.spring.dao.User;

public interface TokenServiceInterface {
	
	String generateToken(UserDetails user);

	UserDetails parseToken(String token);

}
