package com.bebehp.mc.eewreciever.twitter;

import java.text.SimpleDateFormat;

import com.bebehp.mc.eewreciever.EEWRecieverMod;
import com.bebehp.mc.eewreciever.ping.AbstractQuakeNode;
import com.bebehp.mc.eewreciever.ping.QuakeException;

public class TweetQuakeNode extends AbstractQuakeNode {
	protected boolean canceled;
	protected boolean training;
	protected AnnouncementType announcement;
	protected int telegramnumber;
	protected String quakenumber;
	protected boolean landorsea;
	protected boolean alarm;

	@Override
	public TweetQuakeNode parseString(String text) throws QuakeException
	{
		try {
			EEWRecieverMod.logger.info(text);
			String[] tnode = text.split(",", 0);

			this.canceled = "39".equals(tnode[0]);
			this.training = "01".equals(tnode[1]);
			this.announcementtime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(tnode[2]);
			this.announcement = AnnouncementType.parseString(tnode[3]);
			this.telegramnumber = Integer.parseInt(tnode[4]);
			this.quakenumber = tnode[5];
			this.time = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(tnode[6]);
			this.location = new TweetQuakeLocation(tnode[7], tnode[8]);
			this.where = tnode[9];
			this.deep = tnode[10] + "km";
			this.magnitude = Float.parseFloat(tnode[11]);
			this.strong = Integer.parseInt(tnode[12]);
			this.landorsea = "1".equals(tnode[13]);
			this.alarm = "1".equals(tnode[14]);

		} catch (Exception e) {
			throw new QuakeException("parse error", e);
		}
		return this;
	}

	@Override
	public String toString()
	{
		return String.format("%s %s %s %s 第%d報\n 最大震度(推定):%s 震央地名:%s マグニチュード(推定):%s 震源の深さ(推定):%s 地震発生時刻%s %s%s",
				(this.training ? "[訓練です]" : ""),
				(this.canceled ? "[誤報]" : ""),
				this.announcement,
				(this.alarm ? "[警報]" : "[予報]"),
				this.telegramnumber,
				this.strong,
				this.where,
				this.magnitude,
				this.deep,
				this.time,
				this.location,
				(this.alarm ? "\n身の安全を確保してください。\n倒れてくる家具などから離れ、机など頑丈な物の下に隠れてください。" : "")
		);
	}
}
