package com.bebehp.mc.eewreciever.p2pquake;

public enum P2PQuakeNodeTsunami {
	TInfo("揺れが強かった沿岸部では、念のため津波に注意してください。"),
	TNoTsunami("この地震による津波の心配はありません。"),
	TTunami("津波発生の可能性があります。テレビやインターネットなどの情報に警戒して下さい。"),
	TDefault("Unknown");

	private final String name;
	private P2PQuakeNodeTsunami(String str)
	{
		name = str;
	}

	@Override
	public String toString()
	{
		return name;
	}

	public static P2PQuakeNodeTsunami parseInt(int type)
	{
		switch(type)
		{
			case 0:
				return TNoTsunami;
			case 1:
				return TTunami;
			case 2:
				return TInfo;
			default:
				return TDefault;
		}
	}

	public static P2PQuakeNodeTsunami parseString(String type)
	{
		P2PQuakeNodeTsunami qtype;
		try {
			qtype = parseInt(Integer.parseInt(type));
		} catch(NumberFormatException e) {
			qtype = TDefault;
		}
		return qtype;
	}
}
