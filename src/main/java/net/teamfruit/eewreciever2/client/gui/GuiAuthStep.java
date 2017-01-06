package net.teamfruit.eewreciever2.client.gui;

import net.teamfruit.eewreciever2.common.quake.twitter.TweetQuakeAuther;
import net.teamfruit.eewreciever2.common.quake.twitter.TweetQuakeHelper;
import net.teamfruit.eewreciever2.lib.bnnwidget.WPanel;
import net.teamfruit.eewreciever2.lib.bnnwidget.position.R;

public abstract class GuiAuthStep extends WPanel {
	protected final TweetQuakeAuther auther = TweetQuakeHelper.getAuther();

	public GuiAuthStep() {
		super(new R());
	}

}
