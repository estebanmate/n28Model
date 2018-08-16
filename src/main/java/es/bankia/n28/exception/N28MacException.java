package es.bankia.n28.exception;

/**
 * Thrown if errors occur during the MAC encrypt/decrypt process.
 *
 */
public class N28MacException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public N28MacException(String message) {
        super(message);
    }

    public N28MacException(String message, Throwable cause) {
        super(message, cause);
    }
}
