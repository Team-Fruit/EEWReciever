package com.bebehp.mc.eewreciever.twitter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.bebehp.mc.eewreciever.ping.IAPIPath;
import com.bebehp.mc.eewreciever.ping.QuakeException;
import com.bebehp.mc.eewreciever.ping.QuakeNode;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class TweetStream implements IAPIPath {
	Configuration configuration = new ConfigurationBuilder()
			.setOAuthConsumerKey("mh5mOJhrXkVarLLdNgDn2QFRO")
			.setOAuthConsumerSecret("NbBfZ5ytY47IniUEOoFOIk0wqfOuByzqMzK26DqvH9GhVL0K3E")
			.setOAuthAccessToken("4893957312-30hXziVjdX0ZHzH6OJCv0eWAJmaDgyqR7Wwfjob")
			.setOAuthAccessTokenSecret("ZwqJSMxSFC7lCMmAjgDw3ikwfgnJE9RVyTZt67MYIsMOM")
			.build();

	TwitterStream twitterStream = new TwitterStreamFactory(configuration).getInstance();

	StatusListener listener = new StatusListener() {

		@Override
		public void onException(Exception arg0) {
			// TODO 自動生成されたメソッド・スタブ

		}

		@Override
		public void onDeletionNotice(StatusDeletionNotice arg0) {
			// TODO 自動生成されたメソッド・スタブ

		}

		@Override
		public void onScrubGeo(long arg0, long arg1) {
			// TODO 自動生成されたメソッド・スタブ

		}

		@Override
		public void onStallWarning(StallWarning arg0) {
			// TODO 自動生成されたメソッド・スタブ

		}

		@Override
		public  void onStatus(Status status) {
			// TODO 自動生成されたメソッド・スタブ
			String str = (status.getText());
			String[] tnode = str.split(",", 0);

			List<String> list = new ArrayList<String>();
			Collections.addAll(list,tnode[0],tnode[1],tnode[2],tnode[3],tnode[4],tnode[5],tnode[6],tnode[7],tnode[8],tnode[9],tnode[10],tnode[11],tnode[12],tnode[13],tnode[14],tnode[15]);

		}

		@Override
		public void onTrackLimitationNotice(int arg0) {
			// TODO 自動生成されたメソッド・スタブ

		}

	};

	@Override
	public List<QuakeNode> getQuake() throws QuakeException {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}
}