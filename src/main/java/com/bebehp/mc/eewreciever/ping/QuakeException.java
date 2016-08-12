package com.bebehp.mc.eewreciever.ping;

/**
 * 地震情報取得例外
 * @author b7n
 */
public class QuakeException extends Exception {

	public QuakeException() {
		super();
	}

	public QuakeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public QuakeException(String message, Throwable cause) {
		super(message, cause);
	}

	public QuakeException(String message) {
		super(message);
	}

	public QuakeException(Throwable cause) {
		super(cause);
	}

}
