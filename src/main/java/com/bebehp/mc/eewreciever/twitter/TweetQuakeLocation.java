package com.bebehp.mc.eewreciever.twitter;

import com.bebehp.mc.eewreciever.ping.QuakeLocation;

public class TweetQuakeLocation extends QuakeLocation {
	public TweetQuakeLocation(String ns, String we) {
		super(Float.parseFloat(ns), Float.parseFloat(we));
	}
}
