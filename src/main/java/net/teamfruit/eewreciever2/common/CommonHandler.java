package net.teamfruit.eewreciever2.common;

import java.io.File;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ServerTickEvent;
import net.teamfruit.eewreciever2.common.twitter.TweetQuakeClient;

public class CommonHandler {
	public static File modConfigDir;
	public static QuakeMain main = new QuakeMain();
	public static TweetQuakeClient twitter = new TweetQuakeClient();

	@SubscribeEvent
	public void onServerTick(final ServerTickEvent event) {
		main.onServerTick(event);
	}
}
