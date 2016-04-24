package com.bebehp.mc.eewreciever.p2pquake;

public enum P2PQuakeNodeQuakeType {
	QUpdate("情報更新"),
	QSpeedLevelInfo("震度速報"),
	QCoreInfo("震源情報"),
	QCoreLevelInfo("震源・震度情報"),
	QCoreDetailLevelInfo("震源・詳細震度情報"),
	QFarInfo("遠地地震情報"),
	QDefault("Unknown");

	private final String name;
	private P2PQuakeNodeQuakeType(String str)
	{
		name = str;
	}

	@Override
	public String toString()
	{
		return name;
	}

	public static P2PQuakeNodeQuakeType parseInt(int type)
	{
		switch(type)
		{
			case 0:
				return QUpdate;
			case 1:
				return QSpeedLevelInfo;
			case 2:
				return QCoreInfo;
			case 3:
				return QCoreLevelInfo;
			case 4:
				return QCoreDetailLevelInfo;
			case 5:
				return QFarInfo;
			default:
				return QDefault;
		}
	}

	public static P2PQuakeNodeQuakeType parseString(String type)
	{
		P2PQuakeNodeQuakeType qtype;
		try {
			qtype = parseInt(Integer.parseInt(type));
		} catch(NumberFormatException e) {
			qtype = QDefault;
		}
		return qtype;
	}
}
