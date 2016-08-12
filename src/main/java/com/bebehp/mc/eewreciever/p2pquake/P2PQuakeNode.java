package com.bebehp.mc.eewreciever.p2pquake;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import com.bebehp.mc.eewreciever.Reference;
import com.bebehp.mc.eewreciever.ping.AbstractQuakeNode;
import com.bebehp.mc.eewreciever.ping.MyNumber;
import com.bebehp.mc.eewreciever.ping.QuakeException;

import net.minecraft.util.EnumChatFormatting;

public class P2PQuakeNode extends AbstractQuakeNode {
	//	private static final SimpleDateFormat dateformat1 = new SimpleDateFormat("HH:mm:ss");
	private static final SimpleDateFormat dateformat2 = new SimpleDateFormat("dd日HH時mm分");

	protected int quaketype;
	protected P2PQuakeNodeTsunami tsunami;
	protected boolean modified;
	protected boolean unknownMagnitude;

	@Override
	public P2PQuakeNode parseString(final String text) throws QuakeException {
		try {
			final String[] data = Arrays.copyOf(text.split("/"), 10);
			final String[] time = Arrays.copyOf(data[0].split(","), 3);

			this.id = time[0];
			//			this.announcementtime = (time[0] != null) ? dateformat1.parse(time[0]) : null;
			this.alarm = "QUA".equals(time[1]);
			this.time = (time[2] != null) ? dateformat2.parse(time[2]) : null;
			this.strong = data[1];
			this.tsunami = P2PQuakeNodeTsunami.parseString(data[2]);
			this.quaketype = Integer.parseInt(data[3]);
			this.where = data[4];
			this.deep = data[5];
			this.magnitude = new MyNumber(data[6]);
			this.unknownMagnitude = "-1.0".equals(data[6]);
			this.modified = "1".equals(data[7]);
		} catch (final ParseException e) {
			throw new QuakeException("Parse Error", e);
		}
		return this;
	}

	@Override
	public String toString() {
		switch (this.quaketype)
		{
		case 1:
			return String.format("[震度速報]【最大震度%s】(気象庁速報) %s頃発生\n%s",
					this.strong,
					((this.time != null) ? (dateformat2.format(this.time)) : ""),
					this.tsunami
					);
		case 2:
			return String.format("[震源情報]【最大震度%s】%s\n深さ%s%s M%s %s頃発生\n%s",
					this.strong,
					this.where,
					(this.deep.equals("ごく浅い") ? "" : "約"),
					this.deep,
					(this.unknownMagnitude ? "不明" : this.magnitude),
					((this.time != null) ? (dateformat2.format(this.time)) : ""),
					this.tsunami
					);
		case 3:
			return String.format("[震源・詳細情報]【最大震度%s】%s\n深さ%s%s M%s %s頃発生\n%s",
					this.strong,
					this.where,
					(this.deep.equals("ごく浅い") ? "" : "約"),
					this.deep,
					(this.unknownMagnitude ? "不明" : this.magnitude),
					((this.time != null) ? (dateformat2.format(this.time)) : ""),
					this.tsunami
					);
		case 4:
			return String.format("[震源・詳細震度情報]【最大震度%s】%s\n深さ%s%s M%s %s頃発生\n%s",
					this.strong,
					this.where,
					(this.deep.equals("ごく浅い") ? "" : "約"),
					this.deep,
					(this.unknownMagnitude ? "不明" : this.magnitude),
					((this.time != null) ? (dateformat2.format(this.time)) : ""),
					this.tsunami
					);
		case 5:
			return String.format("[遠地地震情報](気象庁発表) %s M%s%s\n%s頃発生(日本時間)\n%s",
					this.where,
					(this.unknownMagnitude ? "不明" : this.magnitude),
					((this.time != null) ? (dateformat2.format(this.time)) : ""),
					this.tsunami
					);
		case 6:
			return "[EEWReciever]気象庁の情報更新を受信しました";
		default:
			Reference.logger.warn("受信した情報は想定外です: " + this.quaketype);
			return EnumChatFormatting.GRAY + "[EEWReciever]サポートしていない情報を受信しました";
		}
	}
}