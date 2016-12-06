package net.teamfruit.eewreciever2.client.proxy;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.common.MinecraftForge;
import net.teamfruit.eewreciever2.client.QuakeHandler;
import net.teamfruit.eewreciever2.client.gui.OverlayFrame;
import net.teamfruit.eewreciever2.common.proxy.CommonProxy;

public class ClientProxy extends CommonProxy {

	@Override
	public void init(final FMLInitializationEvent event) {
		super.init(event);

		FMLCommonHandler.instance().bus().register(new QuakeHandler());
		MinecraftForge.EVENT_BUS.register(OverlayFrame.instance);
		FMLCommonHandler.instance().bus().register(OverlayFrame.instance);
	}
}
