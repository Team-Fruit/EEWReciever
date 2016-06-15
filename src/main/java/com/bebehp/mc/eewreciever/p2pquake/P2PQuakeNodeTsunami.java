package com.bebehp.mc.eewreciever.p2pquake;

public enum P2PQuakeNodeTsunami {
	TInfo("震源が海底ですと、津波の恐れがあります。今後の情報に注意して下さい。"),
	TNoTsunami("この地震による津波の心配はありません。"),
	TTunami("§c§o[つなみ！にげて！]§rこの地震による津波に関する警報が発表されています！\n"
			+ "警報が発表されている沿岸部にお住いの方は§n今すぐ避難§rして下さい！\n"),
	TUnknown("津波に関する情報は取得できませんでした。"),
	TDefault("Unknown");

	private final String name;
	private P2PQuakeNodeTsunami(final String str)
	{
		this.name = str;
	}

	@Override
	public String toString()
	{
		return this.name;
	}

	public static P2PQuakeNodeTsunami parseInt(final int type)
	{
		switch(type)
		{
		case 0:
			return TNoTsunami;
		case 1:
			return TTunami;
		case 2:
			return TInfo;
		case 3:
			return TUnknown;
		default:
			return TDefault;
		}
	}

	public static P2PQuakeNodeTsunami parseString(final String type)
	{
		P2PQuakeNodeTsunami ttype;
		try {
			ttype = parseInt(Integer.parseInt(type));
		} catch(final NumberFormatException e) {
			ttype = TDefault;
		}
		return ttype;
	}
}
