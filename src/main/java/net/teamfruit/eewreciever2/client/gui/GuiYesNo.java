package net.teamfruit.eewreciever2.client.gui;

import com.kamesuta.mc.bnnwidget.WBase;
import com.kamesuta.mc.bnnwidget.WEvent;
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

public class GuiYesNo extends WPanel {
	protected final YesNoCallback callback;

	public GuiYesNo(final R area, final YesNoCallback callback) {
		super(area);
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
		add(new WPanel(new R(Coord.pleft(.5f), Coord.ptop(.5f), Coord.width(200), Coord.height(50)).child(Coord.pleft(-.5f), Coord.ptop(-.5f))) {

		});
	}
}
