package com.epam.mentoring.security.bruteforce;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class EventSendingAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	protected ApplicationEventPublisher eventPublisher;

	@Autowired
	public void setApplicationEventPublisher(ApplicationEventPublisher eventPublisher) {
		this.eventPublisher = eventPublisher;
	}

	@Override
	public void onAuthenticationFailure(HttpServletRequest request,
										HttpServletResponse response,
										AuthenticationException exception) throws IOException, ServletException {

		if (exception instanceof BadCredentialsException) {
			String name = request.getParameter("username");
			String password = request.getParameter("password");
			Authentication auth =
					new UsernamePasswordAuthenticationToken(name, password);
			eventPublisher.publishEvent(
					new AuthenticationFailureBadCredentialsEvent(auth, exception));
		}
		super.onAuthenticationFailure(request, response, exception);
	}
}
