package net.teamfruit.eewreciever2.client.gui;

import static org.lwjgl.opengl.GL11.*;

import com.kamesuta.mc.bnnwidget.WBase;
import com.kamesuta.mc.bnnwidget.WEvent;
import com.kamesuta.mc.bnnwidget.WFrame;
import com.kamesuta.mc.bnnwidget.WPanel;
import com.kamesuta.mc.bnnwidget.motion.Easings;
import com.kamesuta.mc.bnnwidget.position.Area;
import com.kamesuta.mc.bnnwidget.position.Coord;
import com.kamesuta.mc.bnnwidget.position.Point;
import com.kamesuta.mc.bnnwidget.position.R;
import com.kamesuta.mc.bnnwidget.render.OpenGL;
import com.kamesuta.mc.bnnwidget.render.WRenderer;
import com.kamesuta.mc.bnnwidget.var.V;
import com.kamesuta.mc.bnnwidget.var.VMotion;

import net.minecraft.client.gui.GuiScreen;

public class GuiYesNo extends WFrame {
	protected final YesNoCallback callback;

	public GuiYesNo(final GuiScreen parent, final YesNoCallback callback) {
		super(parent);
		this.callback = callback;
	}

	protected String yesText;
	protected String noText;

	public String getYesText() {
		return this.yesText;
	}

	public void setYesText(final String yesText) {
		this.yesText = yesText;
	}

	public String getNoText() {
		return this.noText;
	}

	public void setNoText(final String noText) {
		this.noText = noText;
	}

	@Override
	protected void initWidget() {
		add(new WBase(new R()) {
			VMotion m = V.pm(0);

			@Override
			public void draw(final WEvent ev, final Area pgp, final Point p, final float frame, final float opacity) {
				WRenderer.startShape();
				OpenGL.glColor4f(0f, 0f, 0f, this.m.get());
				draw(getGuiPosition(pgp));
			}

			@Override
			public void onAdded() {
				this.m.stop().add(Easings.easeLinear.move(.2f, .5f)).start();
			}
		});
		add(new WPanel(new R(Coord.pleft(.5f), Coord.ptop(.5f), Coord.width(V.pm(200f).add(Easings.easeOutQuart.move(.25f, 400)).start()), Coord.height(V.pm(70f).add(Easings.easeOutQuart.move(.25f, 100)).start())).child(Coord.pleft(-.5f), Coord.ptop(-.5f))) {
			@Override
			public void draw(final WEvent ev, final Area pgp, final Point p, final float frame, final float popacity) {
				final Area a = getGuiPosition(pgp);

				WRenderer.startShape();
				OpenGL.glLineWidth(4f);
				OpenGL.glColor4f(0, 0, 0, 0.2f);
				draw(a, GL_LINE_LOOP);

				OpenGL.glPopMatrix();
				OpenGL.glBlendFunc(GL_ONE_MINUS_DST_COLOR, GL_ZERO);
				OpenGL.glBlendFunc(GL_ONE, GL_ONE);
				OpenGL.glBlendFunc(GL_ONE_MINUS_DST_COLOR, GL_ZERO);
				OpenGL.glColor4f(0, 0, 0, .3f);
				draw(a);
				OpenGL.glPushMatrix();

				super.draw(ev, pgp, p, frame, popacity);
			}
		});
	}
}
