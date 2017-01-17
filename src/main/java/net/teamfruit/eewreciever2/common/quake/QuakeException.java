package net.teamfruit.eewreciever2.common.quake;

/**
 * 地震情報取得例外
 * @author b7n
 *
 */
public class QuakeException extends Exception {

	public QuakeException() {
		super();
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
