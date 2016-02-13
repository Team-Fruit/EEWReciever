package com.bebehp.mc.eewreciever.ping;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public abstract class AbstractQuakeNode {
	/**
	 * 更新日時
	 */
	protected Date announcementtime;
	/**
	 * 発生日時
	 */
	protected Date time;
	/**
	 * 震度
	 */
	protected int strong;
	/**
	 * 震央
	 */
	protected String where;
	/**
	 * 深さ
	 */
	protected String deep;
	/**
	 * マグニチュード
	 */
	protected float magnitude;
	protected boolean modified;
	protected QuakeLocation location;

	@Override
	public boolean equals(Object o)
	{
		if (o instanceof AbstractQuakeNode)
			return ((AbstractQuakeNode)o).announcementtime == this.announcementtime;
		else
			return false;
	}

	public String toString()
	{
		return "【最大震度" + this.strong + "】(気象庁発表)" +
				this.where +
				" 深さ約" + this.deep +
				" M" + this.magnitude +
				this.time + "頃発生 " +
				"[" + this.location + "]";
	}

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
