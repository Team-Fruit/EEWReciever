package com.bebehp.mc.eewreciever.common.twitter;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.bebehp.mc.eewreciever.common.AbstractQuakeNode;
import com.bebehp.mc.eewreciever.common.QuakeException;

import net.minecraft.util.EnumChatFormatting;

public class TweetQuakeNode extends AbstractQuakeNode {

	protected boolean canceled;
	protected boolean announcement;
	protected int telegramnumber;
	protected boolean finaleew;

	@Override
	public TweetQuakeNode parseString(final String text) throws QuakeException {
		final String[] tnode = Arrays.copyOf(text.split(",", 0), 15);

		this.canceled = "39".equals(tnode[0]);
		this.training = "01".equals(tnode[1]);
		this.announcement = "7".equals(tnode[3]);
		this.finaleew = "9".equals(tnode[3]);
		this.telegramnumber = NumberUtils.isNumber(tnode[4]) ? Integer.parseInt(tnode[4]) : -1;
		this.where = tnode[9];
		this.deep = tnode[10];
		this.magnitude = !StringUtils.isEmpty(tnode[11]) ? (Float.parseFloat(tnode[11]) > 0F) ? tnode[11] : "不明" : null;
		this.strong = tnode[12];
		this.alarm = "1".equals(tnode[14]);
		return this;
	}

	@Override
	public String toString() {
		//		return String.format("{\"text\":\"%s%s%s%s%s %s %skm %s震度%s%s M%s\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"%s%s 地震発生時刻:%s\"}}",
		if (this.canceled) {
			return EnumChatFormatting.RED + "先程の緊急地震速報はキャンセルされました(第" + this.telegramnumber + "報)";
		} else if (this.alarm) {
			return String.format("%s%s%s %s%s%sで地震 予測震度:%s%s%s %skm M:%s (%s)\n%s強い地震が発生しています。身の安全を確保してください。",
					(this.training ? "[訓練報]" : ""),
					EnumChatFormatting.RED,
					(this.announcement ? "緊急地震速報(キャンセル取り消し)" : "緊急地震速報"),
					EnumChatFormatting.AQUA,
					this.where,
					EnumChatFormatting.RESET,
					EnumChatFormatting.LIGHT_PURPLE,
					this.strong,
					EnumChatFormatting.RESET,
					this.deep,
					this.magnitude,
					(this.finaleew ? "最終報" : "第" + this.telegramnumber + "報"),
					EnumChatFormatting.YELLOW
					);
		} else {
			return String.format("%s%s %sで地震 予測震度:%s %skm M:%s (%s)",
					(this.training ? "[訓練報]" : ""),
					(this.announcement ? "緊急地震速報(キャンセル取り消し)" : "緊急地震速報"),
					this.where,
					this.strong,
					this.deep,
					this.magnitude,
					(this.finaleew ? "最終報" : "第" + this.telegramnumber + "報")
					);
		}
	}
}
