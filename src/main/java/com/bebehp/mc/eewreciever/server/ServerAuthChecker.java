package com.bebehp.mc.eewreciever.server;

import com.bebehp.mc.eewreciever.common.ChatUtil;
import com.mojang.authlib.GameProfile;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;

public class ServerAuthChecker {

	public static final ServerAuthChecker INSTANCE = new ServerAuthChecker();
	public static boolean notification = true;

	@SideOnly(Side.SERVER)
	@SubscribeEvent
	public void onPlayerLogin(final PlayerEvent.PlayerLoggedInEvent event) {
		if (notification) {
			final GameProfile profile =  event.player.getGameProfile();
			final ServerConfigurationManager configManager = MinecraftServer.getServer().getConfigurationManager();
			if (configManager.func_152596_g(profile)) {
				event.player.addChatComponentMessage(ChatUtil.byText("[EEWReciever]Twitter連携認証(緊急地震速報)がされていません！"));
				event.player.addChatComponentMessage(ChatUtil.byText("/eew setup でセットアップを開始します"));
				event.player.addChatComponentMessage(ChatUtil.byText("Twitter連携を無効にするには、configを変更し再起動して下さい"));
			} else {
				event.player.addChatComponentMessage(ChatUtil.byText("[EEWReciever]Setupされていません。OPに連絡して下さい。"));
			}
		}
	}

}
