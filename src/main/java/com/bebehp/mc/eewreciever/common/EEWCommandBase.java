package com.bebehp.mc.eewreciever.common;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import com.bebehp.mc.eewreciever.ChatUtil;
import com.bebehp.mc.eewreciever.EEWRecieverMod;
import com.bebehp.mc.eewreciever.Reference;
import com.bebehp.mc.eewreciever.client.ClientAuthChecker;
import com.bebehp.mc.eewreciever.common.handler.ConfigurationHandler;
import com.bebehp.mc.eewreciever.common.p2pquake.P2PQuakeNode;
import com.bebehp.mc.eewreciever.common.twitter.TweetQuake;
import com.bebehp.mc.eewreciever.common.twitter.TweetQuakeFileManager;
import com.bebehp.mc.eewreciever.common.twitter.TweetQuakeNode;
import com.bebehp.mc.eewreciever.common.twitter.TweetQuakeSetup;
import com.bebehp.mc.eewreciever.server.ServerAuthChecker;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.event.ClickEvent;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;

public abstract class EEWCommandBase extends CommandBase {

	public ICommandSender setupSender;
	private final String randomString = RandomStringUtils.randomAlphabetic(10);
	private final TweetQuakeSetup tweetQuakeSetup;

	public EEWCommandBase(final TweetQuakeSetup tweetQuakeSetup) {
		this.tweetQuakeSetup = tweetQuakeSetup;
	}

	@Override
	public String getCommandName() {
		return "eewreciever";
	}

	@Override
	public List<String> getCommandAliases() {
		return Arrays.asList("eew");
	}

	@Override
	public abstract int getRequiredPermissionLevel();

	@Override
	public String getCommandUsage(final ICommandSender icommandsender) {
		return "/eewreciever Tipe <Text>";
	}

	public abstract boolean limitInDebugMode();

