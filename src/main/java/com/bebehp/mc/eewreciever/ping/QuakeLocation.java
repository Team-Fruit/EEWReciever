package com.bebehp.mc.eewreciever.ping;

//@Deprecated
public class QuakeLocation {
	public static final String format = "%.1f";

	public final MyNumber ns;
	public final MyNumber we;

	public QuakeLocation(MyNumber ns, MyNumber we) {
		this.ns = ns;
		this.we = we;
	}

	@Override
	public String toString()
	{
		if (ns != null && we != null)
			return toStringNS() + " : " + toStringWE();
		else
			return "";
	}

	public String toStringNS()
	{
		double nsn = ns.getNumber(0).floatValue();
		return ((nsn < 90) ? ("北緯" + String.format(format, nsn)) : ("南緯" + String.format(format, 90-nsn))) + "°";
	}

	public String toStringWE()
	{
		double wen = we.getNumber(0).floatValue();
		return ((wen > 90) ? ("東経" + String.format(format, wen)) : ("西経" + String.format(format, 90-wen))) + "°";
	}
}