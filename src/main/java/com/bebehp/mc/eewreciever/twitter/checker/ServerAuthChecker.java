package com.bebehp.mc.eewreciever.twitter.checker;

import com.bebehp.mc.eewreciever.ChatUtil;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.ServerTickEvent;
import net.minecraft.server.MinecraftServer;

public class ServerAuthChecker {

	public static final ServerAuthChecker INSTANCE = new ServerAuthChecker();
	public static int playerCount;

	@SubscribeEvent
	public void onServerTick(final ServerTickEvent event) {
		if (event.phase == Phase.END && playerCount < MinecraftServer.getServer().getCurrentPlayerCount()) {
			ChatUtil.sendServerChat(ChatUtil.byText("TEST!!"));
		}
		playerCount = MinecraftServer.getServer().getCurrentPlayerCount();
	}
}
