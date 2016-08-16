package com.bebehp.mc.eewreciever.twitter;

import java.io.File;

import com.bebehp.mc.eewreciever.ChatUtil;
import com.bebehp.mc.eewreciever.Reference;

import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.auth.RequestToken;

public class TweetQuakeCommand {

	public Twitter twitter = TwitterFactory.getSingleton();
	public TwitterStream twitterStream;

	/**
	 * setupを実行中のICommandSender
	 */
	public static ICommandSender setupSender;

	public boolean sendURL(final ICommandSender sender) {
		RequestToken requestToken = null;
		try {
			requestToken = this.twitter.getOAuthRequestToken();
			final StringBuilder stb = new StringBuilder();
			stb.append("{\"text\":\"[ Twitterと連携設定をし、Pinコードを入手して下さい(クリックでURLを開く) ]\",\"color\":\"gold\",\"clickEvent\":{\"action\":\"open_url\",\"value\":\"");
			stb.append(requestToken.getAuthorizationURL());
			stb.append("\"}}");
			ChatUtil.sendPlayerChat(sender, ChatUtil.byJson(new String(stb)));
			return true;
		} catch (final IllegalStateException e) {
			ChatUtil.sendPlayerChat(sender, ChatUtil.byText("Twitter連携は設定済みです！ URLは生成出来ませんでした").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
			Reference.logger.error(e);
			return false;
		} catch (final TwitterException e) {
			ChatUtil.sendPlayerChat(sender, ChatUtil.byText("URLの生成に失敗しました").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
			Reference.logger.error(e.getStatusCode());
			return false;
		}
	}

	public void setup(final ICommandSender sender) {
		if (setupSender == null) {
			if (OAuthHelper.loadAccessToken() == null) {
				setupSender = sender;
				ChatUtil.sendPlayerChat(sender, ChatUtil.byText("[WIP]EEWReciever TwitterSetupを開始します"));
				if (sendURL(sender))
					ChatUtil.sendPlayerChat(sender, ChatUtil.byJson("{\"text\":\"/eew setup pin <Pin>でコードを入力して下さい(クリックで提案)\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"/eewreciever setup pin\"}}"));
			} else {
				ChatUtil.sendPlayerChat(sender, ChatUtil.byText("Twitter連携は設定済みです！").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
			}
		} else if (setupSender == sender){
			ChatUtil.sendPlayerChat(sender, ChatUtil.byText("あなたは現在Setupを実行中です！").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
		} else {
			ChatUtil.sendPlayerChat(sender, ChatUtil.byText("Setupは現在利用出来ません").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
		}
	}

	/**
	 * UserStreamをshutdownし、createAccessTokenFileName()を消去します
	 * 結果をsenderにチャット送信します
	 * @param sender
	 * @author bebe
	 * @deprecated
	 */
	@Deprecated
	public void reset(final ICommandSender sender) {
		final File filename = OAuthHelper.createAccessTokenFileName();
		Reference.logger.info("UserStreamを終了します");
		this.twitterStream.shutdown();
		if (filename.exists()) {
			if (filename.delete()) {
				ChatUtil.sendPlayerChat(sender, ChatUtil.byText("設定ファイルが正常に消去されました"));
				ChatUtil.sendPlayerChat(sender, ChatUtil.byJson("{\"text\":\"/eewreciever setup で再設定を行えます\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"/eewreciever setup\"}}"));
			} else {
				ChatUtil.sendPlayerChat(sender, ChatUtil.byText("設定ファイルを正常に消去出来ませんでした").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
			}
		} else {
			ChatUtil.sendPlayerChat(sender, ChatUtil.byText("設定ファイルが見つかりませんでした").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
		}
	}
}
