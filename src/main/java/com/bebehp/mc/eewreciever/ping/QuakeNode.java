package com.bebehp.mc.eewreciever.ping;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

	@Override
	public String toString()
	{
		return "[" + this.quaketype + "]" +
				"【最大震度" + this.strong + "】(気象庁発表)" +
				this.where +
				" 深さ約" + this.deep +
				" M" + this.magnitude + " " +
				this.time + "頃発生 " +
				(this.tsunami ?
					"揺れが強かった沿岸部では、念のため津波に注意してください" :
						"この地震による津波の心配はありません。") +
				"[" + this.point[0] + ":" + this.point[1] + "]";
	}

	public static List<QuakeNode> getUpdate(List<QuakeNode> older, List<QuakeNode> newer)
	{
		ArrayList<QuakeNode> list = new ArrayList<QuakeNode>(newer);
		for (Iterator<QuakeNode> it = older.iterator(); it.hasNext();) {
			list.remove(it.next());
		}
        return list;
	}
}
