package at.ac.fhsalzburg.swd.spring.config;

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
	public UserDetailsService userDetailsService(BCryptPasswordEncoder bCryptPasswordEncoder) {
	    InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
	    manager.createUser(User.withUsername("admin")
	      .password(bCryptPasswordEncoder.encode("admin"))
	      .roles("ADMIN")
	      .build());
	    manager.createUser(User.withUsername("user")
	      .password(bCryptPasswordEncoder.encode("user"))
	      .roles("USER")
	      .build());
	    return manager;
	}
	
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
	    .hasRole("ADMIN")
	    .antMatchers("/admin/**")
		.hasAnyRole("ADMIN")
		.antMatchers("/user/**")
		.hasAnyRole("USER", "ADMIN")
		.antMatchers("/*")	      
	      .permitAll()
	      .and()
	    .formLogin()
	    	.loginPage("/login")
	    	.defaultSuccessUrl("/")
	    	.failureUrl("/login-error")
	    .and().logout()	    	
      		.logoutSuccessUrl("http://nobody@localhost:8080/")
      		.invalidateHttpSession(true)
      		.deleteCookies("JSESSIONID");
	    return http.build();
	}
	
	
}