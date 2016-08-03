package com.bebehp.mc.eewreciever.twitter;

import java.util.Arrays;

import com.bebehp.mc.eewreciever.ping.AbstractQuakeNode;
import com.bebehp.mc.eewreciever.ping.MyNumber;
import com.bebehp.mc.eewreciever.ping.QuakeException;

public class TweetQuakeNode extends AbstractQuakeNode {

	protected boolean canceled;
	protected boolean announcement;
	protected MyNumber telegramnumber;
	protected boolean finaleew;

	@Override
	public TweetQuakeNode parseString(final String text) throws QuakeException {
		final String[] tnode = Arrays.copyOf(text.split(",", 0), 15);

		this.canceled = "39".equals(tnode[0]);
		this.training = "01".equals(tnode[1]);
		this.announcement = "7".equals(tnode[3]);
		this.finaleew = "9".equals(tnode[3]);
		this.telegramnumber = new MyNumber(tnode[4]);
		this.where = tnode[9];
		this.deep = tnode[10];
		this.magnitude = new MyNumber(tnode[11]);
		this.strong = tnode[12];
		this.alarm = "1".equals(tnode[14]);
		return this;
	}

	@Override
	public String toString() {
		//		return String.format("{\"text\":\"%s%s%s%s%s %s %skm %s震度%s%s M%s\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"%s%s 地震発生時刻:%s\"}}",
		if (this.canceled) {
			return "先程の緊急地震速報はキャンセルされました(第" + this.telegramnumber + "報)";
		} else if (this.alarm) {
			return String.format("%s§c%s§r §b%s§rで地震 予測震度:§d%s§r %skm M:%s (%s)\n§e強い地震が発生しています。身の安全を確保してください。§r",
					(this.training ? "[訓練報]" : ""),
					(this.announcement ? "緊急地震速報(キャンセル取り消し)" : "緊急地震速報"),
					this.where,
					this.strong,
					this.deep,
					this.magnitude,
					(this.finaleew ? "最終報" : String.format("第%s報", this.telegramnumber))
					//				dateformat.format(this.time)
					);
		} else {
			return String.format("%s%s %sで地震 予測震度:%s %skm M:%s (%s)",
					(this.training ? "[訓練報]" : ""),
					(this.announcement ? "緊急地震速報(キャンセル取り消し)" : "緊急地震速報"),
					this.where,
					this.strong,
					this.deep,
					this.magnitude,
					(this.finaleew ? "最終報" : String.format("第%s報", this.telegramnumber))
					);
		}
	}
}
