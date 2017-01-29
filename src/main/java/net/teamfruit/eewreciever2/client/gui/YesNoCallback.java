package net.teamfruit.eewreciever2.client.gui;

import com.kamesuta.mc.bnnwidget.WEvent;
import com.kamesuta.mc.bnnwidget.position.Area;
import com.kamesuta.mc.bnnwidget.position.Point;

public interface YesNoCallback {
	void onYes(WEvent ev, final Area pgp, final Point p, final int button);

	void onNo(WEvent ev, final Area pgp, final Point p, final int button);
}
