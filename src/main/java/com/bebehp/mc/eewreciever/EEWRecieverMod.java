package com.bebehp.mc.eewreciever;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;

@Mod(modid="EEWReciever", name="EEWReciever", version="1.0")
public class EEWRecieverMod
{
	public static final Logger logger = LogManager.getLogger("EEWReciever");

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		logger.info("EEW is setting up.");
	}
}