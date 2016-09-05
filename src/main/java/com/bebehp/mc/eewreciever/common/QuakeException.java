package com.bebehp.mc.eewreciever.common;

/**
 * 地震情報取得例外
 * @author b7n
 */
public class QuakeException extends Exception {

	private static final long serialVersionUID = 1L;

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
