package net.teamfruit.eewreciever2.server.proxy;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import net.teamfruit.eewreciever2.common.proxy.CommonProxy;
import net.teamfruit.eewreciever2.server.QuakeHandler;

public class ServerProxy extends CommonProxy {

	@Override
	public void init(final FMLInitializationEvent event) {
		super.init(event);

		FMLCommonHandler.instance().bus().register(new QuakeHandler());
	}
}
