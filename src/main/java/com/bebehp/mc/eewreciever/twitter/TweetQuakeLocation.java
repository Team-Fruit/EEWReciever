package com.bebehp.mc.eewreciever.twitter;

import com.bebehp.mc.eewreciever.ping.MyNumber;
import com.bebehp.mc.eewreciever.ping.QuakeLocation;

//@Deprecated
public class TweetQuakeLocation extends QuakeLocation {
	public TweetQuakeLocation(String ns, String we) {
		super(new MyNumber(ns), new MyNumber(we));
	}
}
