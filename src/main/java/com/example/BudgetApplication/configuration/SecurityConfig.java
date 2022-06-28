package com.example.BudgetApplication.configuration;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.example.BudgetApplication.security.AuthenticationFilter;
import com.example.BudgetApplication.security.AuthorizationFilter;

/**
 * Class to configure Spring Security which inherits from WebSecurityConfigurerAdapter.
 * 
 * @author Benjamin Chang
 *
 */
@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private UserDetailsService uds;

	/**
	 * Bean method to create a PasswordEncoder which uses BCryptPasswordEncoder.
	 * 
	 * @return BCryptPasswordEncoder
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * Default constructor for SecurityConfig class which autowres in UserDetailsService
	 * @param uds
	 */
	@Autowired
	public SecurityConfig(UserDetailsService uds) {
		super();
		this.uds = uds;
	}

	/**
	 * Overriden method of configure which sets the password encoder used by UserDetailsService to the password encoder generated from the Bean method.
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(uds).passwordEncoder(passwordEncoder());
	}

	/**
	 * Overriden method to configure HttpSecurity which will control access to the SpringBoot application for API calls.
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.cors()
				.and()
			.csrf()
				.disable()
			.authorizeRequests()
				.antMatchers("/api/register").permitAll()
				.anyRequest()
				.authenticated()
				.and()
			.addFilter(getAuthenticationFilter())
			.addFilter(new AuthorizationFilter(authenticationManager()))
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.logout()
			.invalidateHttpSession(true)
			.deleteCookies("JSESSIONID");
	}

	
	/**
	 * Method to get AuthenticationFilter object
	 * 
	 * @return AuthenticationFilter
	 * @throws Exception
	 */
	public AuthenticationFilter getAuthenticationFilter() throws Exception {
		final AuthenticationFilter filter = new AuthenticationFilter(authenticationManager());
		filter.setFilterProcessesUrl("/api/login");
		return filter;
	}

	/**
	 * Overriden method to get AuthenticationManager
	 */
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	/**
	 * Method to create a Bean which returns a CorsConfigurationSource. Used to configure cors to allow front-end to interact with Spring Boot's backend.
	 * 
	 * @return CorsConfigurationSource object
	 */
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		final CorsConfiguration configuration = new CorsConfiguration();

		configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
		configuration.setAllowCredentials(true);
		configuration.setAllowedHeaders(Arrays.asList("*"));
		configuration.setExposedHeaders(Arrays.asList("*"));
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;

	}
}