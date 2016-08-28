package com.bebehp.mc.eewreciever.server;

import com.bebehp.mc.eewreciever.EEWRecieverMod;
import com.bebehp.mc.eewreciever.common.ChatUtil;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.ServerTickEvent;
import net.minecraft.server.MinecraftServer;

public class ServerAuthChecker {

	public static final ServerAuthChecker INSTANCE = new ServerAuthChecker();
	public static int playerCount;
	public static int noiceCount;

	@SubscribeEvent
	public void onServerTick(final ServerTickEvent event) {
		final int currentCount = MinecraftServer.getServer().getCurrentPlayerCount();
		if (EEWRecieverMod.accessToken == null && noiceCount >= 5 && event.phase == Phase.END && playerCount < currentCount) {
			ChatUtil.sendServerChat(ChatUtil.byText("[EEWReciever]Twitter連携認証(緊急地震速報)がされていません！"));
			ChatUtil.sendServerChat(ChatUtil.byText("/eew setup でセットアップを開始します"));
			ChatUtil.sendServerChat(ChatUtil.byText("Twitter連携を無効にするには、configを変更し再起動して下さい"));
			noiceCount++;
		}
		playerCount = currentCount;
	}
}
