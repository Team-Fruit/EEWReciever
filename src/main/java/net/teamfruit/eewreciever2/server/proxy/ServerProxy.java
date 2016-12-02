package net.teamfruit.eewreciever2.server.proxy;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.common.MinecraftForge;
import net.teamfruit.eewreciever2.common.proxy.CommonProxy;
import net.teamfruit.eewreciever2.server.ServerHandler;

public class ServerProxy extends CommonProxy {

	@Override
	public void init(final FMLInitializationEvent event) {
		super.init(event);

		MinecraftForge.EVENT_BUS.register(new ServerHandler());
	}
}
