package com.bebehp.mc.eewreciever.twitter;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

public class TweetQuakeSetup implements Runnable {
	public static final TweetQuakeSetup INSTANCE = new TweetQuakeSetup();

	private final Twitter twitter;
	private final TweetQuakeKey tweetQuakeKey;
	private AccessToken accessToken;

	public String getAuthURL() throws TwitterException {
		return this.twitter.getOAuthRequestToken().getAuthenticationURL();
	}

	public TweetQuakeSetup() {
		this.twitter = TwitterFactory.getSingleton();
		this.tweetQuakeKey = TweetQuakeFileHelper.loadKey();
		this.twitter.setOAuthConsumer(this.tweetQuakeKey.getKey1(), this.tweetQuakeKey.getKey2());
	}

	public AccessToken getAccessToken(final String pin) throws TwitterException {
		final RequestToken requestToken = this.twitter.getOAuthRequestToken();
		final AccessToken accessToken = this.twitter.getOAuthAccessToken(requestToken, pin);
		return accessToken;

	}

	@Override
	public void run() {

	}
}
