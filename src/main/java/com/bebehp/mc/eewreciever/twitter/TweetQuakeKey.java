package com.bebehp.mc.eewreciever.twitter;

public class TweetQuakeKey {

	//	private static final long serialVersionUID = 32L;

	private final String key1;
	private final String key2;

	public TweetQuakeKey(final String key1, final String key2) {
		this.key1 = key1;
		this.key2 = key2;
	}

	public String getKey1() {
		return this.key1;
	}

	public String getKey2() {
		return this.key2;
	}
}
