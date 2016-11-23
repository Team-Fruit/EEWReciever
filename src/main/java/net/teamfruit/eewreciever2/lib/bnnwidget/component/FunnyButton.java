package net.teamfruit.eewreciever2.lib.bnnwidget.component;

import net.teamfruit.eewreciever2.lib.OpenGL;
import net.teamfruit.eewreciever2.lib.bnnwidget.WEvent;
import net.teamfruit.eewreciever2.lib.bnnwidget.motion.Easings;
import net.teamfruit.eewreciever2.lib.bnnwidget.position.Area;
import net.teamfruit.eewreciever2.lib.bnnwidget.position.Point;
import net.teamfruit.eewreciever2.lib.bnnwidget.position.R;
import net.teamfruit.eewreciever2.lib.bnnwidget.var.V;
import net.teamfruit.eewreciever2.lib.bnnwidget.var.VMotion;

public class FunnyButton extends MButton {
	public FunnyButton(final R position) {
		super(position);
	}

	private boolean isHighlight = true;
	private boolean highlighted;
	VMotion m = V.pm(0);
	VMotion s = V.pm(1);

	public boolean isHighlight() {
		return this.isHighlight;
	}

	public void setHighlight(final boolean b) {
		this.isHighlight = b;
	}

	@Override
	public void update(final WEvent ev, final Area pgp, final Point p) {
		if (isHighlight()) {
			if (!this.highlighted) {
				this.highlighted = true;
				this.m.stop().add(Easings.easeOutElastic.move(.5f, 6f)).start();
				this.s.stop().add(Easings.easeOutElastic.move(.5f, 1.1f)).start();
			}
		} else if (this.highlighted) {
			this.highlighted = false;
			this.m.stop().add(Easings.easeOutElastic.move(.5f, 0f)).start();
			this.s.stop().add(Easings.easeOutElastic.move(.5f, 1f)).start();
		}
		super.update(ev, pgp, p);
	}

	@Override
	public void draw(final WEvent ev, final Area pgp, final Point p, final float frame, final float opacity) {
		final Area a = getGuiPosition(pgp);
		OpenGL.glPushMatrix();
		OpenGL.glTranslatef(a.x1()+a.w()/2, a.y1()+a.h()/2, 0);
		final float c = this.s.get();
		OpenGL.glScalef(c, c, 1f);
		OpenGL.glRotatef(this.m.get(), 0, 0, 1);
		OpenGL.glTranslatef(-a.x1()-a.w()/2, -a.y1()-a.h()/2, 0);
		super.draw(ev, pgp, p, frame, opacity);
		OpenGL.glPopMatrix();
	}
}