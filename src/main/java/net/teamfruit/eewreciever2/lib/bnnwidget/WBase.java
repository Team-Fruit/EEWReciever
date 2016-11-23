package net.teamfruit.eewreciever2.lib.bnnwidget;

import net.teamfruit.eewreciever2.lib.bnnwidget.position.Area;
import net.teamfruit.eewreciever2.lib.bnnwidget.position.Point;
import net.teamfruit.eewreciever2.lib.bnnwidget.position.R;
import net.teamfruit.eewreciever2.lib.bnnwidget.var.V;
import net.teamfruit.eewreciever2.lib.bnnwidget.var.VCommon;

public abstract class WBase extends WComponent {

	protected R position;
	protected VCommon opacity;

	public WBase(final R position) {
		initPosition(position);
		initOpacity();
	}

	public void setPosition(final R position) {
		this.position = position;
	}

	public void setOpacity(final VCommon c) {
		this.opacity = c;
	}

	protected void initPosition(final R position) {
		setPosition(position);
	}

	protected void initOpacity() {
		setOpacity(V.p(1f));
	}

	public R getGuiPosition() {
		return this.position;
	}

	public VCommon getGuiOpacity() {
		return this.opacity;
	}

	public Area getGuiPosition(final Area pgp) {
		return pgp.child(getGuiPosition());
	}

	public float getGuiOpacity(final float popacity) {
		return getGuiOpacity().getAbsCoord(0, popacity);
	}

	@Override
	public WCommon top(final WEvent ev, final Area pgp, final Point point) {
		final Area a = getGuiPosition(pgp);
		if (a.pointInside(point))
			return this;
		return null;
	}
}
