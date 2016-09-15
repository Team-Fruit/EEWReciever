package com.bebehp.mc.eewreciever.common;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Nullable;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

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
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;

public abstract class EEWCommandBase extends CommandBase {
	public ICommandSender setupSender;
	private final String randomString = RandomStringUtils.randomAlphabetic(10);

	public EEWCommandBase() { //NO-OP
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
	public void execute(final MinecraftServer server, final ICommandSender icommandsender, final String[] astring) throws CommandException {
		if (astring.length >= 1 && (StringUtils.equalsIgnoreCase(astring[0], "p2p") || StringUtils.equalsIgnoreCase(astring[0], "p"))) {
			if (ConfigurationHandler.debugMode || !limitInDebugMode()) {
				if (astring.length >= 2) {
					final String chat = buildString(astring, 1);
					try {
						EEWRecieverMod.sendServerChat(new P2PQuakeNode().parseString(chat).toString());
					} catch (final QuakeException e) {
						Reference.logger.error(e);
					}
				} else {
					ChatUtil.sendPlayerChat(icommandsender, ChatUtil.byText("/eewreciever p2p <Text>"));
				}
			} else {
				ChatUtil.sendPlayerChat(icommandsender, ChatUtil.byText("For debug mode is disabled, this command is not available").setStyle(new Style().setColor(TextFormatting.RED)));
			}
		} else if (astring.length >= 1 && (StringUtils.equalsIgnoreCase(astring[0], "twitter") || StringUtils.equalsIgnoreCase(astring[0], "t"))) {
			if (ConfigurationHandler.debugMode || !limitInDebugMode()) {
				if (astring.length >= 2) {
					final String chat = buildString(astring, 1);
					try {
						EEWRecieverMod.sendServerChat(new TweetQuakeNode().parseString(chat).toString());
					} catch (final QuakeException e) {
						Reference.logger.error(e);
					}
				} else {
					ChatUtil.sendPlayerChat(icommandsender, ChatUtil.byText("/eewreciever twitter <Text>"));
				}
			} else {
				ChatUtil.sendPlayerChat(icommandsender, ChatUtil.byText("For debug mode is disabled, this command is not available").setStyle(new Style().setColor(TextFormatting.RED)));
			}
		} else if (astring.length >= 1 && StringUtils.equalsIgnoreCase(astring[0], "setup")) {
			if (ConfigurationHandler.twitterEnable) {
				if (astring.length >= 2) {
					if (StringUtils.equalsIgnoreCase(astring[1], "pin")) {
						if (this.setupSender == null || this.setupSender == icommandsender) {
							final String chat = buildString(astring, 2);
							if (StringUtils.isNumeric(chat) && chat.length() == 7) {
								try {
									final AccessToken accessToken = TweetQuakeSetup.INSTANCE.getAccessToken(chat);
									TweetQuakeFileManager.storeAccessToken(accessToken);
									EEWRecieverMod.accessToken = accessToken;
									ChatUtil.sendPlayerChat(icommandsender, ChatUtil.byText("認証が完了しました"));
									QuakeMain.INSTANCE.setTweetQuake(new TweetQuake());
									ChatUtil.sendPlayerChat(icommandsender, ChatUtil.byText("Twitterに接続し、Setupを終了します"));
									this.setupSender = null;
								} catch (final TwitterException e) {
									Reference.logger.error(e);
									ChatUtil.sendPlayerChat(icommandsender, ChatUtil.byText("認証に失敗しました").setStyle(new Style().setColor(TextFormatting.RED)));
								}
							} else {
								ChatUtil.sendPlayerChat(icommandsender, ChatUtil.byText("Pinコードが不正です！").setStyle(new Style().setColor(TextFormatting.RED)));
							}
						} else {
							ChatUtil.sendPlayerChat(icommandsender, ChatUtil.byText("Setupは現在利用できません").setStyle(new Style().setColor(TextFormatting.RED)));
						}
					} else if (StringUtils.equalsIgnoreCase(astring[1], "geturl")) {
						try {
							ChatUtil.sendPlayerChat(icommandsender, ChatUtil.byText(TweetQuakeSetup.INSTANCE.setOAuthConsumer().getAuthURL()));
						} catch (final TwitterException e) {
							ChatUtil.sendPlayerChat(icommandsender, ChatUtil.byText("URLの生成に失敗しました").setStyle(new Style().setColor(TextFormatting.RED)));
							Reference.logger.error(e.getStatusCode());
						}
					} else if (StringUtils.equalsIgnoreCase(astring[1], "stop")) {
						this.setupSender = null;
						ChatUtil.sendPlayerChat(icommandsender, ChatUtil.byText("Setupを中止しました"));
					} else {
						ChatUtil.sendPlayerChat(icommandsender, ChatUtil.byText("/eewreciever setup pin <Pin>"));
					}
				} else {
					if (this.setupSender == null) {
						if (EEWRecieverMod.accessToken == null) {
							this.setupSender = icommandsender;
							ClientAuthChecker.notification = false;
							ServerAuthChecker.notification = false;
							ChatUtil.sendPlayerChat(icommandsender, ChatUtil.byText("EEWReciever TwitterSetupを開始します"));
							try {
								final StringBuilder stb = new StringBuilder();
								stb.append("{\"text\":\"Twitterと連携設定をし、Pinコードを入手して下さい(クリックでURLを開く)\",\"color\":\"gold\",\"clickEvent\":{\"action\":\"open_url\",\"value\":\"");
								stb.append(TweetQuakeSetup.INSTANCE.setOAuthConsumer().getAuthURL());
								stb.append("\"}}");
								ChatUtil.sendPlayerChat(icommandsender, ChatUtil.byJson(new String(stb)));
							} catch (final TwitterException e) {
								Reference.logger.error(e.getStatusCode());
								ChatUtil.sendPlayerChat(icommandsender, ChatUtil.byText("URLの生成に失敗しました").setStyle(new Style().setColor(TextFormatting.RED)));
								ChatUtil.sendPlayerChat(icommandsender, ChatUtil.byText("Setupを終了します").setStyle(new Style().setColor(TextFormatting.RED)));
								this.setupSender = null;
								return;
							}
							ChatUtil.sendPlayerChat(icommandsender, ChatUtil.byJson("{\"text\":\"/eew setup pin <Pin>でコードを入力して下さい(クリックで提案)\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"/eewreciever setup pin \"}}"));
						} else {
							ChatUtil.sendPlayerChat(icommandsender, ChatUtil.byText("Twitter連携は設定済みです！").setStyle(new Style().setColor(TextFormatting.RED)));
						}
					} else if (this.setupSender == icommandsender){
						ChatUtil.sendPlayerChat(icommandsender, ChatUtil.byText("あなたは現在Setupを実行中です！").setStyle(new Style().setColor(TextFormatting.RED)));
					} else {
						ChatUtil.sendPlayerChat(icommandsender, ChatUtil.byText("Setupは現在利用出来ません").setStyle(new Style().setColor(TextFormatting.RED)));
					}
				}
			} else {
				ChatUtil.sendPlayerChat(icommandsender, ChatUtil.byText("Twitter連携が無効な為 利用できません").setStyle(new Style().setColor(TextFormatting.RED)));
			}
		} else if (astring.length >= 1 && StringUtils.equalsIgnoreCase(astring[0], "deletesettings")) {
			if (astring.length >= 2) {
				final String chat = buildString(astring, 1);
				if (StringUtils.equalsIgnoreCase(astring[1], this.randomString)) {
					if (TweetQuakeFileManager.deleteAccessTokenFile()) {
						ChatUtil.sendPlayerChat(icommandsender, ChatUtil.byText("消去に成功しました"));
					} else {
						ChatUtil.sendPlayerChat(icommandsender, ChatUtil.byText("消去に失敗しました").setStyle(new Style().setColor(TextFormatting.RED)));
					}
				} else {
					ChatUtil.sendPlayerChat(icommandsender, ChatUtil.byText("消去キーが違います").setStyle(new Style().setColor(TextFormatting.RED)));
				}
			} else {
				final StringBuilder stb = new StringBuilder();
				stb.append("{\"text\":\"本当に消去しますか？実行するにはチャットをクリックして下さい\",\"color\":\"light_purple\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"");
				stb.append("/eewreciever deletesettings ");
				stb.append(this.randomString);
				stb.append("\"}}");
				ChatUtil.sendPlayerChat(icommandsender, ChatUtil.byJson(new String(stb)));
			}
		} else if (astring.length >= 1 && StringUtils.equalsIgnoreCase(astring[0], "help")) {
			final List<String> list = new LinkedList<String>(Arrays.asList("setup", "deletesettings"));
			if (ConfigurationHandler.debugMode)
				list.addAll(Arrays.asList("p2p", "twitter"));

			ChatUtil.sendPlayerChat(icommandsender, ChatUtil.byText("--- Help page ---").setStyle(new Style().setColor(TextFormatting.DARK_GREEN)));
			for (final String line : list) {
				ChatUtil.sendPlayerChat(icommandsender, ChatUtil.byText("/eew " + TextFormatting.YELLOW + line));
			}
		} else {
			ChatUtil.sendPlayerChat(icommandsender, ChatUtil.byText("Unknown command. Try /eew help for a list of commands").setStyle(new Style().setColor(TextFormatting.RED)));
		}
	}

	@Override
	public List<String> getTabCompletionOptions(final MinecraftServer server, final ICommandSender sender, final String[] astring, @Nullable final BlockPos pos) {
		if (astring.length <= 1 && (ConfigurationHandler.debugMode || !limitInDebugMode())) {
			return Arrays.asList("p2p", "twitter", "setup", "deletesettings");
		} else if (astring.length == 1) {
			return Arrays.asList("setup", "deletesettings");
		} else if ((astring.length == 2 && (StringUtils.equalsIgnoreCase(astring[0], "p2p") || StringUtils.equalsIgnoreCase(astring[0], "p"))) && (ConfigurationHandler.debugMode || !limitInDebugMode())) {
			return Arrays.asList("00:00:00,QUA,01日00時00分/1/0/4/宮城県沖/10km/3.5/0/N35.0/E140.0/サンプル");
		} else if ((astring.length == 2 && (StringUtils.equalsIgnoreCase(astring[0], "twitter") || StringUtils.equalsIgnoreCase(astring[0], "t"))) && (ConfigurationHandler.debugMode || !limitInDebugMode())) {
			return Arrays.asList("37,00,2011/04/03 23:53:51,0,1,NDID,2011/04/03 23:53:21,37.8,142.3,宮城県沖,10,2.5,1,1,0,サンプル");
		} else if (astring.length == 2 && StringUtils.equalsIgnoreCase(astring[0], "setup")) {
			return Arrays.asList("pin", "geturl", "stop");
		} else {
			return null;
		}
	}
}
