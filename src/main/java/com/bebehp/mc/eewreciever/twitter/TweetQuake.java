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
	private List<AbstractQuakeNode> updatequeue = new LinkedList<AbstractQuakeNode>();
	private Configuration configuration;
	private TwitterStream twitterStream;
	private StatusListener listener;

	public TweetQuake() {
		configuration = new ConfigurationBuilder().setOAuthConsumerKey("mh5mOJhrXkVarLLdNgDn2QFRO")
				.setOAuthConsumerSecret("NbBfZ5ytY47IniUEOoFOIk0wqfOuByzqMzK26DqvH9GhVL0K3E")
				.setOAuthAccessToken("4893957312-30hXziVjdX0ZHzH6OJCv0eWAJmaDgyqR7Wwfjob")
				.setOAuthAccessTokenSecret("ZwqJSMxSFC7lCMmAjgDw3ikwfgnJE9RVyTZt67MYIsMOM").build();
		twitterStream = new TwitterStreamFactory(configuration).getInstance();
		listener = new StatusAdapter() {
			@Override
			public void onStatus(Status status) {
				try {
					String str = new String(status.getText().getBytes("UTF-8"), "UTF-8").intern();
					updatequeue.add(new TweetQuakeNode().parseString(str));
					EEWRecieverMod.logger.info(status.getText());
				} catch (UnsupportedEncodingException e) {
					EEWRecieverMod.logger.error("Encode Error", e);
				} catch (QuakeException e) {
					EEWRecieverMod.logger.error("Recieve Error", e);
				}
			}
		};
		twitterStream.addListener(listener);
		if(ConfigurationHandler.twitterEnable) twitterStream.user();
	}

	@Override
	public List<AbstractQuakeNode> getQuakeUpdate() throws QuakeException {
		if (updatequeue.isEmpty())
			return updatequeue;

		List<AbstractQuakeNode> ret = new LinkedList<AbstractQuakeNode>(updatequeue);
		updatequeue.clear();
		return ret;
	}
}