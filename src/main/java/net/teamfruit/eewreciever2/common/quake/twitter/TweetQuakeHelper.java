package net.teamfruit.eewreciever2.common.quake.twitter;

import java.io.IOException;

import twitter4j.Twitter;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.auth.AccessToken;

public class TweetQuakeHelper {

	private static TweetQuakeSecure secure = new TweetQuakeSecure().init();
	private static Twitter twitter = secure.getAuthedTwitter();

	private TweetQuakeHelper() {
	}

	public static TweetQuakeAuther getAuther() {
		return new TweetQuakeAuther(twitter);
	}

	public static void setAccessToken(final AccessToken token) throws IOException {
		secure.storeAccessToken(token);
	}

	public static boolean isKeyValid() {
		return secure.isKeyValid();
	}

	public static boolean isTokenValid() {
		return secure.isTokenValid();
	}

	public static Twitter getAuthedTwitter() {
		return twitter;
	}

	public static TwitterStream getAuthedTwitterStream() {
		return new TwitterStreamFactory().getInstance(twitter.getAuthorization());
	}
}
