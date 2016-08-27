package com.bebehp.mc.eewreciever.common.twitter;

import com.bebehp.mc.eewreciever.EEWRecieverMod;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

public class TweetQuakeSetup {
	public static final TweetQuakeSetup INSTANCE = new TweetQuakeSetup();

	private final Twitter twitter;
	private AccessToken accessToken;
	private RequestToken requestToken = null;

	public TweetQuakeSetup() {
		this.twitter = TwitterFactory.getSingleton();
		this.twitter.setOAuthConsumer(EEWRecieverMod.tweetQuakeKey.getKey1(), EEWRecieverMod.tweetQuakeKey.getKey2());
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
