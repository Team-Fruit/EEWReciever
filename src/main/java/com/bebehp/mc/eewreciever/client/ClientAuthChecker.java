package com.bebehp.mc.eewreciever.client;

import com.bebehp.mc.eewreciever.EEWRecieverMod;
import com.bebehp.mc.eewreciever.common.ChatUtil;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class ClientAuthChecker {

	public static final ClientAuthChecker INSTANCE = new ClientAuthChecker();
	public static boolean notification = true;

	@SubscribeEvent
	public void onTick(final ClientTickEvent event) {
		if (EEWRecieverMod.accessToken == null && notification && event.phase == Phase.END && Minecraft.getMinecraft().thePlayer != null) {
			final EntityPlayer player = Minecraft.getMinecraft().thePlayer;
			player.addChatComponentMessage(ChatUtil.byText("[EEWReciever]Twitter連携認証(緊急地震速報)がされていません！"));
			player.addChatComponentMessage(ChatUtil.byText("/eew setup でセットアップを開始します"));
			player.addChatComponentMessage(ChatUtil.byText("Twitter連携を無効にするには、configを変更し再起動して下さい"));
			notification = false;
		}
	}

}
