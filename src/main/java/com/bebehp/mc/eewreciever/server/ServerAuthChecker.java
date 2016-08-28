package com.bebehp.mc.eewreciever.server;

import com.bebehp.mc.eewreciever.common.ChatUtil;
import com.mojang.authlib.GameProfile;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;

public class ServerAuthChecker {

	public static final ServerAuthChecker INSTANCE = new ServerAuthChecker();

	@SubscribeEvent
	public void onPlayerLogin(final PlayerEvent.PlayerLoggedInEvent event) {
		final GameProfile profile =  event.player.getGameProfile();
		final ServerConfigurationManager configManager = MinecraftServer.getServer().getConfigurationManager();
		final EntityPlayerMP playerMP = configManager.func_152612_a(profile.getName());
		if (configManager.func_152596_g(profile)) {
			playerMP.addChatComponentMessage(ChatUtil.byText("[EEWReciever]Twitter連携認証(緊急地震速報)がされていません！"));
			playerMP.addChatComponentMessage(ChatUtil.byText("/eew setup でセットアップを開始します"));
			playerMP.addChatComponentMessage(ChatUtil.byText("Twitter連携を無効にするには、configを変更し再起動して下さい"));
		} else {
			playerMP.addChatComponentMessage(ChatUtil.byText("[EEWReciever]Setupされていません。OPに連絡して下さい。"));
		}
	}

}
