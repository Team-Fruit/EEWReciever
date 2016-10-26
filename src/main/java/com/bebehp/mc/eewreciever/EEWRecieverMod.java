package com.bebehp.mc.eewreciever;

import java.util.Map;

import com.bebehp.mc.eewreciever.common.proxy.CommonProxy;
import com.bebehp.mc.eewreciever.common.twitter.TweetQuakeKey;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import twitter4j.auth.AccessToken;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class EEWRecieverMod {

	@SidedProxy(serverSide = Reference.PROXY_SERVER, clientSide = Reference.PROXY_CLIENT)
	public static CommonProxy proxy;

	public static TweetQuakeKey tweetQuakeKey;
	public static AccessToken accessToken;

	@EventHandler
	public void preInit(final FMLPreInitializationEvent event) {
		proxy.preInit(event);
	}

	@EventHandler
	public void init(final FMLInitializationEvent event) {
		proxy.init(event);
	}

	@EventHandler
	public void serverLoad(final FMLServerStartingEvent event) {
		proxy.serverLoad(event);
	}

	public static void sendServerChat(final String msg) {
		final String[] linemsg = msg.split("\n");
		for (final String line : linemsg) {
			ChatUtil.sendServerChat(ChatUtil.byText(line));
		}
	}
}