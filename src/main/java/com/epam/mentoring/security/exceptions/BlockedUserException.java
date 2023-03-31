package com.epam.mentoring.security.exceptions;

public class BlockedUserException extends RuntimeException {
	public BlockedUserException(String message) {
		super(message);
	}
}
