package com.bebehp.mc.eewreciever.ping;

public class QuakeLocation {
	public final MyNumber ns;
	public final MyNumber we;

	public QuakeLocation(MyNumber ns, MyNumber we) {
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
		double nsn = ns.getNumber(0).floatValue();
		return ((nsn < 90) ? ("北緯" + nsn) : ("南緯" + (90-nsn))) + "°";
	}

	public String toStringWE()
	{
		double wen = we.getNumber(0).floatValue();
		return ((wen > 90) ? ("東経" + wen) : ("西経" + (90-wen))) + "°";
	}
}