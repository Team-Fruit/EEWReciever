package net.teamfruit.eewreciever2.common;

import java.io.File;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ServerTickEvent;

public class CommonHandler {
	public static File modConfigDir;
	public static QuakeEventExecutor main = new QuakeEventExecutor();

	@SubscribeEvent
	public void onServerTick(final ServerTickEvent event) {
		main.onServerTick(event);
	}
}
