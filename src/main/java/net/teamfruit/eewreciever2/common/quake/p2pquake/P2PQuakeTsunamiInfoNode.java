package net.teamfruit.eewreciever2.common.quake.p2pquake;

import java.util.List;

import com.google.common.collect.Lists;
import com.google.gson.JsonParseException;

import net.minecraft.util.EnumChatFormatting;
import net.teamfruit.eewreciever2.common.quake.IQuakeNode;
import net.teamfruit.eewreciever2.common.quake.QuakeEvent;
import net.teamfruit.eewreciever2.common.quake.QuakeEvent.TsunamiWarnEvent;
import net.teamfruit.eewreciever2.common.quake.QuakeException;
import net.teamfruit.eewreciever2.common.quake.p2pquake.P2PQuakeJson.TsunamiInfo;
import net.teamfruit.eewreciever2.common.quake.p2pquake.P2PQuakeJson.TsunamiInfo.Area;

public class P2PQuakeTsunamiInfoNode implements IQuakeNode {

	private TsunamiInfo data;

	public String issue;
	public boolean cancell;
	public List<Area> areas;

	public TsunamiInfo getData() {
		return this.data;
	}

	@Override
	public String getId() {
		return String.valueOf(this.data.code);
	}

	@Override
	public IQuakeNode parseString(final String source) throws QuakeException {
		try {
			this.data = P2PQuake.gson.fromJson(source, TsunamiInfo.class);
		} catch (final JsonParseException e) {
			throw new QuakeException("Parse Error", e);
		}
		this.issue = this.data.issue.type;
		this.cancell = this.data.cancelled;
		this.areas = this.data.areas;
		return this;
	}

	@Override
	public QuakeEvent getEvent() {
		return new TsunamiWarnEvent(this);
	}

	@Override
	public String toString() {
		return getChatFormat();
	}

	@Override
	public String getChatFormat() {
		if (!this.issue.equalsIgnoreCase("Focus"))
			return "[EEWReciever2] 不明な情報を受信しました。";
		if (this.data.cancelled)
			return "[津波情報] 発表されていた津波予報は全て解除されました。";
		if (this.areas.isEmpty())
			return "[EEWReciever2] エリア不明の津波予報を受信しました。";

		final List<Area> major = Lists.newArrayList();
		final List<Area> warn = Lists.newArrayList();
		final List<Area> watch = Lists.newArrayList();
		for (final Area area : this.areas) {
			if (area.name.equals("MajorWarning"))
				major.add(area);
			else if (area.name.equals("Warning"))
				warn.add(area);
			else if (area.name.equals("Watch"))
				watch.add(area);
		}

		final StringBuilder sb = new StringBuilder();
		if (!major.isEmpty())
			sb.append("大津波警報 ");
		if (!warn.isEmpty())
			sb.append("津波警報 ");
		if (!watch.isEmpty())
			sb.append("津波注意報 ");
		sb.append("が以下の地域に発表されました！\n");
		if (!major.isEmpty()) {
			sb.append(EnumChatFormatting.LIGHT_PURPLE);
			sb.append("【大津波警報】");
			sb.append(EnumChatFormatting.RESET);
			sb.append(formatTsunamiArea(major));
			sb.append("\n");
		}
		if (!warn.isEmpty()) {
			sb.append(EnumChatFormatting.RED);
			sb.append("【津波警報】");
			sb.append(EnumChatFormatting.RESET);
			sb.append(formatTsunamiArea(warn));
			sb.append("\n");
		}
		if (!watch.isEmpty()) {
			sb.append(EnumChatFormatting.GOLD);
			sb.append("【津波注意報】");
			sb.append(EnumChatFormatting.RESET);
			sb.append(formatTsunamiArea(watch));
			sb.append("\n");
		}
		sb.append("メディア等から情報を入手し、適切な行動を行ってください。");
		return sb.toString();
	}

	private String formatTsunamiArea(final List<Area> area) {
		final StringBuilder sb = new StringBuilder();
		for (final Area line : area) {
			sb.append(line.name);
			if (line.immediate) {
				sb.append(EnumChatFormatting.RED);
				sb.append("[すぐ来る！]");
				sb.append(EnumChatFormatting.RESET);
			}
		}
		return sb.toString();
	}

	@Override
	public boolean canChat() {
		return this.issue.equalsIgnoreCase("Focus")&&!this.areas.isEmpty();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime*result+((this.areas==null) ? 0 : this.areas.hashCode());
		result = prime*result+(this.cancell ? 1231 : 1237);
		result = prime*result+((this.data==null) ? 0 : this.data.hashCode());
		result = prime*result+((this.issue==null) ? 0 : this.issue.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this==obj)
			return true;
		if (obj==null)
			return false;
		if (!(obj instanceof P2PQuakeTsunamiInfoNode))
			return false;
		final P2PQuakeTsunamiInfoNode other = (P2PQuakeTsunamiInfoNode) obj;
		if (this.areas==null) {
			if (other.areas!=null)
				return false;
		} else if (!this.areas.equals(other.areas))
			return false;
		if (this.cancell!=other.cancell)
			return false;
		if (this.data==null) {
			if (other.data!=null)
				return false;
		} else if (!this.data.equals(other.data))
			return false;
		if (this.issue==null) {
			if (other.issue!=null)
				return false;
		} else if (!this.issue.equals(other.issue))
			return false;
		return true;
	}
}
