package com.bebehp.mc.eewreciever.twitter;

import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;

import com.bebehp.mc.eewreciever.ConfigurationHandler;
import com.bebehp.mc.eewreciever.EEWRecieverMod;
import com.bebehp.mc.eewreciever.ping.AbstractQuakeNode;
import com.bebehp.mc.eewreciever.ping.IQuake;
import com.bebehp.mc.eewreciever.ping.QuakeException;

import twitter4j.Status;
import twitter4j.StatusAdapter;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class TweetQuake implements IQuake {
	private final List<AbstractQuakeNode> updatequeue = new LinkedList<AbstractQuakeNode>();
	private final Configuration configuration;
	private final TwitterStream twitterStream;
	private final StatusListener listener;

	public TweetQuake() {
		this.configuration = new ConfigurationBuilder().setOAuthConsumerKey("mh5mOJhrXkVarLLdNgDn2QFRO")
				.setOAuthConsumerSecret("NbBfZ5ytY47IniUEOoFOIk0wqfOuByzqMzK26DqvH9GhVL0K3E")
				.setOAuthAccessToken("4893957312-30hXziVjdX0ZHzH6OJCv0eWAJmaDgyqR7Wwfjob")
				.setOAuthAccessTokenSecret("ZwqJSMxSFC7lCMmAjgDw3ikwfgnJE9RVyTZt67MYIsMOM").build();
		this.twitterStream = new TwitterStreamFactory(this.configuration).getInstance();
		this.listener = new StatusAdapter() {
			@Override
			public void onStatus(final Status status) {
				try {
					final String str = new String(status.getText().getBytes("UTF-8"), "UTF-8").intern();
					TweetQuake.this.updatequeue.add(new TweetQuakeNode().parseString(str));
					EEWRecieverMod.logger.info(status.getText());
				} catch (final UnsupportedEncodingException e) {
					EEWRecieverMod.logger.error("Encode Error", e);
				} catch (final QuakeException e) {
					EEWRecieverMod.logger.error("Recieve Error", e);
				}
			}
		};
		this.twitterStream.addListener(this.listener);
		//		if (ConfigurationHandler.debugMode && ConfigurationHandler.twitterEnable)
		//			this.twitterStream.user();
		//		else if (ConfigurationHandler.twitterEnable) {
		//			final FilterQuery query = new FilterQuery();
		//			query.track(new String[] { "from:eewbot" });
		//			this.twitterStream.filter(query);
		//	}
		if (ConfigurationHandler.twitterEnable) this.twitterStream.user();
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