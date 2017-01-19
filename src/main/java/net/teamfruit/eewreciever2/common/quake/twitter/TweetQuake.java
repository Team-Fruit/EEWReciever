package net.teamfruit.eewreciever2.common.quake.twitter;

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
import twitter4j.TwitterStream;

public class TweetQuake implements IQuake {
	public static final TweetQuake INSTANCE = new TweetQuake();
	public boolean isAuthRequired;
	private final Queue<IQuakeNode> updatequeue = Queues.newArrayDeque();

	private TweetQuake() {
		init();
	}

	public Queue<IQuakeNode> queue() {
		return this.updatequeue;
	}

	private void init() {
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

		if (TweetQuakeManager.intance().isKeyValid()) {
			if (TweetQuakeManager.intance().isTokenValid()) {
				final TwitterStream twitterStream = TweetQuakeManager.intance().getAuthedTwitterStream();
				twitterStream.addListener(listener);
				twitterStream.filter(new FilterQuery(214358709L)); //@eewbot
				Reference.logger.info("Starting Twitter Stream");
				this.isAuthRequired = false;
			} else
				this.isAuthRequired = true;
		} else
			Reference.logger.warn("Because there was a problem, we could not connect to Twitter.");
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
