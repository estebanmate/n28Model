package com.cora.token.exception;

/**
 * Thrown if errors occur during the authentication process.
 *
 */
public class SendChannelException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public SendChannelException(String message) {
        super(message);
    }

    public SendChannelException(String message, Throwable cause) {
        super(message, cause);
    }
}
