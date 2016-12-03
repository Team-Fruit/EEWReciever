package net.teamfruit.eewreciever2.common.p2pquake;

import net.teamfruit.eewreciever2.common.IQuakeNode;
import net.teamfruit.eewreciever2.common.QuakeEvent;
import net.teamfruit.eewreciever2.common.QuakeException;

public class P2PQuakeQuakeInfoNode extends P2PQuakeJson.QuakeInfo implements IQuakeNode {

	@Override
	public String getId() {
		return String.valueOf(this.code);
	}

	@Override
	public QuakeEvent getEvent() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public IQuakeNode parseString(final String source) throws QuakeException {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

}