	@Override
	public void processCommand(final ICommandSender icommandsender, final String[] astring) {
		if (astring.length>=1&&(StringUtils.equalsIgnoreCase(astring[0], "p2p")||StringUtils.equalsIgnoreCase(astring[0], "p"))) {
			if (ConfigurationHandler.debugMode||!limitInDebugMode()) {
				if (astring.length>=2) {
					final String chat = func_82360_a(icommandsender, astring, 1);
					try {
						EEWRecieverMod.sendServerChat(new P2PQuakeNode().parseString(chat).toString());
					} catch (final QuakeException e) {
						Reference.logger.error(e);
					}
				} else {
					ChatUtil.sendPlayerChat(icommandsender, ChatUtil.byText("/eewreciever p2p <Text>"));
				}
			} else {
				ChatUtil.sendPlayerChat(icommandsender, ChatUtil.byText("For debug mode is disabled, this command is not available").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
			}
		} else if (astring.length>=1&&(StringUtils.equalsIgnoreCase(astring[0], "twitter")||StringUtils.equalsIgnoreCase(astring[0], "t"))) {
			if (ConfigurationHandler.debugMode||!limitInDebugMode()) {
				if (astring.length>=2) {
					final String chat = func_82360_a(icommandsender, astring, 1);
					try {
						EEWRecieverMod.sendServerChat(new TweetQuakeNode().parseString(chat).toString());
					} catch (final QuakeException e) {
						Reference.logger.error(e);
					}
				} else {
					ChatUtil.sendPlayerChat(icommandsender, ChatUtil.byText("/eewreciever twitter <Text>"));
				}
			} else {
				ChatUtil.sendPlayerChat(icommandsender, ChatUtil.byText("For debug mode is disabled, this command is not available").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
			}
		} else if (astring.length>=1&&StringUtils.equalsIgnoreCase(astring[0], "setup")) {
			if (ConfigurationHandler.twitterEnable) {
				if (astring.length>=2) {
					if (StringUtils.equalsIgnoreCase(astring[1], "pin")) {
						if (this.setupSender==null||this.setupSender==icommandsender) {
							final String chat = func_82360_a(icommandsender, astring, 2);
							if (StringUtils.isNumeric(chat)&&chat.length()==7) {
								try {
									final AccessToken accessToken = this.tweetQuakeSetup.getAccessToken(chat);
									TweetQuakeFileManager.storeAccessToken(accessToken);
									EEWRecieverMod.accessToken = accessToken;
									ChatUtil.sendPlayerChat(icommandsender, ChatUtil.byText("認証が完了しました"));
									QuakeMain.INSTANCE.setTweetQuake(new TweetQuake());
									ChatUtil.sendPlayerChat(icommandsender, ChatUtil.byText("Twitterに接続し、Setupを終了します"));
									this.setupSender = null;
								} catch (final TwitterException e) {
									Reference.logger.error(e);
									ChatUtil.sendPlayerChat(icommandsender, ChatUtil.byText("認証に失敗しました").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
								}
							} else {
								ChatUtil.sendPlayerChat(icommandsender, ChatUtil.byText("Pinコードが不正です！").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
							}
						} else {
							ChatUtil.sendPlayerChat(icommandsender, ChatUtil.byText("Setupは現在利用できません").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
						}
					} else if (StringUtils.equalsIgnoreCase(astring[1], "geturl")) {
						try {
							ChatUtil.sendPlayerChat(icommandsender, ChatUtil.byText(this.tweetQuakeSetup.getAuthURL()));
						} catch (final TwitterException e) {
							ChatUtil.sendPlayerChat(icommandsender, ChatUtil.byText("URLの生成に失敗しました").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
							Reference.logger.error(e.getStatusCode());
							Reference.logger.error(e);
						}
					} else if (StringUtils.equalsIgnoreCase(astring[1], "stop")) {
						this.setupSender = null;
						ChatUtil.sendPlayerChat(icommandsender, ChatUtil.byText("Setupを中止しました"));
					} else {
						ChatUtil.sendPlayerChat(icommandsender, ChatUtil.byText("/eewreciever setup pin <Pin>"));
					}
				} else {
					if (this.setupSender==null) {
						if (EEWRecieverMod.accessToken==null) {
							this.setupSender = icommandsender;
							ClientAuthChecker.notification = false;
							ServerAuthChecker.notification = false;
							ChatUtil.sendPlayerChat(icommandsender, ChatUtil.byText("EEWReciever TwitterSetupを開始します"));
							try {
								ChatUtil.sendPlayerChat(icommandsender, ChatUtil.byText("Twitterと連携設定をし、Pinコードを入手して下さい(クリックでURLを開く)")
										.setChatStyle(new ChatStyle().setColor(EnumChatFormatting.GOLD).setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, this.tweetQuakeSetup.getAuthURL()))));
							} catch (final TwitterException e) {
								Reference.logger.error(e.getStatusCode());
								ChatUtil.sendPlayerChat(icommandsender, ChatUtil.byText("URLの生成に失敗しました").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
								ChatUtil.sendPlayerChat(icommandsender, ChatUtil.byText("Setupを終了します").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
								this.setupSender = null;
								return;
							}
							ChatUtil.sendPlayerChat(icommandsender, ChatUtil.byText("/eew setup pin <Pin>でコードを入力して下さい(クリックで提案)")
									.setChatStyle(new ChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/eewreciever setup pin "))));
						} else {
							ChatUtil.sendPlayerChat(icommandsender, ChatUtil.byText("Twitter連携は設定済みです！").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
						}
					} else if (this.setupSender==icommandsender) {
						ChatUtil.sendPlayerChat(icommandsender, ChatUtil.byText("あなたは現在Setupを実行中です！").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
					} else {
						ChatUtil.sendPlayerChat(icommandsender, ChatUtil.byText("Setupは現在利用出来ません").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
					}
				}
			} else {
				ChatUtil.sendPlayerChat(icommandsender, ChatUtil.byText("Twitter連携が無効な為 利用できません").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
			}
		} else if (astring.length>=1&&StringUtils.equalsIgnoreCase(astring[0], "deletesettings")) {
			if (astring.length>=2) {
				final String chat = func_82360_a(icommandsender, astring, 1);
				if (StringUtils.equalsIgnoreCase(astring[1], this.randomString)) {
					if (TweetQuakeFileManager.deleteAccessTokenFile()) {
						ChatUtil.sendPlayerChat(icommandsender, ChatUtil.byText("消去に成功しました"));
					} else {
						ChatUtil.sendPlayerChat(icommandsender, ChatUtil.byText("消去に失敗しました").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
					}
				} else {
					ChatUtil.sendPlayerChat(icommandsender, ChatUtil.byText("消去キーが違います").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
				}
			} else {
				ChatUtil.sendPlayerChat(icommandsender, ChatUtil.byText("本当に消去しますか？実行するにはチャットをクリックして下さい")
						.setChatStyle(new ChatStyle().setColor(EnumChatFormatting.LIGHT_PURPLE).setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/eewreciever deletesettings "+this.randomString))));
			}
		} else if (astring.length>=1&&StringUtils.equalsIgnoreCase(astring[0], "enable")) {
			if (!QuakeMain.INSTANCE.getStatus()) {
				QuakeMain.INSTANCE.setStatus(true);
				ChatUtil.sendPlayerChat(icommandsender, ChatUtil.byText("[EEWReciever] Enabled"));
			} else {
				ChatUtil.sendPlayerChat(icommandsender, ChatUtil.byText("EEWRecieverはすでに有効です。").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
			}
		} else if (astring.length>=1&&StringUtils.equalsIgnoreCase(astring[0], "disable")) {
			if (QuakeMain.INSTANCE.getStatus()) {
				QuakeMain.INSTANCE.setStatus(false);
				ChatUtil.sendPlayerChat(icommandsender, ChatUtil.byText("[EEWReciever] Disabled"));
			} else {
				ChatUtil.sendPlayerChat(icommandsender, ChatUtil.byText("EEWRecieverはすでに無効です。").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
			}
		} else if (astring.length>=1&&StringUtils.equalsIgnoreCase(astring[0], "help")) {
			final List<String> list = new LinkedList<String>(Arrays.asList("setup", "deletesettings", "enabled", "disabled"));
			if (ConfigurationHandler.debugMode)
				list.addAll(Arrays.asList("p2p", "twitter"));

			ChatUtil.sendPlayerChat(icommandsender, ChatUtil.byText("--- Help page ---").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.DARK_GREEN)));
			for (final String line : list) {
				ChatUtil.sendPlayerChat(icommandsender, ChatUtil.byText("/eew "+EnumChatFormatting.YELLOW+line));
			}
		} else {
			ChatUtil.sendPlayerChat(icommandsender, ChatUtil.byText("Unknown command. Try /eew help for a list of commands").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
		}
	}

	@Override
	public List<String> addTabCompletionOptions(final ICommandSender icommandsender, final String[] astring) {
		if (astring.length<=1&&(ConfigurationHandler.debugMode||!limitInDebugMode())) {
			return getListOfStringsMatchingLastWord(astring, "p2p", "twitter", "setup", "deletesettings", "enable", "disable");
		} else if (astring.length==1) {
			return getListOfStringsMatchingLastWord(astring, "setup", "deletesettings", "enabled", "disabled");
		} else if ((astring.length==2&&(StringUtils.equalsIgnoreCase(astring[0], "p2p")||StringUtils.equalsIgnoreCase(astring[0], "p")))&&(ConfigurationHandler.debugMode||!limitInDebugMode())) {
			return Arrays.asList("00:00:00,QUA,01日00時00分/1/0/4/宮城県沖/10km/3.5/0/N35.0/E140.0/サンプル");
		} else if ((astring.length==2&&(StringUtils.equalsIgnoreCase(astring[0], "twitter")||StringUtils.equalsIgnoreCase(astring[0], "t")))&&(ConfigurationHandler.debugMode||!limitInDebugMode())) {
			return Arrays.asList("37,00,2011/04/03 23:53:51,0,1,NDID,2011/04/03 23:53:21,37.8,142.3,宮城県沖,10,2.5,1,1,0,サンプル");
		} else if (astring.length==2&&StringUtils.equalsIgnoreCase(astring[0], "setup")) {
			return getListOfStringsMatchingLastWord(astring, "pin", "geturl", "stop");
		} else {
			return null;
		}
	}
}
