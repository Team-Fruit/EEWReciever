package net.teamfruit.eewreciever2.client.gui;

import static org.lwjgl.opengl.GL11.*;

import com.kamesuta.mc.bnnwidget.WBase;
import com.kamesuta.mc.bnnwidget.WEvent;
import com.kamesuta.mc.bnnwidget.WFrame;
import com.kamesuta.mc.bnnwidget.WPanel;
import com.kamesuta.mc.bnnwidget.component.MLabel;
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

	protected String descText;
	protected String yesText = "はい";
	protected String noText = "いいえ";

	public String getDescText() {
		return this.descText;
	}

	public GuiYesNo setDescText(final String descText) {
		this.descText = descText;
		return this;
	}

	public String getYesText() {
		return this.yesText;
	}

	public GuiYesNo setYesText(final String yesText) {
		this.yesText = yesText;
		return this;
	}

	public String getNoText() {
		return this.noText;
	}

	public GuiYesNo setNoText(final String noText) {
		this.noText = noText;
		return this;
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
				this.m.stop().add(Easings.easeLinear.move(.2f, .9f)).start();
			}
		});
		add(new WPanel(new R(Coord.pleft(.5f), Coord.ptop(.5f), Coord.width(V.am(240).add(Easings.easeOutQuart.move(.25f, 265)).start()), Coord.height(V.am(35f).add(Easings.easeOutQuart.move(.25f, 45)).start())).child(Coord.pleft(-.5f), Coord.ptop(-.5f))) {
			@Override
			public void draw(final WEvent ev, final Area pgp, final Point p, final float frame, final float popacity) {
				final Area a = getGuiPosition(pgp);

				WRenderer.startShape();
				OpenGL.glColor4f(.4f, .4f, .4f, .15f);
				draw(a);

				OpenGL.glLineWidth(1.2f);
				OpenGL.glColor4f(.5f, .5f, .5f, 0.3f);
				draw(a, GL_LINE_LOOP);

				super.draw(ev, pgp, p, frame, popacity);
			}

			@Override
			protected void initWidget() {
				add(new MLabel(new R(Coord.left(0), Coord.top(8), Coord.right(0), Coord.height(12.5f))) {
					{
						setColor(0xf5f5f5);
					}

					@Override
					public String getText() {
						return getDescText();
					}
				});
				add(new MLabel(new R(Coord.pleft(.5f), Coord.top(28), Coord.width(125), Coord.height(12.5f)).child(Coord.pleft(-.5f)).child(Coord.right(65.5f))) {
					{
						setColor(0x00000);
					}

					@Override
					public void draw(final WEvent ev, final Area pgp, final Point p, final float frame, final float popacity) {
						final Area a = getGuiPosition(pgp);

						WRenderer.startShape();
						OpenGL.glColor4f(.95f, .95f, .95f, 1f);
						draw(a);

						OpenGL.glLineWidth(1.2f);
						OpenGL.glColor4f(.5f, .5f, .5f, 0.3f);
						draw(a, GL_LINE_LOOP);

						super.draw(ev, pgp, p, frame, popacity);
					}

					@Override
					public boolean mouseClicked(final WEvent ev, final Area pgp, final Point p, final int button) {
						GuiYesNo.this.callback.onYes();
						return super.mouseClicked(ev, pgp, p, button);
					}

					@Override
					public String getText() {
						return getYesText();
					}
				});
				add(new MLabel(new R(Coord.pleft(.5f), Coord.top(28), Coord.width(125), Coord.height(12.5f)).child(Coord.pleft(-.5f)).child(Coord.left(65.5f))) {
					{
						setColor(0x00000);
					}

					@Override
					public void draw(final WEvent ev, final Area pgp, final Point p, final float frame, final float popacity) {
						final Area a = getGuiPosition(pgp);

						WRenderer.startShape();
						OpenGL.glColor4f(.95f, .95f, .95f, 1f);
						draw(a);

						OpenGL.glLineWidth(1.2f);
						OpenGL.glColor4f(.5f, .5f, .5f, 0.3f);
						draw(a, GL_LINE_LOOP);
						super.draw(ev, pgp, p, frame, popacity);
					}

					@Override
					public boolean mouseClicked(final WEvent ev, final Area pgp, final Point p, final int button) {
						GuiYesNo.this.callback.onNo();
						return super.mouseClicked(ev, pgp, p, button);
					}

					@Override
					public String getText() {
						return getNoText();
					}
				});
			}
		});
	}
}