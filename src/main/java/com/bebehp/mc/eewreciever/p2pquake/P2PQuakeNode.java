package com.bebehp.mc.eewreciever.p2pquake;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import com.bebehp.mc.eewreciever.ping.AbstractQuakeNode;
import com.bebehp.mc.eewreciever.ping.MyNumber;
import com.bebehp.mc.eewreciever.ping.QuakeException;

public class P2PQuakeNode extends AbstractQuakeNode {
	private static final SimpleDateFormat dateformat1 = new SimpleDateFormat("HH:mm:ss");
	private static final SimpleDateFormat dateformat2 = new SimpleDateFormat("dd日HH時mm分");

	protected P2PQuakeNodeQuakeType quaketype;
	protected P2PQuakeNodeTsunami tsunami;
	protected boolean modified;
	protected String type;

	@Override
	public P2PQuakeNode parseString(String text) throws QuakeException
	{
		try {
			String[] data = Arrays.copyOf(text.split("/"), 10);
			String[] time = Arrays.copyOf(data[0].split(","), 3);

			this.announcementtime = (time[0]!=null) ? dateformat1.parse(time[0]) : null;
			this.type = time[1];
			this.alarm = "QUA".equals(time[1]);
			this.time = (time[2]!=null) ? dateformat2.parse(time[2]) : null;
			this.strong = data[1];
			this.tsunami = P2PQuakeNodeTsunami.parseString(data[2]);
			this.quaketype = P2PQuakeNodeQuakeType.parseString(data[3]);
			this.where = data[4];
			this.deep = data[5];
			this.magnitude = new MyNumber(data[6]);
			this.modified = "1".equals(data[7]);
			this.location = new P2PQuakeLocation(data[8], data[9]);
		} catch (Exception e) {
//			e.printStackTrace();
			throw new QuakeException("parse error", e);
		}
		return this;
	}

	@Override
	public String toString()
	{
		return "[" + this.quaketype + "]" +
				"【最大震度" + this.strong + "】(気象庁発表)" +
				this.where +
				" 深さ" + this.deep +
				this.magnitude.format((this.magnitude.getNumber(-1f).doubleValue() >= 0 ? " M %d" : "不明"), "不明") +
				((this.time!=null) ? (this.time + "頃発生\n") : "") +
				this.tsunami +
//				(this.tsunami ?
//					"揺れが強かった沿岸部では、念のため津波に注意してください" :
//						"この地震による津波の心配はありません。") +
				"[" + this.location + "]";
	}
}
