package com.bebehp.mc.eewreciever.twitter;

import com.bebehp.mc.eewreciever.ping.AbstractQuakeNode;
import com.bebehp.mc.eewreciever.ping.QuakeException;

public class TweetQuakeNode extends AbstractQuakeNode {
	//①フィールドを宣言！！
	protected String teregram;
	@Override
	public TweetQuakeNode parseString(String text) throws QuakeException
	{
		try {
			String[] tnode = text.split(",", 0);

			//②代入！！
			this.teregram = tnode[0];
			//this.training = tnode[1];
			//this.announcementtime =
			//this.announcement = tnode[3]
			//this.telegramnumber = tnode[4]
			//this.quakenumber = tnode[5]
//			this.time =

		} catch (Exception e) {
			throw new QuakeException("parse error", e);
		}
		return this;
	}

	@Override
	public String toString()
	{
		//③書き換え！！
		return "[" + this.quaketype + "]" +
				"【最大震度" + this.strong + "】(気象庁発表)" +
				this.where +
				" 深さ約" + this.deep +
				" M" + this.magnitude +
				this.time.toString() + "頃発生 " +
				"[" + this.point[0] + ":" + this.point[1] + "]";
	}
}
