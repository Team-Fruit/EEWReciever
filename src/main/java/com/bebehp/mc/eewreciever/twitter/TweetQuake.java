package com.bebehp.mc.eewreciever.twitter;

import java.util.LinkedList;
import java.util.List;

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
				String str = (status.getText());

				try {
					updatequeue.add(new TweetQuakeNode().parseString(str));
				} catch (QuakeException e) {
					EEWRecieverMod.logger.error(e);
				}
			}
		};
		twitterStream.addListener(listener);
		twitterStream.sample();
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