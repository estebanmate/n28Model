package com.cora.token.exception;

/**
 * Thrown if errors occur during the authentication process.
 *
 */
public class AuthenticationException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AuthenticationException(String message) {
        super(message);
    }

    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
