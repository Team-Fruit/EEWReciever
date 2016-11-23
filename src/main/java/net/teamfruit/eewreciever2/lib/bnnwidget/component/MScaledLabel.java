package net.teamfruit.eewreciever2.lib.bnnwidget.component;

import net.teamfruit.eewreciever2.lib.bnnwidget.position.Area;
import net.teamfruit.eewreciever2.lib.bnnwidget.position.R;

public class MScaledLabel extends MLabel {
	public MScaledLabel(final R position) {
		super(position);
	}

	@Override
	public float getScaleWidth(final Area a) {
		final float f1 = a.w()/font().getStringWidth(getText());
		final float f2 = a.h()/font().FONT_HEIGHT;
		return Math.min(f1, f2);
	}

	@Override
	public float getScaleHeight(final Area a) {
		final float f1 = a.w()/font().getStringWidth(getText());
		final float f2 = a.h()/font().FONT_HEIGHT;
		return Math.min(f1, f2);
	}
}
