package net.teamfruit.eewreciever2.lib.bnnwidget;

import net.teamfruit.eewreciever2.lib.bnnwidget.position.Area;
import net.teamfruit.eewreciever2.lib.bnnwidget.position.Point;

public interface WCommon {
	void onAdded();

	void onInit(WEvent ev, Area pgp, Point mouse);

	void draw(WEvent ev, Area pgp, Point mouse, float frame, float popacity);

	void update(WEvent ev, Area pgp, Point mouse);

	boolean keyTyped(WEvent ev, Area pgp, Point mouse, char c, int keycode);

	boolean mouseScrolled(WEvent ev, Area pgp, Point mouse, int scroll);

	boolean mouseMoved(WEvent ev, Area pgp, Point mouse, int button);

	boolean mouseClicked(WEvent ev, Area pgp, Point mouse, int button);

	boolean mouseDragged(WEvent ev, Area pgp, Point mouse, int button, long time);

	boolean mouseReleased(WEvent ev, Area pgp, Point mouse, int button);

	boolean onCloseRequest();

	boolean onClosing(WEvent ev, Area pgp, Point mouse);

	WCommon top(WEvent ev, Area pgp, Point point);
}
