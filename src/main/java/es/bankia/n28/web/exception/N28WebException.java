package es.bankia.n28.web.exception;

/**
 * Thrown if errors occur during the MAC encrypt/decrypt process.
 *
 */
public class N28WebException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public N28WebException(String message) {
        super(message);
    }

    public N28WebException(String message, Throwable cause) {
        super(message, cause);
    }
}
