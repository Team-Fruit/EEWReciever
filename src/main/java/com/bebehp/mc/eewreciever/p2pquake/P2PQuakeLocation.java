package com.bebehp.mc.eewreciever.p2pquake;

import com.bebehp.mc.eewreciever.ping.QuakeLocation;

public class P2PQuakeLocation extends QuakeLocation {
	public P2PQuakeLocation(float ns, float we) {
		super(ns, we);
	}

	public P2PQuakeLocation(String ns, String we) {
		super(parseNS(ns), parseWE(we));
	}

	public static float parseNS(String ns)
	{
		float nsf = Float.parseFloat(ns.substring(1));
		if ("S".equals(ns.substring(0, 0))) {
			nsf = 180 - nsf;
		}
		return nsf;
	}

	public static float parseWE(String we)
	{
		float wef = Float.parseFloat(we.substring(1));
		if ("W".equals(we.substring(0, 0))) {
			wef = 180 - wef;
		}
		return wef;
	}
}
