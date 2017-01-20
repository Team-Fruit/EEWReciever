package net.teamfruit.eewreciever2.common.quake.twitter;

import java.io.IOException;

import org.apache.commons.lang3.ArrayUtils;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.auth.AccessToken;

public class TweetQuakeManager {
	private static final TweetQuakeManager INSTANCE = new TweetQuakeManager();

	private final TweetQuakeSecure secure;
	private final Twitter twitter;

	private TweetQuakeManager() {
		this.secure = new TweetQuakeSecure().init();
		this.twitter = this.secure.getAuthedTwitter();
	}

	public static TweetQuakeManager intance() {
		return INSTANCE;
	}

	public TweetQuakeAuther getAuther() {
		return new TweetQuakeAuther(this.twitter);
	}

	public void setAccessToken(final AccessToken token) throws IOException {
		this.secure.storeAccessToken(token);
	}

	public boolean isKeyValid() {
		return this.secure.isKeyValid();
	}

	public boolean isTokenValid() {
		return this.secure.isTokenValid();
	}

	public Twitter getAuthedTwitter() {
		return this.twitter;
	}

	public TwitterStream getAuthedTwitterStream() {
		return new TwitterStreamFactory().getInstance(this.twitter.getAuthorization());
	}

	public TweetQuakeUserState getUserState(final long userID) throws TwitterException {
		final TweetQuakeUserState state = new TweetQuakeUserState(userID);
		state.setBlock(ArrayUtils.contains(this.twitter.getBlocksIDs().getIDs(), userID));
		state.setMute(ArrayUtils.contains(this.twitter.getMutesIDs(-1).getIDs(), userID));
		state.setFollow(ArrayUtils.contains(this.twitter.getFriendsIDs(-1).getIDs(), userID));
		return state;
	}
}