package net.teamfruit.eewreciever2.common.proxy;

import java.io.File;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.teamfruit.eewreciever2.EEWReciever2;
import net.teamfruit.eewreciever2.common.ConfigHandler;
import net.teamfruit.eewreciever2.common.Locations;
import net.teamfruit.eewreciever2.common.Reference;
import net.teamfruit.eewreciever2.common.quake.QuakeEventExecutor;
import net.teamfruit.eewreciever2.common.quake.p2pquake.P2PQuake;
import net.teamfruit.eewreciever2.common.quake.twitter.TweetQuakeSecure;

public class CommonProxy {

	public void preInit(final FMLPreInitializationEvent event) {
		Reference.logger = event.getModLog();

		EEWReciever2.locations = new Locations(event);

		if (!EEWReciever2.locations.modCfgDir.exists())
			if (!EEWReciever2.locations.modCfgDir.mkdir())
				Reference.logger.warn("Could not create Condiguration Directory[{}]!", EEWReciever2.locations.modCfgDir);

		EEWReciever2.locations.checkLegacy(event.getModConfigurationDirectory());

		ConfigHandler.instance = new ConfigHandler(new File(EEWReciever2.locations.modCfgDir, Reference.MODID+".cfg"));
		TweetQuakeSecure.instance.init();
	}

	public void init(final FMLInitializationEvent event) {
		FMLCommonHandler.instance().bus().register(ConfigHandler.instance);
		FMLCommonHandler.instance().bus().register(QuakeEventExecutor.instance());

		QuakeEventExecutor.instance().register(new P2PQuake());
	}

	public void postInit(final FMLPostInitializationEvent event) {
	}
}
