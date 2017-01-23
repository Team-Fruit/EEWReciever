package net.teamfruit.eewreciever2.client.gui;

import java.io.IOException;

import org.lwjgl.util.Timer;

import com.kamesuta.mc.bnnwidget.WBox;
import com.kamesuta.mc.bnnwidget.WEvent;
import com.kamesuta.mc.bnnwidget.WPanel;
import com.kamesuta.mc.bnnwidget.component.MButton;
import com.kamesuta.mc.bnnwidget.component.MChatTextField;
import com.kamesuta.mc.bnnwidget.component.MLabel;
import com.kamesuta.mc.bnnwidget.component.MScaledLabel;
import com.kamesuta.mc.bnnwidget.position.Area;
import com.kamesuta.mc.bnnwidget.position.Coord;
import com.kamesuta.mc.bnnwidget.position.Point;
import com.kamesuta.mc.bnnwidget.position.R;

import net.teamfruit.eewreciever2.common.quake.twitter.TweetQuakeAuther.AuthState;
import net.teamfruit.eewreciever2.common.quake.twitter.TweetQuakeManager;
import net.teamfruit.eewreciever2.common.quake.twitter.TweetQuakeUserState;
import twitter4j.TwitterException;

public class GuiAuthPin extends WPanel {
	protected final StatusTextLabel statusText;

	private boolean auth;

	public boolean isAuthed() {
		return this.auth;
	}

	public GuiAuthPin(final R position) {
		super(position);
		this.statusText = new StatusTextLabel(new R(Coord.left(10), Coord.right(10), Coord.top(90), Coord.height(10)));
	}

	@Override
	protected void initWidget() {
		add(new MScaledLabel(new R(Coord.left(10), Coord.right(10), Coord.top(45), Coord.height(10))) {
			{
				setColor(0);
				setText("入手したPINコードを入力してください");
			}
		});
		add(new MChatTextField(new R(Coord.pleft(.5f), Coord.width(60), Coord.height(20), Coord.top(65)).child(Coord.pleft(-.5f))) {
			{
				setMaxStringLength(7);
				setAllowedCharacters("0123456789");
			}

			private String oldText;

			@Override
			public void update(final WEvent ev, final Area pgp, final Point p) {
				super.update(ev, pgp, p);
				if (GuiAuth.auther.getState()==AuthState.URL) {
					if (!getText().equals(this.oldText)) {
						if (getText().length()==7) {
							GuiAuthPin.this.statusText.setText("認証中");
							new Thread() {
								@Override
								public void run() {
									try {
										GuiAuth.auther.getAccessToken(getText()).storeAccessToken();
										GuiAuthPin.this.statusText.setText("認証成功");
										try {
											ev.data.put("state", TweetQuakeManager.intance().getUserState(214358709L));
										} catch (final TwitterException e) {
											GuiAuthPin.this.statusText.setErrorText("ユーザー情報の取得に失敗しました "+e.getMessage());
										}
										GuiAuthPin.this.auth = true;
									} catch (final TwitterException e) {
										GuiAuthPin.this.statusText.setErrorText("認証に失敗しました "+e.getMessage());
									} catch (final IOException e) {
										GuiAuthPin.this.statusText.setErrorText("認証トークンの保存に失敗しました "+e.getMessage());
									}
								};
							}.start();
						}
					}
				} else if (GuiAuth.auther.getState()==AuthState.TOKEN) {
					GuiAuthPin.this.statusText.setText("認証成功");
					new Thread() {
						@Override
						public void run() {
							try {
								ev.data.put("state", TweetQuakeManager.intance().getUserState(214358709L));
							} catch (final TwitterException e) {
								GuiAuthPin.this.statusText.setErrorText("ユーザー情報の取得に失敗しました "+e.getMessage());
							}
						};
					}.start();
					GuiAuthPin.this.auth = true;
				}
				this.oldText = getText();
			}

			@Override
			public boolean canAddChar(final char c) {
				final boolean b = super.canAddChar(c);
				if (!b)
					GuiAuthPin.this.statusText.setErrorText("半角数字のみが入力出来ます！");
				return b;
			}
		});
		add(this.statusText);
		add(new MButton(new R(Coord.left(15), Coord.width(75), Coord.top(130), Coord.height(20))) {
			{
				setText("戻る");
			}

			@Override
			protected boolean onClicked(final WEvent ev, final Area pgp, final Point mouse, final int button) {
				final WBox box = (WBox) ev.data.get("box");
				box.invokeLater(new Runnable() {
					@Override
					public void run() {
						box.add(new GuiAuthURL(new R()));
					}
				});
				return true;
			}
		});
		add(new MButton(new R(Coord.right(15), Coord.width(75), Coord.top(130), Coord.height(20))) {
			{
				setEnabled(false);
				setText("次へ");
			}

			private boolean finish;
			private boolean stateChecked;

			@Override
			public void update(final WEvent ev, final Area pgp, final Point p) {
				super.update(ev, pgp, p);
				setEnabled(isAuthed());
				setText(this.finish ? "終了" : "次へ");
				if (!this.stateChecked&&isAuthed()) {
					final Object obj = ev.data.get("state");
					if (obj!=null) {
						final TweetQuakeUserState state = (TweetQuakeUserState) obj;
						this.finish = state.isFollow()&&!state.isBlock()&&!state.isMute();
						this.stateChecked = true;
					}
				}
			}

			@Override
			protected boolean onClicked(final WEvent ev, final Area pgp, final Point mouse, final int button) {
				final WBox box = (WBox) ev.data.get("box");
				box.invokeLater(new Runnable() {
					@Override
					public void run() {
						if (finish) {
							ev.owner.requestClose();
							GuiAuth.auther.connect();
						} else
							box.add(new GuiAuthFollow(new R(), (TweetQuakeUserState) ev.data.get("state")));
					}
				});
				return true;
			}
		});
	}

	public static class StatusTextLabel extends MScaledLabel {

		public StatusTextLabel(final R position) {
			super(position);
			setColor(0);
		}

		@Override
		public MLabel setText(final String s) {
			setColor(0);
			return super.setText(s);
		}

		protected Timer timer;

		public MLabel setErrorText(final String s) {
			this.timer = new Timer();
			this.timer.set(-3);
			setColor(0xdd5555);
			return super.setText(s);
		}

		@Override
		public void update(final WEvent ev, final Area pgp, final Point p) {
			super.update(ev, pgp, p);
			if (this.timer!=null)
				if (this.timer.getTime()>0f)
					super.setText("");
		}
	}
}