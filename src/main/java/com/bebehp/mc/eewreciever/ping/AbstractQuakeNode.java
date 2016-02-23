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
	protected String strong;
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
	protected MyNumber magnitude;
	protected QuakeLocation location;
	protected boolean alarm;

	public boolean isAlarm() {
		return alarm;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this.announcementtime == null && o == null)
			return true;
		if (this.announcementtime != null && o instanceof AbstractQuakeNode)
			return this.announcementtime.equals(((AbstractQuakeNode)o).announcementtime);
		else
			return false;
	}

	public String toString()
	{
		return "【最大震度" + this.strong + "】(気象庁発表)" +
				this.where +
				" 深さ" + this.deep +
				this.magnitude.format((this.magnitude.getNumber(-1f).doubleValue() >= 0 ? " M %d" : "不明"), "不明") +
				((this.time!=null) ? (this.time + "頃発生\n") : "") +
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
