package com.bebehp.mc.eewreciever.twitter;

import com.bebehp.mc.eewreciever.ChatUtil;
import com.bebehp.mc.eewreciever.ConfigurationHandler;
import com.bebehp.mc.eewreciever.EEWRecieverMod;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ServerTickEvent;
import net.minecraft.client.Minecraft;

public class TweetQuakeAuthChecker {

	private boolean notification = true;

	@SubscribeEvent
	public void onServerTick(final ServerTickEvent event) {
		if (ConfigurationHandler.twitterEnable && EEWRecieverMod.accessToken == null && Minecraft.getMinecraft().thePlayer != null && this.notification) {
			ChatUtil.sendServerChat(ChatUtil.byText("TEST!!"));
			this.notification = false;
		}
	}
}
