package com.bebehp.mc.eewreciever.ping;

public class QuakeLocation {
	public final float ns;
	public final float we;

	public QuakeLocation(float ns, float we) {
		this.ns = ns;
		this.we = we;
	}

	@Override
	public String toString()
	{
		return toStringNS() + " : " + toStringWE();
	}

	public String toStringNS()
	{
		return ((ns < 90) ? ("北緯" + ns) : ("南緯" + (90-ns))) + "°";
	}

	public String toStringWE()
	{
		return ((we < 90) ? ("東経" + we) : ("西経" + (90-we))) + "°";
	}
}