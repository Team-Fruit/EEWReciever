package net.teamfruit.eewreciever2.lib.bnnwidget.component;

import net.minecraft.util.ResourceLocation;
import net.teamfruit.eewreciever2.lib.OpenGL;
import net.teamfruit.eewreciever2.lib.RenderHelper;
import net.teamfruit.eewreciever2.lib.bnnwidget.WEvent;
import net.teamfruit.eewreciever2.lib.bnnwidget.WPanel;
import net.teamfruit.eewreciever2.lib.bnnwidget.position.Area;
import net.teamfruit.eewreciever2.lib.bnnwidget.position.Point;
import net.teamfruit.eewreciever2.lib.bnnwidget.position.R;

public class MPanel extends WPanel {
	public static final ResourceLocation background = new ResourceLocation("signpic", "textures/gui/background.png");

	public MPanel(final R position) {
		super(position);
	}

	@Override
	public void draw(final WEvent ev, final Area pgp, final Point p, final float frame, final float popacity) {
		final Area a = getGuiPosition(pgp);
		final float op = getGuiOpacity(popacity);
		RenderHelper.startTexture();
		OpenGL.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		texture().bindTexture(background);
		drawTextureModalSize(a.x1(), a.y1(), a.w()/2, a.h()/2, 0, 0, a.w()/2, a.h()/2);
		drawTextureModalSize(a.x1()+a.w()/2, a.y1(), a.w()/2, a.h()/2, 256-a.w()/2, 0, a.w()/2, a.h()/2);
		drawTextureModalSize(a.x1(), a.y1()+a.h()/2, a.w()/2, a.h()/2, 0, 256-a.h()/2, a.w()/2, a.h()/2);
		drawTextureModalSize(a.x1()+a.w()/2, a.y1()+a.h()/2, a.w()/2, a.h()/2, 256-a.w()/2, 256-a.h()/2, a.w()/2, a.h()/2);
		super.draw(ev, pgp, p, frame, popacity);
	}
}
