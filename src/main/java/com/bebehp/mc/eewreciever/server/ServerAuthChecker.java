package com.bebehp.mc.eewreciever.server;

import com.bebehp.mc.eewreciever.common.ChatUtil;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

public class ServerAuthChecker {

	public static final ServerAuthChecker INSTANCE = new ServerAuthChecker();
	public static boolean notification = true;

	@SubscribeEvent
	public void onPlayerLogin(final PlayerEvent.PlayerLoggedInEvent event) {
		final String name =  event.player.getGameProfile().getName();
		final MinecraftServer mc = FMLCommonHandler.instance().getMinecraftServerInstance();
		final EntityPlayerMP playerMP = mc.getPlayerList().getPlayerByUsername(name);
		if (notification) {
			if (isOP(name)) {
				playerMP.addChatComponentMessage(ChatUtil.byText("[EEWReciever]Twitter連携認証(緊急地震速報)がされていません！"));
				playerMP.addChatComponentMessage(ChatUtil.byText("/eew setup でセットアップを開始します"));
				playerMP.addChatComponentMessage(ChatUtil.byText("Twitter連携を無効にするには、configを変更し再起動して下さい"));
			} else {
				playerMP.addChatComponentMessage(ChatUtil.byText("[EEWReciever]Setupされていません。OPに連絡して下さい。"));
			}
		}
	}

	private boolean isOP(final String name) {
		final String[] opPlayers = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getOppedPlayerNames();
		for (final String line : opPlayers) {
			if (line == name)
				return true;
		}
		return false;
	}
}
