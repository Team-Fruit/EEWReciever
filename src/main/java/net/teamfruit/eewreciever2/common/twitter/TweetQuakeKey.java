package net.teamfruit.eewreciever2.common.twitter;

import java.io.Serializable;
import java.util.Arrays;

public final class TweetQuakeKey implements Serializable {
	private static final char destroy = 0;

	private final char[] key1;
	private final char[] key2;

	public TweetQuakeKey(final String key1, final String key2) {
		this.key1 = key1.toCharArray();
		this.key2 = key2.toCharArray();
	}

	public String getKey1() {
		return String.valueOf(this.key1);
	}

	public String getKey2() {
		return String.valueOf(this.key2);
	}

	public void destroy() {
		Arrays.fill(this.key1, destroy);
		Arrays.fill(this.key2, destroy);
	}

}
