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
	}

	public static class TwitterButton extends MButton {

		public TwitterButton(final R position) {
			super(position);
		}

		@Override
		public void draw(final WEvent ev, final Area pgp, final Point p, final float frame, final float popacity) {
			final Area a = getGuiPosition(pgp);
			final float opacity = getGuiOpacity(popacity);

			OpenGL.glBegin(GL_QUADS);
			OpenGL.glColor4f(0f, 0f, 0f, 1f);
			OpenGL.glVertex3f(0, 0, 0);
			OpenGL.glEnd();
			drawText(ev, pgp, p, frame, opacity);
		}
	}
}
