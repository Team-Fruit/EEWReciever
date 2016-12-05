package net.teamfruit.eewreciever2.common.quake.p2pquake;

import java.text.ParseException;
import java.util.Map;

import com.google.gson.JsonParseException;

import net.teamfruit.eewreciever2.common.quake.IQuakeNode;
import net.teamfruit.eewreciever2.common.quake.QuakeEvent;
import net.teamfruit.eewreciever2.common.quake.QuakeException;
import net.teamfruit.eewreciever2.common.quake.p2pquake.P2PQuakeJson.QuakeSensingInfo;

public class P2PQuakeSensingInfoNode extends P2PQuakeNode<P2PQuakeJson.QuakeSensingInfo> {

	public int count;
	public Map<String, Integer> areas;
	public Map<String, Integer> prefs;
	public Map<String, Integer> regions;

	@Override
	public IQuakeNode parseString(final String source) throws QuakeException {
		try {
			this.data = P2PQuake.gson.fromJson(source, QuakeSensingInfo.class);

			this.date = dateFormat.parse(this.data.time);
			this.code = this.data.code;
			this.count = this.data.count;
			this.areas = this.data.areas;
			this.prefs = this.data.prefs;
			this.regions = this.data.regions;
		} catch (final JsonParseException e) {
			throw new QuakeException("Parse Error", e);
		} catch (final ParseException e) {
			throw new QuakeException("Parse Error", e);
		}
		return this;
	}

	@Override
	@SuppressWarnings("deprecation")
	public QuakeEvent getEvent() {
		return new net.teamfruit.eewreciever2.common.quake.QuakeEvent.QuakeSensingEvent(this);
	}

	@Override
	public String getChatFormat() {
		final StringBuilder sb = new StringBuilder("[地震感知情報] ");
		int i = 0;
		for (final String line : this.prefs.keySet()) {
			if (i!=0)
				sb.append(", ");
			sb.append(line).append(':').append(String.valueOf(this.prefs.get(line)));
			i++;
		}
		return sb.toString();
	}

	@Override
	public boolean canChat() {
		return super.canChat()&&this.prefs!=null&&this.prefs.size()!=0;
	}

}
