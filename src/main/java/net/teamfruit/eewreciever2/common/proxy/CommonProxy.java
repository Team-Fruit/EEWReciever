package net.teamfruit.eewreciever2.common.proxy;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.MinecraftForge;
import net.teamfruit.eewreciever2.Reference;
import net.teamfruit.eewreciever2.common.CommonHandler;

public class CommonProxy {

	public void preInit(final FMLPreInitializationEvent event) {
		Reference.logger = event.getModLog();
	}

	public void init(final FMLInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(new CommonHandler());
	}

	public void postInit(final FMLPostInitializationEvent event) {

	}
}
