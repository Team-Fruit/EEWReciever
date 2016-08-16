package com.bebehp.mc.eewreciever.twitter;

import com.bebehp.mc.eewreciever.ChatUtil;
import com.bebehp.mc.eewreciever.Reference;

import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;

public class TweetQuakeCommand {

	public Twitter twitter = TwitterFactory.getSingleton();
	public TwitterStream twitterStream;

	/**
	 * setupを実行中のICommandSender
	 */
	public static ICommandSender setupSender;

	public void setup(final ICommandSender sender) {
		if (setupSender == null) {
			if (OAuthHelper.loadAccessToken() == null) {
				setupSender = sender;
				ChatUtil.sendPlayerChat(sender, ChatUtil.byText("[WIP]EEWReciever TwitterSetupを開始します"));
				try {
					final StringBuilder stb = new StringBuilder();
					stb.append("{\"text\":\"[ Twitterと連携設定をし、Pinコードを入手して下さい(クリックでURLを開く) ]\",\"color\":\"gold\",\"clickEvent\":{\"action\":\"open_url\",\"value\":\"");
					stb.append(OAuthHelper.ceateAuthorizationURL());
					stb.append("\"}}");
					ChatUtil.sendPlayerChat(sender, ChatUtil.byJson(new String(stb)));
				} catch (final TwitterException e) {
					Reference.logger.error(e.getStatusCode());
					ChatUtil.sendPlayerChat(sender, ChatUtil.byText("URLの生成に失敗しました").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
					ChatUtil.sendPlayerChat(sender, ChatUtil.byText("Setupを終了します").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
					setupSender = null;
					return;
				}
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
}
