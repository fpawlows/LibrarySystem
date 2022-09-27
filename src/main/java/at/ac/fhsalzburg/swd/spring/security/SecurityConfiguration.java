package at.ac.fhsalzburg.swd.spring.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration {
	
	
	@Autowired
	private TokenService tokenService;
	
	
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// order is important, start with most specific
		http.
		csrf().ignoringAntMatchers("/*","/admin/**","/user/**").and()
	    .authorizeRequests()
	    .antMatchers("/api/**")
			.hasAnyRole("USER", "ADMIN") // everything under /user needs either user or admin role
			.and()
			.addFilterBefore(new JwtAuthenticationFilter(tokenService),
					UsernamePasswordAuthenticationFilter.class)
		.csrf().ignoringAntMatchers("/api/**").and()		
	    .authorizeRequests()
	    .antMatchers("/admin/**")
			.hasAnyRole("ADMIN") // everything under /admin needs admin role
		.antMatchers("/user/**")
			.hasAnyRole("USER", "ADMIN") // everything under /user needs either user or admin role
		//.antMatchers("/*")	      
	    //  .permitAll() // no login required for everything under / (without sub-dirs)
	    .and()
	    .formLogin() // we want form based authentication
	    	.loginPage("/login") // our login page
	    	.defaultSuccessUrl("/") // where to go when login was successful
	    	.failureUrl("/login-error") // when something went wrong
	    .and().logout()    	
	    	.logoutSuccessUrl("/") // where to go after logout
      		.invalidateHttpSession(true) // at logout invalidate session
      		.deleteCookies("JSESSIONID") // and delete session cookie
      	;
		
		http.headers().frameOptions().disable(); // h2-console would not work otherwise
		
	    return http.build();
	}
	
	
	
	
}