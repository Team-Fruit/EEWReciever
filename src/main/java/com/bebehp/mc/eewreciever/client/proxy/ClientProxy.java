package com.bebehp.mc.eewreciever.client.proxy;

import com.bebehp.mc.eewreciever.EEWRecieverMod;
import com.bebehp.mc.eewreciever.client.ClientAuthChecker;
import com.bebehp.mc.eewreciever.client.ClientEEWCommand;
import com.bebehp.mc.eewreciever.common.handler.ConfigurationHandler;
import com.bebehp.mc.eewreciever.common.proxy.CommonProxy;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.common.MinecraftForge;

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

		event.registerServerCommand(new ClientEEWCommand());
	}
}
