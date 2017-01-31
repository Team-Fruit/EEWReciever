package net.teamfruit.eewreciever2.client.gui;

import static org.lwjgl.opengl.GL11.*;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.lwjgl.util.Timer;

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

import net.minecraft.client.Minecraft;
import net.teamfruit.eewreciever2.client.ClientThreadPool;
import net.teamfruit.eewreciever2.common.Reference;
import net.teamfruit.eewreciever2.common.quake.twitter.TweetQuakeManager;
import net.teamfruit.eewreciever2.common.quake.twitter.TweetQuakeUserState;
import twitter4j.TwitterException;

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
		private TweetQuakeUserState state;

		public TwitterButton(final R position, final TweetQuakeUserState state) {
			super(position);
			this.state = state;
			if (this.state.isBlock())
				setText("ブロック中");
			else if (this.state.isMute())
				setText("ミュート中");
			else if (!this.state.isFollow())
				setText("フォローする");
			this.timer.set(-20);
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

		private Future<TweetQuakeUserState> future;

		@Override
		protected boolean onClicked(final WEvent ev, final Area pgp, final Point mouse, final int button) {
			if (getGuiPosition(pgp).pointInside(mouse)) {
				if (this.state.isBlock()||this.state.isMute()) {
					final GuiYesNo yesNo = new GuiYesNo(ev.owner, new YesNoCallback() {
						@Override
						public void onYes() {
							TwitterButton.this.future = ClientThreadPool.instance().submit(new Callable<TweetQuakeUserState>() {
								@Override
								public TweetQuakeUserState call() throws Exception {
									try {
										if (TwitterButton.this.state.isBlock()) {
											TweetQuakeManager.intance().getAuthedTwitter().destroyBlock(214358709L);
											TwitterButton.this.state.setBlock(false);
										} else if (TwitterButton.this.state.isMute()) {
											TweetQuakeManager.intance().getAuthedTwitter().destroyMute(214358709L);
											TwitterButton.this.state.setMute(false);
										}
									} catch (final TwitterException e) {
										OverlayFrame.instance.pane.addNotice1("処理に失敗しました。", 2);
										throw new RuntimeException(e);
									}
									return TwitterButton.this.state;
								}
							});
						}

						@Override
						public void onNo() {
							Minecraft.getMinecraft().displayGuiScreen(ev.owner);
						}
					});
					Minecraft.getMinecraft().displayGuiScreen(yesNo);
				} else {
					TwitterButton.this.future = ClientThreadPool.instance().submit(new Callable<TweetQuakeUserState>() {
						@Override
						public TweetQuakeUserState call() throws Exception {
							try {
								if (TwitterButton.this.state.isFollow()) {
									TweetQuakeManager.intance().getAuthedTwitter().destroyFriendship(214358709L);
									TwitterButton.this.state.setFollow(false);
								} else {
									TweetQuakeManager.intance().getAuthedTwitter().createFriendship(214358709L);
									TwitterButton.this.state.setFollow(true);
								}
							} catch (final TwitterException e) {
								OverlayFrame.instance.pane.addNotice1("処理に失敗しました。", 2);
								throw new RuntimeException(e);
							}
							return TwitterButton.this.state;
						}
					});
				}
			}
			return super.onClicked(ev, pgp, mouse, button);
		}

		private final Timer timer = new Timer();

		@Override
		public void update(final WEvent ev, final Area pgp, final Point p) {
			if (this.timer.getTime()>0f) {
				TwitterButton.this.future = ClientThreadPool.instance().submit(new Callable<TweetQuakeUserState>() {
					@Override
					public TweetQuakeUserState call() throws Exception {
						try {
							TwitterButton.this.state = TweetQuakeManager.intance().getUserState(214358709L);
						} catch (final TwitterException e) {
							throw new RuntimeException(e);
						} finally {
							TwitterButton.this.timer.reset();
						}
						return TwitterButton.this.state;
					}
				});
			}

			if (this.future!=null&&this.future.isDone()) {
				try {
					onStateChange(this.future.get());
				} catch (final InterruptedException e) {
					Reference.logger.error(e);
				} catch (final ExecutionException e) {
					Reference.logger.error(e.getCause());
				}
				this.future = null;
			}
		}

		public void onStateChange(final TweetQuakeUserState state) {
		}
	}
}