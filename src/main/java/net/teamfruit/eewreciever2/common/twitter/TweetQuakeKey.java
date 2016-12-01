package net.teamfruit.eewreciever2.common.twitter;

import java.io.Serializable;
import java.util.Arrays;

public final class TweetQuakeKey implements Serializable {
	private static final long serialVersionUID = -7063613724826185974L;

	private static final char destroy = 0;

	private char[] key1;
	private char[] key2;

	private TweetQuakeKey() {
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
