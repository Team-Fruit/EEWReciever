package net.teamfruit.eewreciever2.common.proxy;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.teamfruit.eewreciever2.EEWReciever2;
import net.teamfruit.eewreciever2.Reference;
import net.teamfruit.eewreciever2.common.Locations;
import net.teamfruit.eewreciever2.common.QuakeEventExecutor;
import net.teamfruit.eewreciever2.common.twitter.TweetQuakeSecure;

public class CommonProxy {

	public void preInit(final FMLPreInitializationEvent event) {
		Reference.logger = event.getModLog();

		EEWReciever2.locations = new Locations(event);

		if (!EEWReciever2.locations.modCfgDir.exists())
			if (!EEWReciever2.locations.modCfgDir.mkdir())
				Reference.logger.warn("Could not create Condiguration Directory[{}]!", EEWReciever2.locations.modCfgDir);

		EEWReciever2.locations.checkLegacy(event.getModConfigurationDirectory());

		TweetQuakeSecure.instance.init();
	}

	public void init(final FMLInitializationEvent event) {
		FMLCommonHandler.instance().bus().register(new QuakeEventExecutor());
	}

	public void postInit(final FMLPostInitializationEvent event) {
	}
}
