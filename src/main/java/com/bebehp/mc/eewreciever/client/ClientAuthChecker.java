package com.bebehp.mc.eewreciever.client;

import com.bebehp.mc.eewreciever.ChatUtil;
import com.bebehp.mc.eewreciever.EEWRecieverMod;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ClientAuthChecker {

	public static final ClientAuthChecker INSTANCE = new ClientAuthChecker();
	public static boolean notification = true;

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onTick(final ClientTickEvent event) {
		final Minecraft mc = Minecraft.getMinecraft();
		if (EEWRecieverMod.accessToken == null && notification && mc.isSingleplayer() && event.phase == Phase.END && mc.thePlayer != null) {
			final EntityPlayer player = Minecraft.getMinecraft().thePlayer;
			player.addChatComponentMessage(ChatUtil.byText("[EEWReciever]Twitter連携認証(緊急地震速報)がされていません！"));
			player.addChatComponentMessage(ChatUtil.byText("/eew setup でセットアップを開始します"));
			player.addChatComponentMessage(ChatUtil.byText("Twitter連携を無効にするには、configを変更し再起動して下さい"));
			notification = false;
		}
	}

}
