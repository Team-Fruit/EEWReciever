package net.teamfruit.eewreciever2.common.quake.p2pquake;

import java.text.ParseException;

import com.google.gson.JsonParseException;

import net.teamfruit.eewreciever2.common.quake.IQuakeNode;
import net.teamfruit.eewreciever2.common.quake.QuakeEvent;
import net.teamfruit.eewreciever2.common.quake.QuakeEvent.QuakeInfoEvent;
import net.teamfruit.eewreciever2.common.quake.QuakeException;
import net.teamfruit.eewreciever2.common.quake.p2pquake.P2PQuakeJson.QuakeInfo;

public class P2PQuakeQuakeInfoNode extends P2PQuakeNode<P2PQuakeJson.QuakeInfo> {

	@Override
	public IQuakeNode parseString(final String source) throws QuakeException {
		try {
			this.data = P2PQuake.gson.fromJson(source, QuakeInfo.class);

			this.date = dateFormat.parse(this.data.time);
			this.code = this.data.code;
		} catch (final JsonParseException e) {
			throw new QuakeException("Parse Error", e);
		} catch (final ParseException e) {
			throw new QuakeException("Parse Error", e);
		}
		return this;
	}

	@Override
	public QuakeEvent getEvent() {
		return new QuakeInfoEvent(this);
	}

	@Override
	public String getChatFormat() {

		return null;
	}

	@Override
	public boolean canChat() {
		return super.canChat();
	}

}
