package com.bebehp.mc.eewreciever.twitter;

import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;

import com.bebehp.mc.eewreciever.ConfigurationHandler;
import com.bebehp.mc.eewreciever.Reference;
import com.bebehp.mc.eewreciever.ping.AbstractQuakeNode;
import com.bebehp.mc.eewreciever.ping.IQuake;
import com.bebehp.mc.eewreciever.ping.QuakeException;

import twitter4j.FilterQuery;
import twitter4j.Status;
import twitter4j.StatusAdapter;
import twitter4j.StatusListener;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class TweetQuake implements IQuake {
	private final List<AbstractQuakeNode> updatequeue = new LinkedList<AbstractQuakeNode>();
	private final TweetQuakeKey tweetQuakeKey;
	private final Configuration configuration;
	private final TwitterStream twitterStream;
	private final StatusListener listener;
	private final Twitter twitter;

	public TweetQuake() {
		this.tweetQuakeKey = TweetQuakeFileHelper.loadKey();
		this.configuration = new ConfigurationBuilder()
				.setOAuthConsumerKey(this.tweetQuakeKey.getKey1())
				.setOAuthAccessTokenSecret(this.tweetQuakeKey.getKey2())
				.build();
		this.twitterStream = new TwitterStreamFactory(this.configuration).getInstance();
		this.twitter = new TwitterFactory().getInstance();
		this.listener = new StatusAdapter() {
			@Override
			public void onStatus(final Status status) {
				try {
					if (status.getInReplyToScreenName() == null && (!status.isRetweet() || ConfigurationHandler.debugMode)) {
						final String str = new String(status.getText().getBytes("UTF-8"), "UTF-8").intern();
						TweetQuake.this.updatequeue.add(new TweetQuakeNode().parseString(str));
					}
				} catch (final UnsupportedEncodingException e) {
					Reference.logger.error("Encode Error", e);
				} catch (final QuakeException e) {
					Reference.logger.error("Recieve Error", e);
				}
			}
		};
		this.twitterStream.addListener(this.listener);
		if (ConfigurationHandler.twitterEnable) {
			final AccessToken accessToken = TweetQuakeFileHelper.loadAccessToken();
			if (accessToken != null) {
				this.twitter.setOAuthAccessToken(accessToken);
				final long[] list = {214358709L}; //from:eewbot;
				final FilterQuery query = new FilterQuery(list);
				this.twitterStream.filter(query);
			} else {
				Reference.logger.warn("Twitter authentication was not possible!");
				Reference.logger.info("Plaese try /eewreciever setup");
			}
		}
	}

	@Override
	public List<AbstractQuakeNode> getQuakeUpdate() throws QuakeException {
		if (this.updatequeue.isEmpty())
			return this.updatequeue;

		final List<AbstractQuakeNode> ret = new LinkedList<AbstractQuakeNode>(this.updatequeue);
		this.updatequeue.clear();
		return ret;
	}
}