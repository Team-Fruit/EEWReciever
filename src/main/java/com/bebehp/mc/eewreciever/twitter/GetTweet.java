package com.bebehp.mc.eewreciever.twitter;

import com.bebehp.mc.eewreciever.EEWRecieverMod;

import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class GetTweet {

	public void onStatus(Status status) {

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
		public void onStatus(Status status) {
			// TODO 自動生成されたメソッド・スタブ
			EEWRecieverMod.sendServerChat("テスト" +  status.getText());
		}
		@Override
		public void onTrackLimitationNotice(int arg0) {
			// TODO 自動生成されたメソッド・スタブ

		}
		};
		twitterStream.addListener(listener);

		// フィルター
		FilterQuery filterQuery = new FilterQuery();
		filterQuery.track(new String[] {"from:eewbot"});
		twitterStream.filter(filterQuery);
	 }
}
