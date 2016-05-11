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
//	protected QuakeLocation location;
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

	@Override
	public String toString()
	{
		return String.format("[%s] 【最大震度%s】(気象庁発表) %s\n深さ約%s M%s %s頃発生\n%s",
				this.strong,
				this.where,
				this.deep,
				this.magnitude
			);
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
