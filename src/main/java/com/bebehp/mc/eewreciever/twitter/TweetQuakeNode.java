package com.bebehp.mc.eewreciever.twitter;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import com.bebehp.mc.eewreciever.EEWRecieverMod;
import com.bebehp.mc.eewreciever.ping.AbstractQuakeNode;
import com.bebehp.mc.eewreciever.ping.MyNumber;
import com.bebehp.mc.eewreciever.ping.QuakeException;

public class TweetQuakeNode extends AbstractQuakeNode {
	private static final SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	protected boolean canceled;
	protected boolean training;
	protected AnnouncementType announcement;
	protected MyNumber telegramnumber;
	protected String quakenumber;
	protected boolean landorsea;
	protected boolean alarm;

	@Override
	public TweetQuakeNode parseString(String text) throws QuakeException
	{
		try {
			EEWRecieverMod.logger.info(text);
//			ArrayList<String> tnode = new ArrayList<String>(15);
//			tnode.addAll(Arrays.asList(text.split(",", 0)));
			String[] tnode = Arrays.copyOf(text.split(",", 0), 15);

			this.canceled = "39".equals(tnode[0]);
			this.training = "01".equals(tnode[1]);
			this.announcementtime = dateformat.parse(tnode[2]);
			this.announcement = AnnouncementType.parseString(tnode[3]);
			this.telegramnumber = new MyNumber(tnode[4]);
			this.quakenumber = tnode[5];
			this.time = dateformat.parse(tnode[6]);
			this.location = new TweetQuakeLocation(tnode[7], tnode[8]);
			this.where = tnode[9];
			this.deep = tnode[10];
			this.magnitude = new MyNumber(tnode[11]);
			this.strong = new MyNumber(tnode[12]);
			this.landorsea = "1".equals(tnode[13]);
			this.alarm = "1".equals(tnode[14]);

/*			this.canceled = "39".equals(tnode.get(0));
			this.training = "01".equals(tnode.get(1));
			this.announcementtime = dateformat.parse(tnode.get(2));
			this.announcement = AnnouncementType.parseString(tnode.get(3));
			this.telegramnumber = new MyNumber(tnode.get(4));
			this.quakenumber = tnode.get(5);
			this.time = dateformat.parse(tnode.get(6));
			this.location = new TweetQuakeLocation(tnode.get(7), tnode.get(8));
			this.where = tnode.get(9);
			this.deep = tnode.get(10);
			this.magnitude = new MyNumber(tnode.get(11));
			this.strong = new MyNumber(tnode.get(12));
			this.landorsea = "1".equals(tnode.get(13));
			this.alarm = "1".equals(tnode.get(14));
*/

		} catch (Exception e) {
			throw new QuakeException("parse error", e);
		}
		return this;
	}

	@Override
	public String toString()
	{
		return String.format("%s%s%s %s %s\n %s 震央地名:%s %s 震源の深さ(推定):%skm 地震発生時刻%s %s%s",
				(this.training ? "[訓練です]" : ""),
				(this.canceled ? "[誤報]" : ""),
				this.announcement,
				(this.alarm ? "[警報]" : "[予報]"),
				this.telegramnumber.format("第%s報", ""),
				this.strong.format("最大震度(推定):%s", ""),
				this.where,
				this.magnitude.format("マグニチュード(推定):%s", ""),
				this.deep,
				this.time,
				this.location,
				(this.alarm ? "\n身の安全を確保してください。\n倒れてくる家具などから離れ、机など頑丈な物の下に隠れてください。" : "")
		);
	}
}
