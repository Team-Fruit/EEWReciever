package com.bebehp.mc.eewreciever.ping;

import java.util.ArrayList;
import java.util.Collection;
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
				"【最大震度" + this.strong + "】" +
				this.where +
				"深さ約" + this.deep +
				"M" + this.magnitude +
				this.time + "頃発生" +
				(this.tsunami ?
					"震源が海底です、念のため津波の情報に注意して下さい。" :
						"この地震による津波の心配ありません。") +
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

	public static <E> Collection<E> subtract(final Collection<E> a, final Collection<E> b) {
        ArrayList<E> list = new ArrayList<E>( a );
        for (Iterator<E> it = b.iterator(); it.hasNext();) {
            list.remove(it.next());
        }
        return list;
    }
}
