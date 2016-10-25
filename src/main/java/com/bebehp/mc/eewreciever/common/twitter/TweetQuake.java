package com.bebehp.mc.eewreciever.common.twitter;

import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;

import com.bebehp.mc.eewreciever.EEWRecieverMod;
import com.bebehp.mc.eewreciever.Reference;
import com.bebehp.mc.eewreciever.common.AbstractQuakeNode;
import com.bebehp.mc.eewreciever.common.IQuake;
import com.bebehp.mc.eewreciever.common.QuakeException;
import com.bebehp.mc.eewreciever.common.handler.ConfigurationHandler;

import twitter4j.FilterQuery;
import twitter4j.Status;
import twitter4j.StatusAdapter;
import twitter4j.StatusListener;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

public class TweetQuake implements IQuake {
	private final List<AbstractQuakeNode> updatequeue = new LinkedList<AbstractQuakeNode>();
	private final TwitterStream twitterStream;
	private final Twitter twitter;
	private final StatusListener listener;

	private boolean status = true;

	public TweetQuake() {
		this.twitterStream = new TwitterStreamFactory().getInstance();
		this.twitter = new TwitterFactory().getInstance();
		this.listener = new StatusAdapter() {
			@Override
			public void onStatus(final Status status) {
				try {
					if (status.getInReplyToScreenName()==null&&
							(!status.isRetweet()||ConfigurationHandler.debugMode)&&TweetQuake.this.status) {
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
			if (EEWRecieverMod.tweetQuakeKey!=null) {
				if (EEWRecieverMod.accessToken!=null) {
					this.twitterStream.setOAuthConsumer(EEWRecieverMod.tweetQuakeKey.getKey1(), EEWRecieverMod.tweetQuakeKey.getKey2());
					this.twitterStream.setOAuthAccessToken(EEWRecieverMod.accessToken);
					this.twitterStream.filter(new FilterQuery(214358709L)); //@eewbot
					Reference.logger.info("Starting Twitter Stream");
				} else {
					Reference.logger.info("Twitter authentication was not possible!");
					Reference.logger.info("Plaese try /eew setup");
				}
			} else {
				Reference.logger.error("Could not load the necessary file!");
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

	@Override
	public void setStatus(final boolean enabled) {
		this.status = enabled;
	}
}