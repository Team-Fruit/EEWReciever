package com.bebehp.mc.eewreciever.server.proxy;

import com.bebehp.mc.eewreciever.EEWRecieverMod;
import com.bebehp.mc.eewreciever.common.handler.ConfigurationHandler;
import com.bebehp.mc.eewreciever.common.proxy.CommonProxy;
import com.bebehp.mc.eewreciever.common.twitter.TweetQuakeSetup;
import com.bebehp.mc.eewreciever.server.ServerAuthChecker;
import com.bebehp.mc.eewreciever.server.ServerEEWCommand;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

public class ServerProxy extends CommonProxy {

	@Override
	public void init(final FMLInitializationEvent event) {
		super.init(event);

		if (ConfigurationHandler.twitterEnable && EEWRecieverMod.accessToken == null)
			MinecraftForge.EVENT_BUS.register(ServerAuthChecker.INSTANCE);
	}

	@Override
	public void serverLoad(final FMLServerStartingEvent event){
		super.serverLoad(event);

		event.registerServerCommand(new ServerEEWCommand(new TweetQuakeSetup()));
	}
}
