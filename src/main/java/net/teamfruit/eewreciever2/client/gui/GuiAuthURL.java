package net.teamfruit.eewreciever2.client.gui;

import java.awt.Desktop;
import java.net.URI;

import com.kamesuta.mc.bnnwidget.WBox;
import com.kamesuta.mc.bnnwidget.WEvent;
import com.kamesuta.mc.bnnwidget.WPanel;
import com.kamesuta.mc.bnnwidget.component.MButton;
import com.kamesuta.mc.bnnwidget.component.MChatTextField;
import com.kamesuta.mc.bnnwidget.component.MScaledLabel;
import com.kamesuta.mc.bnnwidget.position.Area;
import com.kamesuta.mc.bnnwidget.position.Coord;
import com.kamesuta.mc.bnnwidget.position.Point;
import com.kamesuta.mc.bnnwidget.position.R;

import net.minecraft.client.gui.GuiScreen;
import twitter4j.TwitterException;

public class GuiAuthURL extends WPanel {
	private static String authurl;

	protected final MChatTextField textField;

	protected boolean initURL;

	public GuiAuthURL(final R position) {
		super(position);

		this.textField = new MChatTextField(new R(Coord.left(10), Coord.right(10), Coord.top(65), Coord.height(20))) {
			@Override
			public void onAdded() {
				super.onAdded();
				setMaxStringLength(Integer.MAX_VALUE);
				setCanLoseFocus(false);
				setText("Loading");
				setEnabled(false);
			}

			private boolean initField;

			@Override
			public void update(final WEvent ev, final Area pgp, final Point p) {
				super.update(ev, pgp, p);
				if (GuiAuthURL.this.initURL&&!this.initField) {
					if (authurl!=null) {
						setText(authurl);
						setCursorPositionZero();
						setEnabled(true);
					} else {
						setText("URLの取得に失敗しました。");
						setEnabled(false);
					}
					this.initField = true;
				}
			}
		};

		if (authurl==null)
			new Thread() {
				@Override
				public void run() {
					try {
						authurl = GuiAuth.auther.getAuthURL();
					} catch (final TwitterException e) {
					}
					GuiAuthURL.this.initURL = true;
				}
			}.start();
	}

	@Override
	protected void initWidget() {
		add(new MScaledLabel(new R(Coord.left(10), Coord.right(10), Coord.top(35), Coord.height(10))) {
			{
				setColor(0);
				setText("以下のURLからTwitterにログインし");

			}
		});
		add(new MScaledLabel(new R(Coord.left(10), Coord.right(10), Coord.top(45), Coord.height(10))) {
			{
				setColor(0);
				setText("連携アプリを認証して下さい。");
			}
		});
		add(this.textField);
		add(new MButton(new R(Coord.left(15), Coord.width(75), Coord.top(95), Coord.height(20))) {
			{
				setText("コピー");
			}

			@Override
			public void update(final WEvent ev, final Area pgp, final Point p) {
				super.update(ev, pgp, p);
				setEnabled(authurl!=null);
			}

			@Override
			protected boolean onClicked(final WEvent ev, final Area pgp, final Point mouse, final int button) {
				GuiScreen.setClipboardString(GuiAuthURL.this.textField.getText());
				return true;
			}
		});
		add(new MButton(new R(Coord.right(15), Coord.width(75), Coord.top(95), Coord.height(20))) {
			{
				setText("開く");
			}

			@Override
			public void update(final WEvent ev, final Area pgp, final Point p) {
				super.update(ev, pgp, p);
				setEnabled(authurl!=null);
			}

			@Override
			protected boolean onClicked(final WEvent ev, final Area pgp, final Point mouse, final int button) {
				try {
					Desktop.getDesktop().browse(new URI(GuiAuthURL.this.textField.getText()));
				} catch (final Exception e) {
					OverlayFrame.instance.pane.addNotice1(e.getClass().getName(), 2);
				}
				return true;
			}
		});
		add(new MButton(new R(Coord.pleft(.5f), Coord.width(80), Coord.top(130), Coord.height(20)).child(Coord.pleft(-.5f))) {
			{
				setText("次へ");
			}

			@Override
			protected boolean onClicked(final WEvent ev, final Area pgp, final Point mouse, final int button) {
				final WBox box = (WBox) ev.data.get("box");
				box.invokeLater(new Runnable() {
					@Override
					public void run() {
						box.add(new GuiAuthPin(new R()));
					}
				});
				return true;
			}
		});
	}
}