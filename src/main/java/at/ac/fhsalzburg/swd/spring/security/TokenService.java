package at.ac.fhsalzburg.swd.spring.security;


import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import at.ac.fhsalzburg.swd.spring.dao.Product;
import at.ac.fhsalzburg.swd.spring.dao.ProductRepository;
import at.ac.fhsalzburg.swd.spring.dao.User;
import at.ac.fhsalzburg.swd.spring.services.UserService;
import at.ac.fhsalzburg.swd.spring.services.UserServiceInterface;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

// http://jwtbuilder.jamiekurtz.com/
// test with: curl http://localhost:8080/api/users -H 'Accept: application/json' -H "Authorization:Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJPbmxpbmUgSldUIEJ1aWxkZXIiLCJpYXQiOjE2NjQzMDc0NDksImV4cCI6MTY5NTg0MzQ0OSwiYXVkIjoid3d3LmV4YW1wbGUuY29tIiwic3ViIjoiYWRtaW4iLCJ1c2VybmFtZSI6ImFkbWluIn0.curjpEf0q9S43s5EPLB9Pk7VXZEex0onsK2xr74QOak"

@Service
public class TokenService implements TokenServiceInterface {

	private final static String JWT_SECRET = "eyJhbGciOiJIUzI1NiJ9.eyJSb2xlIjoiQWRtaW4iLCJJc3N1ZXIiOiJJc3N1ZXIiLCJVc2VybmFtZSI6IkphdmFJblVzZSIsImV4cCI6MTY2NDMwMjc2OSwiaWF0IjoxNjY0MzAyNzY5fQ.yTUWCgeYft_UXJWJTAfub-BndpDDQmMes73UxKuqkzE";

	@Autowired
	private UserService UserDetailService;
	
	
	
    public TokenService() {

    }

    @Override
    public String generateToken(UserDetails puser) {
    	DemoPrincipal user = (DemoPrincipal) puser;
        Instant expirationTime = Instant.now().plus(1, ChronoUnit.HOURS);
        Date expirationDate = Date.from(expirationTime);

        Key key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes());
        
        String compactTokenString = Jwts.builder()
                .claim("id", user.getUsername())
                .claim("sub", user.getUsername())
                .claim("admin", user.getRole().equals("ADMIN"))
                .claim("role", user.getRole())
                .claim("password", user.getPassword())
                .setExpiration(expirationDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return compactTokenString;
    }

	@Override
	public DemoPrincipal parseToken(String token) {
		byte[] secretBytes = JWT_SECRET.getBytes();

		if (!token.startsWith("Bearer ")) return null;
		token = token.substring(6);
        
		Jws<Claims> jwsClaims = Jwts.parserBuilder()
                .setSigningKey(secretBytes)
                .build()
                .parseClaimsJws(token);

        String username = jwsClaims.getBody()
                .getSubject();               
        
        return (DemoPrincipal) UserDetailService.loadUserByUsername(username);        
	}

}
