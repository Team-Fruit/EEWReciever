package net.teamfruit.eewreciever2.lib.bnnwidget.component;

import net.teamfruit.eewreciever2.lib.OpenGL;
import net.teamfruit.eewreciever2.lib.RenderHelper;
import net.teamfruit.eewreciever2.lib.bnnwidget.WCommon;
import net.teamfruit.eewreciever2.lib.bnnwidget.WEvent;
import net.teamfruit.eewreciever2.lib.bnnwidget.position.Area;
import net.teamfruit.eewreciever2.lib.bnnwidget.position.Coord;
import net.teamfruit.eewreciever2.lib.bnnwidget.position.Point;
import net.teamfruit.eewreciever2.lib.bnnwidget.position.R;

public class MSelectLabel extends MSelect {

	protected MLabel field;

	public MSelectLabel(final R position, final float buttonwidth) {
		super(position, buttonwidth);
	}

	@Override
	protected WCommon getField() {
		return this.field = new MLabel(new R(Coord.left(this.buttonwidth), Coord.right(this.buttonwidth), Coord.top(0), Coord.bottom(0))) {
			@Override
			public void draw(final WEvent ev, final Area pgp, final Point p, final float frame, final float popacity) {
				final Area a = getGuiPosition(pgp);
				RenderHelper.startShape();
				OpenGL.glColor4f(0f, 0f, 0f, .4f);
				draw(a);
				super.draw(ev, pgp, p, frame, popacity);
			}

			@Override
			protected void onTextChanged(final String oldText) {
				onChanged(oldText, getText());
			}
		};
	}

	@Override
	public MSelect setText(final String text) {
		this.field.setText(text);
		return this;
	}
}
