package at.ac.fhsalzburg.swd.spring.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.RegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
public class SecurityConfigurationREST {
	
	@Autowired
	private TokenService tokenService;
	
	
	//@Bean
	public SecurityFilterChain filterChainREST(HttpSecurity http) throws Exception {
		// order is important, start with most specific
		http.csrf().ignoringAntMatchers("/*","/admin/**","/user/**").and()
	    .authorizeRequests()
	    .antMatchers("/api/**")
			.hasAnyRole("USER", "ADMIN") // everything under /user needs either user or admin role
			.and()
			.addFilterBefore(new JwtAuthenticationFilter(tokenService),
					UsernamePasswordAuthenticationFilter.class);
				
		http.headers().frameOptions().disable(); // h2-console would not work otherwise
		
	    return http.build();
	}
	
	
	
	
}