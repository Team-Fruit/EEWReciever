package net.teamfruit.eewreciever2.client.gui;

import static org.lwjgl.opengl.GL11.*;

import com.kamesuta.mc.bnnwidget.WEvent;
import com.kamesuta.mc.bnnwidget.WPanel;
import com.kamesuta.mc.bnnwidget.component.MButton;
import com.kamesuta.mc.bnnwidget.component.MScaledLabel;
import com.kamesuta.mc.bnnwidget.position.Area;
import com.kamesuta.mc.bnnwidget.position.Coord;
import com.kamesuta.mc.bnnwidget.position.Point;
import com.kamesuta.mc.bnnwidget.position.R;
import com.kamesuta.mc.bnnwidget.render.OpenGL;
import com.kamesuta.mc.bnnwidget.render.WRenderer;

import net.teamfruit.eewreciever2.common.quake.twitter.TweetQuakeUserState;

public class GuiAuthFollow extends WPanel {

	private final TweetQuakeUserState state;

	public GuiAuthFollow(final R position, final TweetQuakeUserState state) {
		super(position);
		this.state = state;
	}

	@Override
	protected void initWidget() {
		add(new MScaledLabel(new R(Coord.left(10), Coord.right(10), Coord.top(35), Coord.height(15))) {
			{
				setColor(0);
				setText("緊急地震速報を利用するには");
			}
		});
		add(new MScaledLabel(new R(Coord.left(10), Coord.right(10), Coord.top(50), Coord.height(15))) {
			{
				setColor(0);
				setText(getLabelText());
			}

			private String getLabelText() {
				if (GuiAuthFollow.this.state.isBlock())
					if (GuiAuthFollow.this.state.isMute())
						return "@eewbot のブロックとミュートを解除し、フォローする必要があります";
					else
						return "@eewbot のブロックを解除し、フォローする必要があります";
				else if (GuiAuthFollow.this.state.isMute())
					if (GuiAuthFollow.this.state.isFollow())
						return "@eewbot のミュートを解除する必要があります";
					else
						return "@eewbot のミュートを解除し、フォローする必要があります";
				else if (GuiAuthFollow.this.state.isFollow())
					return null;
				else
					return "@eewbot をフォローする必要があります";
			}
		});
		add(new TwitterButton(new R(Coord.pleft(.5f), Coord.width(80), Coord.height(20), Coord.top(80)).child(Coord.pleft(-.5f)), this.state));
	}

	public static class TwitterButton extends MButton {
		private final TweetQuakeUserState state;

		public TwitterButton(final R position, final TweetQuakeUserState state) {
			super(position);
			this.state = state;
			if (this.state.isBlock())
				setText("ブロック中");
			else if (this.state.isMute())
				setText("ミュート中");
			else if (!this.state.isFollow())
				setText("フォローする");
		}

		@Override
		public int getTextColor(final WEvent ev, final Area pgp, final Point mouse, final float frame) {
			return this.state.isFollow() ? 0xffffff : 0x000000;
		}

		@Override
		public void draw(final WEvent ev, final Area pgp, final Point p, final float frame, final float popacity) {
			final Area a = getGuiPosition(pgp);
			final float opacity = getGuiOpacity(popacity);

			WRenderer.startShape();
			OpenGL.glColor4f(.2f, .2f, .2f, .2f);
			OpenGL.glLineWidth(1.5f);
			draw(a, GL_LINE_LOOP);
			if (a.pointInside(p)) {
				if (this.state.isFollow())
					OpenGL.glColor4f(.9f, .1f, .1f, .9f);
				else
					OpenGL.glColor4f(.85f, .85f, .85f, 1f);
			} else if (this.state.isFollow())
				OpenGL.glColor4f(.1f, .1f, .9f, .9f);
			else
				OpenGL.glColor4f(.9f, .9f, .9f, 1f);
			draw(a);
			drawText(ev, pgp, p, frame, opacity);
		}

		public void onStateChange(final TweetQuakeUserState state) {
		}
	}
}
