package at.ac.fhsalzburg.swd.spring.security;

import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration {
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder()
	{
	    return new BCryptPasswordEncoder();
	}
	
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		// order is important, start with most specific
		http.csrf().disable()		
	    .authorizeRequests()
	    .antMatchers(HttpMethod.DELETE)
	    	.hasRole("ADMIN") // http delete only with admin role
	    .antMatchers("/admin/**")
			.hasAnyRole("ADMIN") // everything under /admin needs admin role
		.antMatchers("/api/**")
			.hasAnyRole("USER", "ADMIN") // everything under /user needs either user or admin role
		.antMatchers("/*")	      
	      .permitAll() // no login required for everything under / (without sub-dirs)
	      .and()
	    .formLogin() // we want form based authentication
	    	.loginPage("/login") // our login page
	    	.defaultSuccessUrl("/") // where to go when login was successful
	    	.failureUrl("/login-error") // when something went wrong
	    .and().logout()    	
	    	.logoutSuccessUrl("/") // where to go after logout
      		.invalidateHttpSession(true) // at logout invalidate session
      		.deleteCookies("JSESSIONID"); // and delete session cookie
		
		http.headers().frameOptions().disable(); // h2-console would not work otherwise
		
	    return http.build();
	}
	
	
}