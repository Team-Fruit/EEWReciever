package net.teamfruit.eewreciever2.common;

public class QuakeException extends Exception {

	public QuakeException() {
		super();
	}

	public QuakeException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public QuakeException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public QuakeException(final String message) {
		super(message);
	}

	public QuakeException(final Throwable cause) {
		super(cause);
	}
}
