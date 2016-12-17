package net.teamfruit.eewreciever2.common.quake.twitter;

import java.io.IOException;
import java.util.Queue;

import org.apache.commons.codec.binary.StringUtils;

import com.google.common.collect.Queues;

import net.teamfruit.eewreciever2.common.Reference;
import net.teamfruit.eewreciever2.common.quake.IQuake;
import net.teamfruit.eewreciever2.common.quake.IQuakeNode;
import net.teamfruit.eewreciever2.common.quake.QuakeException;
import twitter4j.FilterQuery;
import twitter4j.Status;
import twitter4j.StatusAdapter;
import twitter4j.StatusListener;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.auth.AccessToken;

public class TweetQuake implements IQuake {
	public static final TweetQuake INSTANCE = new TweetQuake(new TweetQuakeSecure().init());

	public boolean isAuthRequired;
	private final TweetQuakeSecure secure;
	private final Queue<IQuakeNode> updatequeue = Queues.newArrayDeque();

	private TweetQuake(final TweetQuakeSecure secure) {
		this.secure = secure;
		init();
	}

	private void init() {
		final TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
		final Twitter twitter = new TwitterFactory().getInstance();
		final StatusListener listener = new StatusAdapter() {
			@Override
			public void onStatus(final Status status) {
				try {
					if (status.getInReplyToScreenName()==null&&!status.isRetweet()) {
						final String str = StringUtils.newStringUtf8(StringUtils.getBytesUtf8(status.getText())).intern();
						TweetQuake.this.updatequeue.add(new TweetQuakeNode().parseString(str));
					}
				} catch (final QuakeException e) {
					Reference.logger.error("Recieve Error", e);
				}
			}
		};
		twitterStream.addListener(listener);

		if (this.secure.isKeyValid()) {
			if (this.secure.isTokenValid()) {
				twitterStream.setOAuthConsumer(this.secure.getTweetQuakeKey().getKey1(), this.secure.getTweetQuakeKey().getKey2());
				twitterStream.setOAuthAccessToken(this.secure.getAccessToken());
				twitterStream.filter(new FilterQuery(214358709L)); //@eewbot
				Reference.logger.info("Starting Twitter Stream");
			} else
				this.isAuthRequired = true;
		} else
			Reference.logger.warn("Because there was a problem, we could not connect to Twitter.");
	}

	protected TweetQuake setAccessToken(final AccessToken token) throws IOException {
		this.secure.setAccessToken(token).storeAccessToken(token);
		return this;
	}

	public void connect() {
		if (this.isAuthRequired)
			init();
	}

	@Override
	public Queue<IQuakeNode> getQuakeUpdate() throws QuakeException {
		if (this.updatequeue.isEmpty())
			return this.updatequeue;

		final Queue<IQuakeNode> ret = Queues.newArrayDeque(this.updatequeue);
		this.updatequeue.clear();
		return ret;
	}
}
