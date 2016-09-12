package com.bebehp.mc.eewreciever.common.twitter;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.bebehp.mc.eewreciever.common.AbstractQuakeNode;
import com.bebehp.mc.eewreciever.common.QuakeException;

import net.minecraft.util.text.TextFormatting;

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
		this.telegramnumber = NumberUtils.toInt(tnode[4], -1);
		this.where = tnode[9];
		this.deep = tnode[10];
		this.magnitude = !StringUtils.isEmpty(tnode[11]) ? (NumberUtils.toFloat(tnode[11], -1F) > 0F) ? tnode[11] : "不明" : null;
		this.strong = tnode[12];
		this.alarm = "1".equals(tnode[14]);
		return this;
	}

	@Override
	public String toString() {
		if (this.canceled) {
			return TextFormatting.RED + "先程の緊急地震速報はキャンセルされました(第" + this.telegramnumber + "報)";
		} else if (this.alarm) {
			return String.format("%s%s%s %s%s%sで地震 予測震度:%s%s%s %skm M:%s (%s)\n%s強い地震が発生しています。身の安全を確保してください。",
					(this.training ? "[訓練報]" : ""),
					TextFormatting.RED,
					(this.announcement ? "緊急地震速報(キャンセル取り消し)" : "緊急地震速報"),
					TextFormatting.AQUA,
					this.where,
					TextFormatting.RESET,
					TextFormatting.LIGHT_PURPLE,
					this.strong,
					TextFormatting.RESET,
					this.deep,
					this.magnitude,
					(this.finaleew ? "最終報" : "第" + this.telegramnumber + "報"),
					TextFormatting.YELLOW
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
