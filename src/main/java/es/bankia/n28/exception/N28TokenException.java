package es.bankia.n28.exception;

/**
 * Thrown if errors occur during the authentication process.
 *
 */
public class N28TokenException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public N28TokenException(String message) {
        super(message);
    }

    public N28TokenException(String message, Throwable cause) {
        super(message, cause);
    }
}
