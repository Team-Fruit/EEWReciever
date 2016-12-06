package net.teamfruit.eewreciever2.common.quake.twitter;

import java.io.IOException;

import net.teamfruit.eewreciever2.common.Reference;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

public class TweetQuakeAuther {

	private final Twitter twitter = TwitterFactory.getSingleton();
	private RequestToken requestToken;
	private AccessToken accessToken;

	public TweetQuakeAuther(final TweetQuakeKey key) {
		this.twitter.setOAuthConsumer(key.getKey1(), key.getKey2());
	}

	public String getAuthURL() throws TwitterException {
		this.requestToken = this.twitter.getOAuthRequestToken();
		return this.requestToken.getAuthorizationURL();
	}

	public AccessToken getAccessToken(final String pin) throws TwitterException {
		if (this.requestToken==null)
			throw new IllegalStateException();

		this.accessToken = this.twitter.getOAuthAccessToken(this.requestToken, pin);
		return this.accessToken;
	}

	public void connect() {
		if (this.accessToken==null)
			throw new IllegalStateException();

		try {
			TweetQuake.INSTANCE.setAccessToken(this.accessToken);
		} catch (final IOException e) {
			Reference.logger.error(e.getMessage(), e);
		}
	}
}
