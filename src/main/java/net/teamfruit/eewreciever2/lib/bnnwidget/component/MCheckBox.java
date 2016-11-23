package net.teamfruit.eewreciever2.lib.bnnwidget.component;

import net.teamfruit.eewreciever2.lib.OpenGL;
import net.teamfruit.eewreciever2.lib.RenderHelper;
import net.teamfruit.eewreciever2.lib.bnnwidget.WEvent;
import net.teamfruit.eewreciever2.lib.bnnwidget.position.Area;
import net.teamfruit.eewreciever2.lib.bnnwidget.position.Point;
import net.teamfruit.eewreciever2.lib.bnnwidget.position.R;

public class MCheckBox extends MLabel {
	protected boolean checked = true;

	public MCheckBox(final R position) {
		super(position);
	}

	public void check(final boolean check) {
		this.checked = check;
		onCheckChanged(!check);
	}

	public final boolean isCheck() {
		return this.checked;
	}

	protected void onCheckChanged(final boolean oldCheck) {
	}

	@Override
	public boolean mouseClicked(final WEvent ev, final Area pgp, final Point p, final int button) {
		final Area a = getGuiPosition(pgp);
		if (a.pointInside(p)) {
			check(!this.checked);
			MButton.playPressButtonSound();
			return true;
		}
		return false;
	}

	@Override
	public void draw(final WEvent ev, final Area pgp, final Point p, final float frame, final float popacity) {
		final Area o = getGuiPosition(pgp);
		final Area a = new Area(o.x1(), o.y1(), o.x1()+o.h(), o.y2());
		drawCheckBox(a);
		final Area b = o.child(o.x1()+o.h(), 0, o.x1()+o.h(), 0);
		drawText(b, getGuiOpacity(popacity));
	}

	protected void drawCheckBox(final Area out) {
		final Area in = out.child(1, 1, -1, -1);
		RenderHelper.startShape();
		OpenGL.glColor4f(0.627451f, 0.627451f, 0.627451f, 1f);
		draw(out);
		OpenGL.glColor4f(0f, 0f, 0f, 1f);
		draw(in);
		RenderHelper.startTexture();
		if (this.checked) {
			final String strcheck = "\u2713";
			OpenGL.glPushMatrix();
			OpenGL.glTranslatef(in.x1()+(in.w()-font().getStringWidth(strcheck))/2, in.y1()+(in.h()-font().FONT_HEIGHT)/2, 0f);
			//glScalef(2f, 2f, 1f);
			font().drawStringWithShadow(strcheck, 0, 0, 0xffffff);
			OpenGL.glPopMatrix();
		}
	}
}
