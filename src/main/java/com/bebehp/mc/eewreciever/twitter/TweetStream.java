package com.bebehp.mc.eewreciever.twitter;

import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TweetStream
{
	public static void main(String[] args) throws TwitterException
	{
		ConfigurationBuilder cfg = new ConfigurationBuilder();
		cfg.setDebugEnabled(true);
		cfg.setOAuthConsumerKey("mh5mOJhrXkVarLLdNgDn2QFRO");
		cfg.setOAuthConsumerSecret("NbBfZ5ytY47IniUEOoFOIk0wqfOuByzqMzK26DqvH9GhVL0K3E");
		cfg.setOAuthAccessToken("4893957312-30hXziVjdX0ZHzH6OJCv0eWAJmaDgyqR7Wwfjob");
		cfg.setOAuthAccessTokenSecret("ZwqJSMxSFC7lCMmAjgDw3ikwfgnJE9RVyTZt67MYIsMOM");

		TwitterStream twitterStream = new TwitterStreamFactory(cfg.build()).getInstance();
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
			public void onStatus(Status status) {
				// TODO 自動生成されたメソッド・スタブ

			}

			@Override
			public void onTrackLimitationNotice(int arg0) {
				// TODO 自動生成されたメソッド・スタブ

			}
	};

	FilterQuery fq = new FilterQuery();
    String keywords[] = {"from:eewbot", "from:EEWReciever"};

    fq.track(keywords);

    twitterStream.addListener(listener);
    twitterStream.filter(fq);
}
}
