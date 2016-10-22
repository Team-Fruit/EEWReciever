package com.bebehp.mc.eewreciever.common.twitter;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

public class TweetQuakeSetup {

	private final Twitter twitter;
	private AccessToken accessToken;
	private RequestToken requestToken;

	public TweetQuakeSetup(final TweetQuakeKey tweetQuakeKey) {
		this.twitter = TwitterFactory.getSingleton();
		if (tweetQuakeKey != null)
			this.twitter.setOAuthConsumer(tweetQuakeKey.getKey1(), tweetQuakeKey.getKey2());
	}

	public String getAuthURL() throws TwitterException {
		this.requestToken = this.twitter.getOAuthRequestToken();
		return this.requestToken.getAuthorizationURL();
	}

	public AccessToken getAccessToken(final String pin) throws TwitterException {
		final AccessToken accessToken = this.twitter.getOAuthAccessToken(this.requestToken, pin);
		return accessToken;
	}

}
