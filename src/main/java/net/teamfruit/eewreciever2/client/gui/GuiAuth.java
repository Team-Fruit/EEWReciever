package net.teamfruit.eewreciever2.client.gui;

import static org.lwjgl.opengl.GL11.*;

import net.teamfruit.eewreciever2.lib.OpenGL;
import net.teamfruit.eewreciever2.lib.RenderHelper;
import net.teamfruit.eewreciever2.lib.bnnwidget.WEvent;
import net.teamfruit.eewreciever2.lib.bnnwidget.WFrame;
import net.teamfruit.eewreciever2.lib.bnnwidget.WPanel;
import net.teamfruit.eewreciever2.lib.bnnwidget.position.Area;
import net.teamfruit.eewreciever2.lib.bnnwidget.position.Point;
import net.teamfruit.eewreciever2.lib.bnnwidget.position.R;

public class GuiAuth extends WFrame {

	@Override
	protected void initWidget() {
		add(new WPanel(new R()) {
			@Override
			public void draw(final WEvent ev, final Area pgp, final Point p, final float frame, final float popacity) {
				RenderHelper.startShape();
				OpenGL.glColor4f(1f, 0f, 0f, 0.4f);
				draw(getGuiPosition(pgp));
			}

			@Override
			protected void initWidget() {
				add(new WPanel(new R(/*Coord.pleft(.5f), Coord.ptop(.5f), Coord.width(200), Coord.height(170)).child(Coord.pleft(-.5f), Coord.ptop(-.5f)*/)) {

					@Override
					public void draw(final WEvent ev, final Area pgp, final Point p, final float frame, final float popacity) {
						final Area a = getGuiPosition(pgp);
						RenderHelper.startShape();
						OpenGL.glColor4f(1f, 1f, 1f, 1f);
						draw(a);

						OpenGL.glLineWidth(2f);
						OpenGL.glColor4f(1, 1, 1, 0.2f);
						draw(a, GL_LINE_LOOP);
						super.draw(ev, pgp, p, frame, popacity);
					}
				});
			}
		});
	}
}
