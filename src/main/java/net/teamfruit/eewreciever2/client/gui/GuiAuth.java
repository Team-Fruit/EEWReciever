package net.teamfruit.eewreciever2.client.gui;

import net.teamfruit.eewreciever2.common.quake.twitter.TweetQuakeAuther;
import net.teamfruit.eewreciever2.lib.bnnwidget.WFrame;

public class GuiAuth extends WFrame {

	protected final TweetQuakeAuther auther;

	public GuiAuth(final TweetQuakeAuther auther) {
		this.auther = auther;
	}
}
