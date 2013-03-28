package no.torsteinv.MS2.Main.Identification;

public class IdentificationException extends Exception {
	private static final long serialVersionUID = 1L;

	public IdentificationException() {
		super();
	}

	public IdentificationException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public IdentificationException(String message, Throwable cause) {
		super(message, cause);
	}

	public IdentificationException(String message) {
		super(message);
	}

	public IdentificationException(Throwable cause) {
		super(cause);
	}

}
