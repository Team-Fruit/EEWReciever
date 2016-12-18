package net.teamfruit.eewreciever2.lib.bnnwidget.component;

import java.awt.Color;

import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.ResourceLocation;
import net.teamfruit.eewreciever2.lib.OpenGL;
import net.teamfruit.eewreciever2.lib.RenderHelper;
import net.teamfruit.eewreciever2.lib.bnnwidget.WBase;
import net.teamfruit.eewreciever2.lib.bnnwidget.WEvent;
import net.teamfruit.eewreciever2.lib.bnnwidget.motion.Easings;
import net.teamfruit.eewreciever2.lib.bnnwidget.position.Area;
import net.teamfruit.eewreciever2.lib.bnnwidget.position.Point;
import net.teamfruit.eewreciever2.lib.bnnwidget.position.R;
import net.teamfruit.eewreciever2.lib.bnnwidget.var.V;
import net.teamfruit.eewreciever2.lib.bnnwidget.var.VMotion;

public class MButton extends WBase {
	public static final ResourceLocation button = new ResourceLocation("signpic", "textures/gui/buttons.png");

	public String text = "";
	public String actionCommand;
	private boolean isEnabled = true;

	public MButton(final R position) {
		super(position);
	}

	public MButton setText(final String s) {
		this.text = s;
		return this;
	}

	public boolean isEnabled() {
		return this.isEnabled;
	}

	public MButton setEnabled(final boolean b) {
		this.isEnabled = b;
		return this;
	}

	@Override
	public boolean mouseClicked(final WEvent ev, final Area pgp, final Point p, final int button) {
		final Area abs = getGuiPosition(pgp);
		if (abs.pointInside(p)) {
			if (isEnabled())
				if (onClicked(ev, pgp, p, button)) {
					if (this.actionCommand!=null)
						ev.eventDispatch(this.actionCommand, Integer.valueOf(button));
					playPressButtonSound();
				}
			return true;
		}
		return false;
	}

	public static void playPressButtonSound() {
		mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
	}

	protected boolean onClicked(final WEvent ev, final Area pgp, final Point p, final int button) {
		return true;
	}

	@Override
	public void draw(final WEvent ev, final Area pgp, final Point p, final float frame, final float popacity) {
		final Area a = getGuiPosition(pgp);
		final float opacity = getGuiOpacity(popacity);

		RenderHelper.startTexture();
		OpenGL.glColor4f(1.0F, 1.0F, 1.0F, opacity);
		texture().bindTexture(button);
		final int state = !isEnabled() ? 0 : a.pointInside(p) ? 2 : 1;

		drawTextureModalSize(a.x1(), a.y1(), a.w()/2, a.h()/2, 0, state*80, a.w()/2, a.h()/2);
		drawTextureModalSize(a.x1()+a.w()/2, a.y1(), a.w()/2, a.h()/2, 256-a.w()/2, state*80, a.w()/2, a.h()/2);
		drawTextureModalSize(a.x1(), a.y1()+a.h()/2, a.w()/2, a.h()/2, 0, state*80+80-a.h()/2, a.w()/2, a.h()/2);
		drawTextureModalSize(a.x1()+a.w()/2, a.y1()+a.h()/2, a.w()/2, a.h()/2, 256-a.w()/2, state*80+80-a.h()/2, a.w()/2, a.h()/2);
		if (this.text!=null)
			drawText(ev, pgp, p, frame, opacity);
	}

	protected VMotion o = V.pm(0).start();
	protected boolean ob = false;;

	@Override
	public void update(final WEvent ev, final Area pgp, final Point p) {
		final Area a = getGuiPosition(pgp);
		if (a.pointInside(p)) {
			if (!this.ob)
				this.o.stop().add(Easings.easeLinear.move(.1f, 1f)).start();
			this.ob = true;
		} else {
			if (this.ob)
				this.o.stop().add(Easings.easeLinear.move(.1f, 0f)).start();
			this.ob = false;
		}
	}

	public void drawText(final WEvent ev, final Area pgp, final Point p, final float frame, final float opacity) {
		final Area a = getGuiPosition(pgp);
		RenderHelper.startTexture();
		final Color c = new Color(getTextColour(ev, pgp, p, frame));
		fontColor(c.getRed(), c.getGreen(), c.getBlue(), (int) (c.getAlpha()*opacity));
		drawString(this.text, a, Align.CENTER, VerticalAlign.MIDDLE, true);
	}

	public int getTextColour(final WEvent ev, final Area pgp, final Point p, final float frame) {
		final Area abs = getGuiPosition(pgp);
		return abs.pointInside(p) ? -96 : !isEnabled() ? -6250336 : -2039584;
	}

	public MButton setActionCommand(final String string) {
		this.actionCommand = string;
		return this;
	}
}
