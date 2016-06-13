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
	protected boolean finaleew;


	@Override
	public TweetQuakeNode parseString(final String text) throws QuakeException
	{
		try {
			EEWRecieverMod.logger.info(text);
			//			ArrayList<String> tnode = new ArrayList<String>(15);
			//			tnode.addAll(Arrays.asList(text.split(",", 0)));
			final String[] tnode = Arrays.copyOf(text.split(",", 0), 15);

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

	//	@Override
	//	public String toString()
	//	{
	//		return String.format("%s%s%s%s%s %s %s\n%s 震源:%s %s\n深さ:%skm %s",
	//				(this.training ? "[訓練です]" : ""),
	//				(this.canceled ? "[誤報]" : ""),
	//				(this.alarm ? "§c" : ""),
	//				this.announcement,
	//				(this.alarm ? "[警報]強い揺れに警戒！" : "[予報]"),
	//				(this.alarm ? "§r" : ""),
	//				this.telegramnumber.format("第%s報", ""),
	//				String.format("最大震度:%s", this.strong),
	//				this.where,
	//				this.magnitude.format("マグニチュード:%s", ""),
	//				this.deep,
	//				dateformat.format(this.time),
	//				(this.alarm ? "\n§6強い地震発生の可能性。身の安全を図り、今後の情報に警戒して下さい。§r" : "")
	//				);
	//	}

	@Override
	public String toString()
	{
		//		return String.format("{\"text\":\"%s%s%s%s%s %s %skm %s震度%s%s M%s\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"%s%s 地震発生時刻:%s\"}}",
		return String.format("%s%s%s%s%s %s %skm %s震度%s%s M%s",
				(this.training ? "[訓練報]" : ""),
				(this.canceled ? "[誤報]" : ""),
				(this.alarm ? "§c" : ""),
				this.announcement,
				(this.alarm ? "§r" : ""),
				this.where,
				this.deep,
				(this.alarm ? "§c" : ""),
				this.strong,
				(this.alarm ? "§r" : ""),
				this.magnitude,
				(this.alarm ? "§c[警報]§r" : "[予報]"),
				(this.finaleew ? "最終報" : String.format("第%s報", this.telegramnumber)),
				dateformat.format(this.time)
				);
	}
}
