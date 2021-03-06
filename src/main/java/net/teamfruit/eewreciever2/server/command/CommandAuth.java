package net.teamfruit.eewreciever2.server.command;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

import net.minecraft.command.ICommandSender;
import net.minecraft.event.ClickEvent;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.teamfruit.eewreciever2.common.Reference;
import net.teamfruit.eewreciever2.common.command.IModCommand;
import net.teamfruit.eewreciever2.common.command.SubCommand;
import net.teamfruit.eewreciever2.common.quake.twitter.TweetQuakeAuther;
import net.teamfruit.eewreciever2.common.quake.twitter.TweetQuakeAuther.AuthState;
import net.teamfruit.eewreciever2.common.quake.twitter.TweetQuakeManager;
import net.teamfruit.eewreciever2.common.util.ChatBuilder;
import twitter4j.TwitterException;

public class CommandAuth extends SubCommand {

	private TweetQuakeAuther auther;

	public CommandAuth() {
		super("auth");
		addChildCommand(new CommandStartAuth());
		addChildCommand(new CommandGetURL());
		addChildCommand(new CommandSetPin());
		setPermLevel(PermLevel.ADMIN);
	}

	protected static void printAuthStart(final ICommandSender sender) {
		final ChatStyle style = new ChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/eewreciever auth start"));
		new ChatBuilder().setText("認証が開始されていません！ クリックして開始").setStyle(style).sendPlayer(sender);
	}

	protected static void printRetry(final ICommandSender sender, final IModCommand command) {
		printRetry("申し訳ありません！エラーが発生した為正常に処理が出来ませんでした。クリックしてリトライ", sender, command);
	}

	protected static void printRetry(final String text, final ICommandSender sender, final IModCommand command) {
		final ChatStyle style = new ChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/"+command.getFullCommandString())).setColor(EnumChatFormatting.RED);
		new ChatBuilder().setText(text).setStyle(style).sendPlayer(sender);
	}

	private class CommandStartAuth extends SubCommand {

		public CommandStartAuth() {
			super("start");
		}

		@Override
		public void processSubCommand(final ICommandSender sender, final String[] args) {
			if (CommandAuth.this.auther==null) {
				CommandAuth.this.auther = TweetQuakeManager.intance().getAuther();
				ChatBuilder.create("Twitter認証を開始します。").sendPlayer(sender);
				final ChatStyle style1 = new ChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://twitter.com/eewbot"));
				ChatBuilder.create("初めに、クリックして開くアカウントを、認証に利用するアカウントでフォローして下さい。(フォローしていないと動作しません)").setStyle(style1).sendPlayer(sender);
				final ChatStyle style2 = new ChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/eewreciever auth url"));
				ChatBuilder.create("完了したら、クリックして認証に進みます。").setStyle(style2).sendPlayer(sender);
			} else if (CommandAuth.this.auther.getState()!=AuthState.CONNECT)
				ChatBuilder.create("認証はすでに開始しています！").sendPlayer(sender);
			else
				ChatBuilder.create("認証は済んでいます！").sendPlayer(sender);
		}
	}

	private class CommandGetURL extends SubCommand {

		public CommandGetURL() {
			super("url");
		}

		@Override
		public void processSubCommand(final ICommandSender sender, final String[] args) {
			if (CommandAuth.this.auther==null)
				printAuthStart(sender);
			else if (CommandAuth.this.auther.getState()!=TweetQuakeAuther.AuthState.START)
				new ChatBuilder().setText("このコマンドは現在有効ではありません。").setStyle(new ChatStyle().setColor(EnumChatFormatting.RED)).sendPlayer(sender);
			else {
				try {
					final ChatStyle style1 = new ChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, CommandAuth.this.auther.getAuthURL()));
					new ChatBuilder().setText("クリックして認証URLを開く").setStyle(style1).sendPlayer(sender);
					final ChatStyle style2 = new ChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/eewreciever auth pin <これを消して数字にしてください>"));
					new ChatBuilder().setText("認証IDを手に入れたらクリックしてIDを入力").setStyle(style2).sendPlayer(sender);
				} catch (final TwitterException e) {
					Reference.logger.error(e.getMessage(), e);
					CommandAuth.printRetry(sender, this);
				}
			}
		}
	}

	private class CommandSetPin extends SubCommand {

		public CommandSetPin() {
			super("token");
		}

		@Override
		public void processSubCommand(final ICommandSender sender, final String[] args) {
			if (CommandAuth.this.auther==null)
				printAuthStart(sender);
			else if (CommandAuth.this.auther.getState()!=TweetQuakeAuther.AuthState.URL)
				new ChatBuilder().setText("このコマンドは現在有効ではありません。").setStyle(new ChatStyle().setColor(EnumChatFormatting.RED)).sendPlayer(sender);
			else if (args.length>=3)
				new ChatBuilder().setText("/eewreciever auth pin <ID>").setStyle(new ChatStyle().setColor(EnumChatFormatting.RED)).sendPlayer(sender);
			else {
				final String pin = args[2];
				if (isPin(pin)) {
					try {
						CommandAuth.this.auther.getAccessToken(pin).storeAccessToken().connect();
					} catch (final TwitterException e) {
						Reference.logger.error(e.getMessage(), e);
						CommandAuth.printRetry(sender, this);
					} catch (final IOException e) {
						CommandAuth.printRetry("申し訳ありません！キーの保存に失敗しました。クリックしてリトライ", sender, this);
					}
				}
			}
		}

		private boolean isPin(final String pin) {
			return StringUtils.isNumeric(pin)&&pin.length()==7;
		}
	}
}
