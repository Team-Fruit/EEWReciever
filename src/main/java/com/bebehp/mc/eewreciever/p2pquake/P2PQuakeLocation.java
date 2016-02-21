package com.bebehp.mc.eewreciever.p2pquake;

import com.bebehp.mc.eewreciever.ping.MyNumber;
import com.bebehp.mc.eewreciever.ping.QuakeLocation;

public class P2PQuakeLocation extends QuakeLocation {
	public P2PQuakeLocation(String ns, String we) {
		super(parseNS(ns), parseWE(we));
	}

	public static MyNumber parseNS(String ns)
	{
		MyNumber nsf = new MyNumber(ns.substring(1));
		if ("S".equals(ns.substring(0, 0))) {
			nsf = new MyNumber(180 - nsf.getNumber(0).floatValue());
		}
		return nsf;
	}

	public static MyNumber parseWE(String we)
	{
		MyNumber wef = new MyNumber(we.substring(1));
		if ("W".equals(we.substring(0, 0))) {
			wef = new MyNumber(180 - wef.getNumber(0).floatValue());
		}
		return wef;
	}
}
