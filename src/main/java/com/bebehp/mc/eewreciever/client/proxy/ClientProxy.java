package com.bebehp.mc.eewreciever.client.proxy;

import com.bebehp.mc.eewreciever.EEWRecieverMod;
import com.bebehp.mc.eewreciever.client.ClientAuthChecker;
import com.bebehp.mc.eewreciever.client.ClientEEWCommand;
import com.bebehp.mc.eewreciever.common.handler.ConfigurationHandler;
import com.bebehp.mc.eewreciever.common.proxy.CommonProxy;
import com.bebehp.mc.eewreciever.common.twitter.TweetQuakeSetup;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

public class ClientProxy extends CommonProxy {

	@Override
	public void init(final FMLInitializationEvent event) {
		super.init(event);

		if (ConfigurationHandler.twitterEnable && EEWRecieverMod.accessToken == null)
			MinecraftForge.EVENT_BUS.register(ClientAuthChecker.INSTANCE);
	}

	@Override
	public void serverLoad(final FMLServerStartingEvent event){
		super.serverLoad(event);

		event.registerServerCommand(new ClientEEWCommand(new TweetQuakeSetup()));
	}
}
