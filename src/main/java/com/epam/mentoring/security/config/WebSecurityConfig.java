package com.epam.mentoring.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.epam.mentoring.security.repository.UserRepository;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	@Bean
	public static PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public UserDetailsService getUserDetailsService(UserRepository userRepository) {
		return new UserDetailsServiceImpl(userRepository);
	}

	@Bean
	public DaoAuthenticationProvider getAuthenticationProvider(UserRepository userRepository) {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(getUserDetailsService(userRepository));
		authProvider.setPasswordEncoder(getPasswordEncoder());

		return authProvider;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http, UserRepository userRepository) throws Exception {
		http
				.authenticationProvider(getAuthenticationProvider(userRepository))
				.authorizeHttpRequests(authz -> authz
						.requestMatchers("/info").hasAuthority("VIEW_INFO")
						.requestMatchers("/admin").hasAuthority("VIEW_ADMIN")
						.requestMatchers("/about").permitAll()
						.anyRequest().authenticated()
				)
				.formLogin();
		return http.build();
	}
}
