package com.bebehp.mc.eewreciever.server;

import com.bebehp.mc.eewreciever.ChatUtil;

import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ServerAuthChecker {

	public static final ServerAuthChecker INSTANCE = new ServerAuthChecker();
	public static boolean notification = true;

	@SideOnly(Side.SERVER)
	@SubscribeEvent
	public void onPlayerLogin(final PlayerEvent.PlayerLoggedInEvent event) {
		final String name =  event.player.getGameProfile().getName();
		if (notification) {
			if (isOP(name)) {
				event.player.addChatComponentMessage(ChatUtil.byText("[EEWReciever]Twitter連携認証(緊急地震速報)がされていません！"));
				event.player.addChatComponentMessage(ChatUtil.byText("/eew setup でセットアップを開始します"));
				event.player.addChatComponentMessage(ChatUtil.byText("Twitter連携を無効にするには、configを変更し再起動して下さい"));
			} else {
				event.player.addChatComponentMessage(ChatUtil.byText("[EEWReciever]Setupされていません。OPに連絡して下さい。"));
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