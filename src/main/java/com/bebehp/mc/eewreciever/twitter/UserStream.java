package com.bebehp.mc.eewreciever.twitter;

import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class UserStream {
	static final String CONSUMER_KEY = "mh5mOJhrXkVarLLdNgDn2QFRO";
	static final String CONSUMER_SECRET = "NbBfZ5ytY47IniUEOoFOIk0wqfOuByzqMzK26DqvH9GhVL0K3E";
	static final String ACCESS_TOKEN = "4893957312-30hXziVjdX0ZHzH6OJCv0eWAJmaDgyqR7Wwfjob";
	static final String ACCESS_TOKEN_SECRET = "ZwqJSMxSFC7lCMmAjgDw3ikwfgnJE9RVyTZt67MYIsMOM";

//	@Execute(validator=false)
	public String startUserStream()throws Exception
	{
		ConfigurationBuilder builder = new ConfigurationBuilder();
		builder.setOAuthConsumerKey(CONSUMER_KEY);
		builder.setOAuthConsumerSecret(CONSUMER_SECRET);
		builder.setOAuthAccessToken(ACCESS_TOKEN);
		builder.setOAuthAccessTokenSecret(ACCESS_TOKEN_SECRET);
		builder.setUserStreamBaseURL( "https://userstream.twitter.com/2/" );

		Configuration conf = builder.build();

		TwitterStreamFactory twitterStreamFactory = new TwitterStreamFactory(conf);
		TwitterStream twitterStream = twitterStreamFactory.getInstance();
		twitterStream.addListener(new MyUserStreamAdapter());
		twitterStream.user();

		return null;
	}
}
