package net.teamfruit.eewreciever2.client.gui;

import static org.lwjgl.opengl.GL11.*;

import com.kamesuta.mc.bnnwidget.WBase;
import com.kamesuta.mc.bnnwidget.WBox;
import com.kamesuta.mc.bnnwidget.WEvent;
import com.kamesuta.mc.bnnwidget.WFrame;
import com.kamesuta.mc.bnnwidget.WPanel;
import com.kamesuta.mc.bnnwidget.component.MScaledLabel;
import com.kamesuta.mc.bnnwidget.position.Area;
import com.kamesuta.mc.bnnwidget.position.Coord;
import com.kamesuta.mc.bnnwidget.position.Point;
import com.kamesuta.mc.bnnwidget.position.R;
import com.kamesuta.mc.bnnwidget.render.OpenGL;
import com.kamesuta.mc.bnnwidget.render.WRenderer;

import net.minecraft.client.gui.GuiScreen;
import net.teamfruit.eewreciever2.common.quake.twitter.TweetQuakeAuther;
import net.teamfruit.eewreciever2.common.quake.twitter.TweetQuakeManager;

public class GuiAuth extends WFrame {
	protected static TweetQuakeAuther auther = TweetQuakeManager.intance().getAuther();

	public GuiAuth(final GuiScreen parent) {
		super(parent);
	}

	public GuiAuth() {
		this(null);
	}

	@Override
	protected void initWidget() {
		add(new WBase(new R()) {
			@Override
			public void draw(final WEvent ev, final Area pgp, final Point p, final float frame, final float popacity) {
				WRenderer.startShape();
				OpenGL.glColor4f(0f, 0f, 0f, 0.4f);
				draw(getGuiPosition(pgp));
			}
		});
		add(new WPanel(new R(Coord.pleft(.5f), Coord.ptop(.5f), Coord.width(190), Coord.height(160)).child(Coord.pleft(-.5f), Coord.ptop(-.5f))) {
			@Override
			protected void initWidget() {
				add(new WBase(new R()) {
					@Override
					public void draw(final WEvent ev, final Area pgp, final Point p, final float frame, final float popacity) {
						final Area a = getGuiPosition(pgp);
						WRenderer.startShape();
						OpenGL.glColor4f(.95f, .95f, .95f, 1f);
						draw(a);

						OpenGL.glLineWidth(4f);
						OpenGL.glColor4f(0, 0, 0, 0.2f);
						draw(a, GL_LINE_LOOP);
					}
				});
				add(new WBox(new R()) {
					{
						if (auther==null) {
							OverlayFrame.instance.pane.addNotice1("認証の必要はありません！", 2);
							mc.displayGuiScreen(GuiAuth.this.parent);
						}
					}

					@Override
					protected void initWidget() {
						invokeLater(new Runnable() {
							@Override
							public void run() {
								add(new GuiAuthURL(new R()));
							}
						});
						GuiAuth.this.event.data.put("box", this);
					}
				});
				add(new MScaledLabel(new R(Coord.left(5), Coord.right(5), Coord.top(10), Coord.height(15))) {
					{
						setText("Twitter認証");
						setColor(0);
					}
				});
			}
		});
	}
}