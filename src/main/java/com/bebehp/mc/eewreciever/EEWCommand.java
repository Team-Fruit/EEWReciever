package com.bebehp.mc.eewreciever;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.bebehp.mc.eewreciever.p2pquake.P2PQuakeNode;
import com.bebehp.mc.eewreciever.ping.QuakeException;
import com.bebehp.mc.eewreciever.twitter.OAuthHelper;
import com.bebehp.mc.eewreciever.twitter.TweetQuakeCommand;
import com.bebehp.mc.eewreciever.twitter.TweetQuakeNode;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import twitter4j.TwitterException;

public class EEWCommand extends CommandBase {
	private final TweetQuakeCommand tqc;

	public EEWCommand(final TweetQuakeCommand tqc) {
		this.tqc = tqc;
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
	public int getRequiredPermissionLevel() {
		return 3;
	}

	@Override
	public String getCommandUsage(final ICommandSender icommandsender) {
		return "/eewreciever Tipe <Text>";
	}

	@Override
	public void processCommand(final ICommandSender icommandsender, final String[] astring) {
		if (astring.length >= 1 && (StringUtils.equalsIgnoreCase(astring[0], "p2p") || StringUtils.equalsIgnoreCase(astring[0], "p"))) {
			if (ConfigurationHandler.debugMode) {
				if (astring.length >= 2) {
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
		} else if (astring.length >= 1 && (StringUtils.equalsIgnoreCase(astring[0], "twitter") || StringUtils.equalsIgnoreCase(astring[0], "t"))) {
			if (ConfigurationHandler.debugMode) {
				if (astring.length >= 2) {
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
		} else if (astring.length >= 1 && StringUtils.equalsIgnoreCase(astring[0], "setup")) {
			if (ConfigurationHandler.twitterEnable) {
				if (astring.length >= 2) {
					if (StringUtils.equalsIgnoreCase(astring[1], "pin")) {
						if (TweetQuakeCommand.setupSender == null || TweetQuakeCommand.setupSender == icommandsender) {
							final String chat = func_82360_a(icommandsender, astring, 2);
							if (StringUtils.isNumeric(chat) && chat.length() == 7) {
								try {
									OAuthHelper.storeAccessToken(OAuthHelper.getAccessTokentoPin(chat));
									ChatUtil.sendPlayerChat(icommandsender, ChatUtil.byText("認証が完了しました"));
									TweetQuakeCommand.setupSender = null;
									ChatUtil.sendPlayerChat(icommandsender, ChatUtil.byText("Setupを終了します"));
								} catch (final TwitterException e) {
									Reference.logger.error(e);
									ChatUtil.sendPlayerChat(icommandsender, ChatUtil.byText("認証に失敗しました もう1度お試しください").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
								}
							} else {
								ChatUtil.sendPlayerChat(icommandsender, ChatUtil.byText("Pinコードが不正です！").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
							}
						} else {
							ChatUtil.sendPlayerChat(icommandsender, ChatUtil.byText("Setupは現在利用できません").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
						}
					} else if (StringUtils.equalsIgnoreCase(astring[1], "geturl")) {
						this.tqc.sendURL(icommandsender);
					} else {
						ChatUtil.sendPlayerChat(icommandsender, ChatUtil.byText("/eewreciever setup pin <Pin>"));
					}
				} else {
					this.tqc.setup(icommandsender);
				}
			} else {
				ChatUtil.sendPlayerChat(icommandsender, ChatUtil.byText("Twitter連携が無効な為 利用できません").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
			}
		} else if (astring.length >= 1 && StringUtils.equalsIgnoreCase(astring[0], "reset")) {
			this.tqc.reset(icommandsender);
		} else if (astring.length >= 1 && StringUtils.equalsIgnoreCase(astring[0], "help")) {
			final List<String> list = new LinkedList<String>(Arrays.asList("setup", "reset"));
			if (ConfigurationHandler.debugMode)
				list.addAll(Arrays.asList("p2p", "twitter"));

			ChatUtil.sendPlayerChat(icommandsender, ChatUtil.byText("--- Help page ---").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.DARK_GREEN)));
			for (final String line : list) {
				ChatUtil.sendPlayerChat(icommandsender, ChatUtil.byText("/eew " + EnumChatFormatting.YELLOW + line));
			}
		} else {
			ChatUtil.sendPlayerChat(icommandsender, ChatUtil.byText("Unknown command. Try /eew help for a list of commands").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
		}
	}

	@Override
	public List<String> addTabCompletionOptions(final ICommandSender icommandsender, final String[] astring) {
		if (astring.length <= 1 && ConfigurationHandler.debugMode) {
			return Arrays.asList("p2p", "twitter", "setup", "reset");
		} else if (astring.length <= 1) {
			return Arrays.asList("setup", "reset");
		} else if ((astring.length <= 2 && (StringUtils.equalsIgnoreCase(astring[0], "p2p") || StringUtils.equalsIgnoreCase(astring[0], "p"))) && ConfigurationHandler.debugMode) {
			return Arrays.asList("00:00:00,QUA,01日00時00分/1/0/4/震央/10km/2.5/0/N35.0/E140.0/サンプル");
		} else if ((astring.length <= 2 && (StringUtils.equalsIgnoreCase(astring[0], "twitter") || StringUtils.equalsIgnoreCase(astring[0], "t"))) && ConfigurationHandler.debugMode) {
			return Arrays.asList("37,00,2011/04/03 23:53:51,0,1,NDID,2011/04/03 23:53:21,37.8,142.3,宮城県沖,10,2.5,1,1,0,サンプル");
		} else if (astring.length <= 2 && StringUtils.equalsIgnoreCase(astring[0], "setup")) {
			return Arrays.asList("pin", "geturl");
		} else {
			return null;
		}
	}
}
