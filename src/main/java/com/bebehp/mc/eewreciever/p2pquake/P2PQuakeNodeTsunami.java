package com.bebehp.mc.eewreciever.p2pquake;

public enum P2PQuakeNodeTsunami {
	TInfo("揺れが強かった沿岸部では、念のため津波に注意してください。"),
	TNoTsunami("この地震による津波の心配はありません。"),
	TTunami("§4§o[つなみ！にげて！]§rこの地震による津波に関する警報が発表されています！\n"
			+ "警報が発表されている沿岸部にお住いの方は§n今すぐ避難§rして下さい！\n"
			+ "§8(この情報は津波予報(若干の海面変動)等の際にも表示されます)§r \n"),
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
		P2PQuakeNodeTsunami ttype;
		try {
			ttype = parseInt(Integer.parseInt(type));
		} catch(NumberFormatException e) {
			ttype = TDefault;
		}
		return ttype;
	}
}
