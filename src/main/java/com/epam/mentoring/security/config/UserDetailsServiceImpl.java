package com.epam.mentoring.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.epam.mentoring.security.bruteforce.LoginAttemptService;
import com.epam.mentoring.security.exceptions.BlockedUserException;
import com.epam.mentoring.security.model.User;
import com.epam.mentoring.security.repository.UserRepository;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {
	private final UserRepository userRepository;
	private final LoginAttemptService loginAttemptService;

	@Autowired
	public UserDetailsServiceImpl(UserRepository userRepository, LoginAttemptService loginAttemptService) {
		this.userRepository = userRepository;
		this.loginAttemptService = loginAttemptService;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findById(username)
				.orElseThrow(() -> new UsernameNotFoundException("Could not find user."));

		validateAndUpdateUserBlockedStatus(user);

		return new UserDetailsConfig(user);
	}

	private void validateAndUpdateUserBlockedStatus(User user) {
		boolean isBlocked = loginAttemptService.isBlocked();
		user.setEnabled(!isBlocked);

		userRepository.save(user);

		if (isBlocked) {
			throw new BlockedUserException(user.getUsername() + " is blocked.");
		}
	}
}
