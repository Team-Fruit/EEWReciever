package com.bebehp.mc.eewreciever.twitter;

public enum AnnouncementType {
	AEEW("緊急地震速報"),
	ACancel("先ほどの誤報発表は間違いでした"),
	ADefault("Unknown");
	private final String name;
	private AnnouncementType(final String str)
	{
		this.name = str;
	}

	@Override
	public String toString()
	{
		return this.name;
	}

	public static AnnouncementType parseInt(final int type)
	{
		switch(type)
		{
		case 0:
		case 8:
		case 9:
			return AEEW;
		case 7:
			return ACancel;
		default:
			return ADefault;
		}
	}

	public static AnnouncementType parseString(final String type)
	{
		AnnouncementType qtype;
		try {
			qtype = parseInt(Integer.parseInt(type));
		} catch(final NumberFormatException e) {
			qtype = ADefault;
		}
		return qtype;
	}
}
