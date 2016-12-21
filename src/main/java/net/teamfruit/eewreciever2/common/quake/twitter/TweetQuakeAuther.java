package net.teamfruit.eewreciever2.common.quake.twitter;

import java.io.IOException;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

public class TweetQuakeAuther {

	public static enum AuthState {
		START, URL, TOKEN, CONNECT;
	}

	private final Twitter twitter = TwitterFactory.getSingleton();
	private RequestToken requestToken;
	private AccessToken accessToken;
	private AuthState state;

	public AuthState getState() {
		return this.state;
	}

	protected TweetQuakeAuther(final TweetQuakeKey key) {
		this.twitter.setOAuthConsumer(key.getKey1(), key.getKey2());
		this.state = AuthState.START;
	}

	public String getAuthURL() throws TwitterException {
		this.requestToken = this.twitter.getOAuthRequestToken();
		final String url = this.requestToken.getAuthorizationURL();
		this.state = AuthState.URL;
		return url;
	}

	public TweetQuakeAuther getAccessToken(final String pin) throws TwitterException {
		if (this.state!=AuthState.URL)
			throw new IllegalStateException();

		this.accessToken = this.twitter.getOAuthAccessToken(this.requestToken, pin);
		this.state = AuthState.TOKEN;
		return this;
	}

	public TweetQuakeAuther storeAccessToken() throws IOException {
		if (this.state!=AuthState.TOKEN)
			throw new IllegalStateException();
		TweetQuake.INSTANCE.setAccessToken(this.accessToken);
		return this;
	}

	public void connect() {
		if (this.state!=AuthState.TOKEN)
			throw new IllegalStateException();
		TweetQuake.INSTANCE.connect();
		this.state = AuthState.CONNECT;
	}
}
