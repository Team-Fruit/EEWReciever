package com.bebehp.mc.eewreciever.twitter.checker;

import com.bebehp.mc.eewreciever.ChatUtil;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import net.minecraft.client.Minecraft;

public class ClientAuthChecker {

	public static final ClientAuthChecker INSTANCE = new ClientAuthChecker();
	public static boolean notification;

	@SubscribeEvent
	public void onTick(final ClientTickEvent event) {
		if (!notification && event.phase == Phase.END && Minecraft.getMinecraft().thePlayer != null) {
			ChatUtil.sendServerChat(ChatUtil.byText("TEST!!"));
			notification = true;
		}
	}

}
