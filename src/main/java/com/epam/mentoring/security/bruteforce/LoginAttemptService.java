package com.epam.mentoring.security.bruteforce;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.epam.mentoring.security.model.User;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

@Service
public class LoginAttemptService {
	public static final int MAX_ATTEMPT = 3;
	private LoadingCache<String, Integer> attemptsCache;

	public LoginAttemptService() {
		super();
		attemptsCache = CacheBuilder.newBuilder()
				.expireAfterWrite(5, TimeUnit.MINUTES)
				.build(new CacheLoader<>() {
					@Override
					public Integer load(final String key) {
						return 0;
					}
				});
	}

	public void loginFailed(final String key) {
		int attempts;
		try {
			attempts = attemptsCache.get(key);
		} catch (final ExecutionException e) {
			attempts = 0;
		}
		attempts++;
		attemptsCache.put(key, attempts);
	}

	public boolean isBlocked(User user) {
		try {
			return attemptsCache.get(user.getUsername()) >= MAX_ATTEMPT;
		} catch (final ExecutionException e) {
			return false;
		}
	}
}
