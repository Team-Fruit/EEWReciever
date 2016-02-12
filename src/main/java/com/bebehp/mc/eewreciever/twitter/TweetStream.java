package com.bebehp.mc.eewreciever.twitter;

import java.util.LinkedList;
import java.util.List;

import com.bebehp.mc.eewreciever.EEWRecieverMod;
import com.bebehp.mc.eewreciever.ping.IAPIPath;
import com.bebehp.mc.eewreciever.ping.QuakeException;
import com.bebehp.mc.eewreciever.ping.QuakeNode;

import twitter4j.Status;
import twitter4j.StatusAdapter;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class TweetStream implements IAPIPath {
	private List<QuakeNode> updatequeue = new LinkedList<QuakeNode>();

	Configuration configuration = new ConfigurationBuilder()
			.setOAuthConsumerKey("mh5mOJhrXkVarLLdNgDn2QFRO")
			.setOAuthConsumerSecret("NbBfZ5ytY47IniUEOoFOIk0wqfOuByzqMzK26DqvH9GhVL0K3E")
			.setOAuthAccessToken("4893957312-30hXziVjdX0ZHzH6OJCv0eWAJmaDgyqR7Wwfjob")
			.setOAuthAccessTokenSecret("ZwqJSMxSFC7lCMmAjgDw3ikwfgnJE9RVyTZt67MYIsMOM")
			.build();

	TwitterStream twitterStream = new TwitterStreamFactory(configuration).getInstance();

	StatusListener listener = new StatusAdapter() {

		@Override
		public  void onStatus(Status status) {
			// TODO 自動生成されたメソッド・スタブ
			String str = (status.getText());
			String[] tnode = str.split(",", 0);

			try {
				updatequeue.add(parseString(str));
			} catch (QuakeException e) {
				EEWRecieverMod.logger.error(e);
			}
		}

	};

	public static QuakeNode parseString(String text) throws QuakeException
	{
		QuakeNode node = new QuakeNode();
		try {
			String[] tnode = text.split(",", 0);

		} catch (Exception e) {
			throw new QuakeException("parse error", e);
		}
		return node;
	}

	@Override
	public List<QuakeNode> getQuakeUpdate() throws QuakeException {
		if(updatequeue.isEmpty()) return updatequeue;

		List<QuakeNode> ret = new LinkedList<QuakeNode>(updatequeue);
		updatequeue.clear();
		return ret;
	}
}