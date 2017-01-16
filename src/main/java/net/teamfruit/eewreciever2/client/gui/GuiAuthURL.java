package net.teamfruit.eewreciever2.client.gui;

import java.awt.Desktop;
import java.net.URI;

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

	static {
		try {
			authurl = GuiAuth.auther.getAuthURL();
		} catch (final TwitterException e) {
		}
	}

	protected final MChatTextField textField;
	protected final MButton copyButton;
	protected final MButton openButton;

	public GuiAuthURL(final R position) {
		super(position);

		this.textField = new MChatTextField(new R(Coord.left(10), Coord.right(10), Coord.top(65), Coord.height(20))) {
			@Override
			public void onAdded() {
				super.onAdded();
				setMaxStringLength(Integer.MAX_VALUE);
				setCanLoseFocus(false);
				if (GuiAuth.auther!=null)
					setText(authurl);
				else {
					setEnabled(false);
					setText("問題が発生したため、URLを取得出来ませんでした。");
					GuiAuthURL.this.copyButton.setEnabled(false);
					GuiAuthURL.this.openButton.setEnabled(false);
				}
			}
		};

		this.copyButton = new MButton(new R(Coord.left(15), Coord.width(75), Coord.top(95), Coord.height(20))) {
			@Override
			public void onAdded() {
				super.onAdded();
				setText("コピー");
			}

			@Override
			protected boolean onClicked(final WEvent ev, final Area pgp, final Point mouse, final int button) {
				GuiScreen.setClipboardString(GuiAuthURL.this.textField.getText());
				return true;
			}
		};

		this.openButton = new MButton(new R(Coord.right(15), Coord.width(75), Coord.top(95), Coord.height(20))) {
			@Override
			public void onAdded() {
				super.onAdded();
				setText("開く");
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
		};
	}

	@Override
	protected void initWidget() {
		add(new MScaledLabel(new R(Coord.left(10), Coord.right(10), Coord.top(35), Coord.height(10))) {
			@Override
			public void onAdded() {
				super.onAdded();
				setColor(0);
				setText("以下のURLからTwitterにログインし");
			}
		});

		add(new MScaledLabel(new R(Coord.left(10), Coord.right(10), Coord.top(45), Coord.height(10))) {
			@Override
			public void onAdded() {
				super.onAdded();
				setColor(0);
				setText("連携アプリを認証して下さい。");
			}
		});

		add(this.textField);
		add(this.copyButton);
		add(this.openButton);

		add(new MButton(new R(Coord.pleft(.5f), Coord.width(80), Coord.top(130), Coord.height(20)).child(Coord.pleft(-.5f))) {
			@Override
			public void onAdded() {
				super.onAdded();
				setText("次へ");
			}

			@Override
			protected boolean onClicked(final WEvent ev, final Area pgp, final Point mouse, final int button) {

				return true;
			}
		});
	}
}
