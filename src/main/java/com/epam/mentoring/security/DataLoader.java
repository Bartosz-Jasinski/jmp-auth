package com.epam.mentoring.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.epam.mentoring.security.model.Authority;
import com.epam.mentoring.security.model.User;
import com.epam.mentoring.security.repository.AuthorityRepository;
import com.epam.mentoring.security.repository.UserRepository;

@Component
public class DataLoader implements ApplicationRunner {
	public static final String ADMIN_EMAIL = "admin@epam.com";
	public static final String USER_EMAIL = "user@epam.com";
	public static final String VIEW_INFO_PERMISSION = "VIEW_INFO";
	public static final String VIEW_ADMIN_PERMISSION = "VIEW_ADMIN";

	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	private final AuthorityRepository authorityRepository;

	@Autowired
	public DataLoader(PasswordEncoder passwordEncoder, UserRepository userRepository, AuthorityRepository authorityRepository) {
		this.passwordEncoder = passwordEncoder;
		this.userRepository = userRepository;
		this.authorityRepository = authorityRepository;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		User user = new User(USER_EMAIL, passwordEncoder.encode("userPass"), true);
		User admin = new User(ADMIN_EMAIL, passwordEncoder.encode("adminPass"), true);
		User noname = new User("noname@epam.com", passwordEncoder.encode("nonamePass"), true);

		userRepository.save(user);
		userRepository.save(admin);
		userRepository.save(noname);

		authorityRepository.save(new Authority(VIEW_INFO_PERMISSION, user));
		authorityRepository.save(new Authority(VIEW_INFO_PERMISSION, admin));
		authorityRepository.save(new Authority(VIEW_ADMIN_PERMISSION, admin));
	}
}
