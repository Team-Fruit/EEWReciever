package net.teamfruit.eewreciever2.client.gui;

import com.kamesuta.mc.bnnwidget.WPanel;
import com.kamesuta.mc.bnnwidget.component.MScaledLabel;
import com.kamesuta.mc.bnnwidget.position.Coord;
import com.kamesuta.mc.bnnwidget.position.R;

import net.teamfruit.eewreciever2.common.Reference;
import net.teamfruit.eewreciever2.common.quake.twitter.TweetQuakeManager;
import twitter4j.TwitterException;

public class GuiAuthFollow extends WPanel {
	protected Follow follow;

	public GuiAuthFollow(final R position) {
		super(position);
		new Thread() {
			@Override
			public void run() {
				try {
					TweetQuakeManager.intance().getAuthedTwitter().showUser(214358709L);
				} catch (final TwitterException e) {
					Reference.logger.error(e);
				}
			};
		}.start();
	}

	@Override
	protected void initWidget() {
		add(new MScaledLabel(new R(Coord.left(10), Coord.right(10), Coord.top(35), Coord.height(10))) {
			{
				setColor(0);
				setText("緊急地震速報を利用するには");
			}
		});
		add(new MScaledLabel(new R(Coord.left(10), Coord.right(10), Coord.top(45), Coord.height(10))) {
			{
				setColor(0);
				setText("@eewbot をフォローする必要があります");
			}
		});
	}

	protected static enum Follow {
		NOTFOLLOW, FOLLOWING, BLOCK
	}
}
