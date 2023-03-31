package com.epam.mentoring.security.config;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.thymeleaf.extras.springsecurity6.dialect.SpringSecurityDialect;

import com.epam.mentoring.security.bruteforce.EventSendingAuthenticationFailureHandler;
import com.epam.mentoring.security.bruteforce.LoginAttemptService;
import com.epam.mentoring.security.repository.UserRepository;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	@Bean
	public static PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public UserDetailsService getUserDetailsService(UserRepository userRepository, LoginAttemptService loginAttemptService) {
		return new UserDetailsServiceImpl(userRepository, loginAttemptService);
	}

	@Bean
	public DaoAuthenticationProvider getAuthenticationProvider(UserRepository userRepository, LoginAttemptService loginAttemptService) {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(getUserDetailsService(userRepository, loginAttemptService));
		authProvider.setPasswordEncoder(getPasswordEncoder());

		return authProvider;
	}

	@Bean
	public AuthenticationFailureHandler getAuthenticationFailureHandler(ApplicationEventPublisher applicationEventPublisher) {
		EventSendingAuthenticationFailureHandler handler = new EventSendingAuthenticationFailureHandler();
		handler.setApplicationEventPublisher(applicationEventPublisher);

		return handler;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http,
										   UserRepository userRepository,
										   LoginAttemptService loginAttemptService,
										   ApplicationEventPublisher applicationEventPublisher) throws Exception {
		http
				.authenticationProvider(getAuthenticationProvider(userRepository, loginAttemptService))
				.authorizeHttpRequests(authz -> authz
						.requestMatchers("/info").hasAuthority("VIEW_INFO")
						.requestMatchers("/admin").hasAuthority("VIEW_ADMIN")
						.requestMatchers("/about").permitAll()
						.anyRequest().authenticated()
				)
				.formLogin()
				.loginPage("/login")
				.permitAll()
				.defaultSuccessUrl("/index")
				.failureUrl("/login?error")
				.and()
				.logout()
				.clearAuthentication(true)
				.invalidateHttpSession(true)
				.logoutSuccessUrl("/login?logout")
				.permitAll();
		return http.build();
	}

	@Bean
	public SpringSecurityDialect springSecurityDialect() {
		return new SpringSecurityDialect();
	}
}
