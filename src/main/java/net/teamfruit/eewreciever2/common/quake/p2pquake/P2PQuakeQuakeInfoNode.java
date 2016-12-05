package net.teamfruit.eewreciever2.common.quake.p2pquake;

import com.google.gson.JsonParseException;

import net.teamfruit.eewreciever2.common.quake.IQuakeNode;
import net.teamfruit.eewreciever2.common.quake.QuakeEvent;
import net.teamfruit.eewreciever2.common.quake.QuakeException;
import net.teamfruit.eewreciever2.common.quake.QuakeEvent.QuakeInfoEvent;
import net.teamfruit.eewreciever2.common.quake.p2pquake.P2PQuakeJson.QuakeInfo;

public class P2PQuakeQuakeInfoNode implements IQuakeNode {

	private QuakeInfo data;

	public QuakeInfo getData() {
		return this.data;
	}

	@Override
	public String getId() {
		return String.valueOf(this.data.code);
	}

	@Override
	public IQuakeNode parseString(final String source) throws QuakeException {
		try {
			this.data = P2PQuake.gson.fromJson(source, QuakeInfo.class);
		} catch (final JsonParseException e) {
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

}
