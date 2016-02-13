package com.bebehp.mc.eewreciever.p2pquake;

import java.text.SimpleDateFormat;

import com.bebehp.mc.eewreciever.ping.AbstractQuakeNode;
import com.bebehp.mc.eewreciever.ping.QuakeException;

public class P2PQuakeNode extends AbstractQuakeNode {
	protected P2PQuakeNodeQuakeType quaketype;
	protected boolean tsunami;
	protected String type;

	@Override
	public P2PQuakeNode parseString(String text) throws QuakeException
	{
		try {
			String[] data = text.split("/");
			String[] time = data[0].split(",");

			this.announcementtime = new SimpleDateFormat("HH:mm:ss").parse(time[0]);
			this.type = time[1];
			this.time = new SimpleDateFormat("dd日HH時mm分").parse(time[2]);
			this.strong = Integer.parseInt(data[1]);
			this.tsunami = "1".equals(data[2]);
			this.quaketype = P2PQuakeNodeQuakeType.parseString(data[3]);
			this.where = data[4];
			this.deep = data[5];
			this.magnitude = Float.parseFloat(data[6]);
			this.modified = "1".equals(data[7]);
			this.location = new P2PQuakeLocation(data[8], data[9]);
		} catch (Exception e) {
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
				" 深さ約" + this.deep +
				" M" + this.magnitude +
				this.time.toString() + "頃発生 " +
				(this.tsunami ?
					"揺れが強かった沿岸部では、念のため津波に注意してください" :
						"この地震による津波の心配はありません。") +
				"[" + this.location + "]";
	}
}
