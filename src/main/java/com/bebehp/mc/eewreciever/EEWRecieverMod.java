package com.bebehp.mc.eewreciever;

import java.util.Map;

import com.bebehp.mc.eewreciever.common.proxy.CommonProxy;
import com.bebehp.mc.eewreciever.common.twitter.TweetQuakeKey;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkCheckHandler;
import cpw.mods.fml.relauncher.Side;
import twitter4j.auth.AccessToken;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class EEWRecieverMod {

	@SidedProxy(serverSide = Reference.PROXY_SERVER, clientSide = Reference.PROXY_CLIENT)
	public static CommonProxy proxy;

	public static TweetQuakeKey tweetQuakeKey;
	public static AccessToken accessToken;

	@Instance(Reference.MODID)
	public static EEWRecieverMod instance;

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

	@NetworkCheckHandler
	public boolean netCheckHandler(final Map<String, String> mods, final Side side) {
		return true;
	}

	public static void sendServerChat(final String msg) {
		final String[] linemsg = msg.split("\n");
		for (final String line : linemsg) {
			ChatUtil.sendServerChat(ChatUtil.byText(line));
		}
	}
}