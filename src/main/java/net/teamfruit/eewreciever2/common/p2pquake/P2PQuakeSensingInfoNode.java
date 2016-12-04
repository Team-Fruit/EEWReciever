package net.teamfruit.eewreciever2.common.p2pquake;

import com.google.gson.JsonParseException;

import net.teamfruit.eewreciever2.common.IQuakeNode;
import net.teamfruit.eewreciever2.common.QuakeEvent;
import net.teamfruit.eewreciever2.common.QuakeEvent.QuakeSensingEvent;
import net.teamfruit.eewreciever2.common.QuakeException;
import net.teamfruit.eewreciever2.common.p2pquake.P2PQuakeJson.QuakeSensingInfo;

public class P2PQuakeSensingInfoNode implements IQuakeNode {

	private QuakeSensingInfo data;

	public QuakeSensingInfo getData() {
		return this.data;
	}

	@Override
	public String getId() {
		return String.valueOf(this.data.code);
	}

	@Override
	public IQuakeNode parseString(final String source) throws QuakeException {
		try {
			this.data = P2PQuake.gson.fromJson(source, QuakeSensingInfo.class);
		} catch (final JsonParseException e) {
			throw new QuakeException("Parse Error", e);
		}
		return this;
	}

	@Override
	public QuakeEvent getEvent() {
		return new QuakeSensingEvent(this);
	}

}
