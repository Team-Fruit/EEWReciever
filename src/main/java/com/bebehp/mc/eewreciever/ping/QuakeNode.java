package com.bebehp.mc.eewreciever.ping;

public class QuakeNode {
	public long uptime;
	public String type;
	public String time;
	public int strong;
	public boolean tsunami;
	public String quaketype;
	public String where;
	public String deep;
	public float magnitude;
	public boolean modified;
	public String[] point;

	@Override
	public boolean equals(Object o)
	{
		if (o instanceof QuakeNode)
			return ((QuakeNode)o).uptime == this.uptime;
		else
			return false;
	}
}
