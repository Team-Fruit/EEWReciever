package com.bebehp.mc.eewreciever.twitter;

import java.text.SimpleDateFormat;

import com.bebehp.mc.eewreciever.ping.AbstractQuakeNode;
import com.bebehp.mc.eewreciever.ping.QuakeException;

public class TweetQuakeNode extends AbstractQuakeNode {
	//②フィールドを宣言！！
	protected boolean canceled;
	protected boolean training;
	protected AnnouncementType announcement;
	protected int telegramnumber;
	protected String quakenumber;

	@Override
	public TweetQuakeNode parseString(String text) throws QuakeException
	{
		try {
			String[] tnode = text.split(",", 0);

			//①代入！！
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
			





		} catch (Exception e) {
			throw new QuakeException("parse error", e);
		}
		return this;
	}

	@Override
	public String toString()
	{
		//③書き換え！！
		return /*"[" + this.quaketype + "]" +*/
				(this.training ? "これは訓練です" : "") +
				(this.canceled ? "[誤報]" : "") +
				this.announcement +
				"第" + this.telegramnumber + "報" +
				"地震発表時刻" + this.time +
				"震央地名" + this.where +
				"最大震度(推定)" + this.strong +
				"震源の深さ(推定)"+ this.deep +
				" M" + this.magnitude +
				this.time.toString() + "頃発生 " +
				"[" + this.location + "]";
	}
}
