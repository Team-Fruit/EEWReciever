package net.teamfruit.eewreciever2.common.quake.twitter;

/**
 * APIキー処理例外
 * @author bebe
 *
 */
public class TweetQuakeSecureException extends Exception {
	public TweetQuakeSecureException() {
		super();
	}

	public TweetQuakeSecureException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public TweetQuakeSecureException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public TweetQuakeSecureException(final String message) {
		super(message);
	}

	public TweetQuakeSecureException(final Throwable cause) {
		super(cause);
	}

}
