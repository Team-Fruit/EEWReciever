package net.teamfruit.eewreciever2.client.gui;

import com.kamesuta.mc.bnnwidget.WPanel;
import com.kamesuta.mc.bnnwidget.component.MScaledLabel;
import com.kamesuta.mc.bnnwidget.position.Coord;
import com.kamesuta.mc.bnnwidget.position.R;

public class GuiAuthFollow extends WPanel {

	public GuiAuthFollow(final R position) {
		super(position);
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
}
