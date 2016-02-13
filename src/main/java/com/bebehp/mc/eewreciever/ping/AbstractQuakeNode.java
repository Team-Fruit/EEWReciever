package com.bebehp.mc.eewreciever.ping;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public abstract class AbstractQuakeNode {
	protected Date uptime;
	protected String type;
	protected Date time;
	protected int strong;
	protected String quaketype;
	protected String where;
	protected String deep;
	protected float magnitude;
	protected boolean modified;
	protected String[] point;

	@Override
	public boolean equals(Object o)
	{
		if (o instanceof AbstractQuakeNode)
			return ((AbstractQuakeNode)o).uptime == this.uptime;
		else
			return false;
	}

	public abstract String toString();

	public abstract AbstractQuakeNode parseString(String source) throws QuakeException;

	public static List<AbstractQuakeNode> getUpdate(List<AbstractQuakeNode> older, List<AbstractQuakeNode> newer)
	{
		ArrayList<AbstractQuakeNode> list = new ArrayList<AbstractQuakeNode>(newer);
		for (Iterator<AbstractQuakeNode> it = older.iterator(); it.hasNext();) {
			list.remove(it.next());
		}
        return list;
	}
}
