package net.teamfruit.eewreciever2.common;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ServerTickEvent;

public class CommonHandler {
	public static QuakeMain main = new QuakeMain();

	@SubscribeEvent
	public void onServerTick(final ServerTickEvent event) {
		main.onServerTick(event);
	}
}
