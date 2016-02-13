package com.bebehp.mc.eewreciever.twitter;

public enum AnnouncementType {
	AEEW("緊急地震速報"),
	ACancel("先ほどの誤報発表は間違いでした"),
	AEEWLast("緊急地震速報(最終報)"),
	ADefault("Unknown");
	private final String name;
	private AnnouncementType(String str)
	{
		name = str;
	}

	@Override
	public String toString()
	{
		return name;
	}

	public static AnnouncementType parseInt(int type)
	{
		switch(type)
		{
			case 0:
				return AEEW;
			case 7:
				return ACancel;
			case 8:
			case 9:
				return AEEWLast;
			default:
				return ADefault;
		}
	}

	public static AnnouncementType parseString(String type)
	{
		AnnouncementType qtype;
		try {
			qtype = parseInt(Integer.parseInt(type));
		} catch(NumberFormatException e) {
			qtype = ADefault;
		}
		return qtype;
	}
}
