package net.teamfruit.eewreciever2.client.proxy;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.common.MinecraftForge;
import net.teamfruit.eewreciever2.client.ClientHandler;
import net.teamfruit.eewreciever2.common.proxy.CommonProxy;

public class ClientProxy extends CommonProxy {

	@Override
	public void init(final FMLInitializationEvent event) {
		super.init(event);
		MinecraftForge.EVENT_BUS.register(new ClientHandler());
	}
}
