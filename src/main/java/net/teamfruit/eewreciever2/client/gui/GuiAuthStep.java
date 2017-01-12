package net.teamfruit.eewreciever2.client.gui;

import com.kamesuta.mc.bnnwidget.WPanel;
import com.kamesuta.mc.bnnwidget.position.R;

import net.teamfruit.eewreciever2.common.quake.twitter.TweetQuakeAuther;
import net.teamfruit.eewreciever2.common.quake.twitter.TweetQuakeHelper;

public abstract class GuiAuthStep extends WPanel {
	protected final TweetQuakeAuther auther = TweetQuakeHelper.getAuther();

	public GuiAuthStep() {
		super(new R());
	}

}
