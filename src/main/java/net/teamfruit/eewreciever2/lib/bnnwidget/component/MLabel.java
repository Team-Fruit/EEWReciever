package net.teamfruit.eewreciever2.lib.bnnwidget.component;

import java.awt.Color;

import org.apache.commons.lang3.StringUtils;

import net.teamfruit.eewreciever2.lib.OpenGL;
import net.teamfruit.eewreciever2.lib.RenderHelper;
import net.teamfruit.eewreciever2.lib.bnnwidget.WBase;
import net.teamfruit.eewreciever2.lib.bnnwidget.WEvent;
import net.teamfruit.eewreciever2.lib.bnnwidget.position.Area;
import net.teamfruit.eewreciever2.lib.bnnwidget.position.Point;
import net.teamfruit.eewreciever2.lib.bnnwidget.position.R;

public class MLabel extends WBase {
	protected int textcolor = 14737632;
	protected String text = "";
	protected boolean shadow;
	protected String watermark;
	protected int watermarkcolor = 0x777777;
	protected Align align = Align.CENTER;
	protected VerticalAlign valign = VerticalAlign.MIDDLE;

	public MLabel(final R position) {
		super(position);
	}

	public MLabel setAlign(final Align align) {
		this.align = align;
		return this;
	}

	public Align getAlign() {
		return this.align;
	}

	public MLabel setVerticalAlign(final VerticalAlign valign) {
		this.valign = valign;
		return this;
	}

	public VerticalAlign getVerticalAlign() {
		return this.valign;
	}

	public MLabel setWatermark(final String watermark) {
		this.watermark = watermark;
		return this;
	}

	public String getWatermark() {
		return this.watermark;
	}

	public MLabel setWatermarkColor(final int watermark) {
		this.watermarkcolor = watermark;
		return this;
	}

	public int getWatermarkColor() {
		return this.watermarkcolor;
	}

	public MLabel setShadow(final boolean b) {
		this.shadow = b;
		return this;
	}

	public boolean isShadow() {
		return this.shadow;
	}

	public MLabel setColor(final int color) {
		this.textcolor = color;
		return this;
	}

	public int getColor() {
		return this.textcolor;
	}

	public MLabel setText(final String s) {
		if (StringUtils.equals(s, getText()))
			return this;
		final String oldText = getText();
		this.text = s;
		onTextChanged(oldText);
		return this;
	}

	public String getText() {
		return this.text;
	}

	protected void onTextChanged(final String oldText) {
	}

	@Override
	public void draw(final WEvent ev, final Area pgp, final Point p, final float frame, final float popacity) {
		final Area out = getGuiPosition(pgp);
		drawText(out, getGuiOpacity(popacity));
	}

	protected float wscale = 1f;

	public MLabel setScaleWidth(final float f) {
		this.wscale = f;
		return this;
	}

	public float getScaleWidth(final Area a) {
		return this.wscale;
	}

	protected float hscale = 1f;

	public MLabel setScaleHeight(final float f) {
		this.hscale = f;
		return this;
	}

	public float getScaleHeight(final Area a) {
		return this.hscale;
	}

	protected void drawText(final Area a, final float opacity) {
		OpenGL.glPushMatrix();
		OpenGL.glTranslated(a.x1()+a.w()/2, a.y1()+a.h()/2, 0);
		OpenGL.glScaled(getScaleWidth(a), getScaleHeight(a), 1);
		OpenGL.glTranslated(-(a.x1()+a.w()/2), -(a.y1()+a.h()/2), 0);
		RenderHelper.startTexture();
		final Color c = new Color(getColor());
		OpenGL.glColor4f(1f, 1f, 1f, opacity);
		fontColor(c.getRed(), c.getGreen(), c.getBlue(), (int) Math.max(4, opacity*c.getAlpha()));
		drawString(getText(), a, getAlign(), getVerticalAlign(), isShadow());
		if (!StringUtils.isEmpty(getWatermark())&&StringUtils.isEmpty(getText())) {
			final Color w = new Color(getWatermarkColor());
			fontColor(w.getRed(), w.getGreen(), w.getBlue(), (int) Math.max(4, opacity*c.getAlpha()));
			drawString(getWatermark(), a, getAlign(), getVerticalAlign(), isShadow());
		}
		OpenGL.glPopMatrix();
	}
}