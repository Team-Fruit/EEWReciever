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
	protected AnnouncementType announcement;
	protected MyNumber telegramnumber;
	protected String quakenumber;
	protected boolean landorsea;
	protected boolean finaleew;

	@Override
	public TweetQuakeNode parseString(final String text) throws QuakeException
	{
		try {
			EEWRecieverMod.logger.info(text);
			//			ArrayList<String> tnode = new ArrayList<String>(15);
			//			tnode.addAll(Arrays.asList(text.split(",", 0)));
			final String[] tnode = Arrays.copyOf(text.split(",", 0), 15);

			//			this.id = tnode[2];
			this.canceled = "39".equals(tnode[0]);
			this.training = "01".equals(tnode[1]);
			this.announcementtime = (tnode[2]!=null) ?dateformat.parse(tnode[2]) : null;
			this.announcement = AnnouncementType.parseString(tnode[3]);
			this.finaleew = "9".equals(tnode[3]);
			this.telegramnumber = new MyNumber(tnode[4]);
			this.quakenumber = tnode[5];
			this.time = (tnode[6]!=null) ?dateformat.parse(tnode[6]) : null;
			this.where = tnode[9];
			this.deep = tnode[10];
			this.magnitude = new MyNumber(tnode[11]);
			this.strong = tnode[12];
			this.landorsea = "1".equals(tnode[13]);
			this.alarm = "1".equals(tnode[14]);

		} catch (final Exception e) {
			throw new QuakeException("parse error", e);
		}
		return this;
	}

	@Override
	public String toString()
	{
		//		return String.format("{\"text\":\"%s%s%s%s%s %s %skm %s震度%s%s M%s\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"%s%s 地震発生時刻:%s\"}}",
		if (this.alarm){
			return String.format("%s%s§c%s§r §b%s§rで地震 予測震度:§d%s§r %skm M:%s (%s)\n§e強い地震が発生しています。身の安全を確保してください。§r",
					(this.training ? "[訓練報]" : ""),
					(this.canceled ? "[誤報]" : ""),
					this.announcement,
					this.where,
					this.strong,
					this.deep,
					this.magnitude,
					(this.finaleew ? "最終報" : String.format("第%s報", this.telegramnumber))
					//				dateformat.format(this.time)
					);
		} else {
			return String.format("%s%s%s %sで地震 %s 予測震度:%s %skm M:%s (%s)",
					(this.training ? "[訓練報]" : ""),
					(this.canceled ? "[誤報]" : ""),
					this.announcement,
					this.where,
					this.strong,
					this.deep,
					this.magnitude,
					(this.finaleew ? "最終報" : String.format("第%s報", this.telegramnumber))
					);
		}
	}
}
