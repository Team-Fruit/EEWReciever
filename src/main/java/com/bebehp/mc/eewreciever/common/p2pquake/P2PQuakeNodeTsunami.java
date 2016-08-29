package com.bebehp.mc.eewreciever.common.p2pquake;

import net.minecraft.util.EnumChatFormatting;

public enum P2PQuakeNodeTsunami {
	TInfo("震源が海底ですと、津波の恐れがあります。今後の情報に注意して下さい。"),
	TNoTsunami("この地震による津波の心配はありません。"),
	TTunami(EnumChatFormatting.RED + "[つなみ！にげて！]" + EnumChatFormatting.RESET +
			"この地震による津波に関する警報が発表されています！\n警報が発表されている沿岸部にお住いの方は" +
			EnumChatFormatting.UNDERLINE + "今すぐ避難" + EnumChatFormatting.RESET + "して下さい！"
			),
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
