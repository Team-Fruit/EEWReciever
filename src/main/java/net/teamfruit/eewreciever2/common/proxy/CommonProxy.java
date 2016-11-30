package net.teamfruit.eewreciever2.common.proxy;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.MinecraftForge;
import net.teamfruit.eewreciever2.Reference;
import net.teamfruit.eewreciever2.common.CommonHandler;

public class CommonProxy {

	public void preInit(final FMLPreInitializationEvent event) {
		Reference.logger = event.getModLog();

		CommonHandler.modConfigDir = new File(event.getModConfigurationDirectory(), Reference.MODID.toLowerCase());
		if (!CommonHandler.modConfigDir.exists())
			if (!CommonHandler.modConfigDir.mkdir())
				Reference.logger.warn("Could not create Condiguration Directory[{}]!", CommonHandler.modConfigDir);

		checkLegacy(event.getModConfigurationDirectory());

		CommonHandler.twitter.init();
	}

	public void init(final FMLInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(new CommonHandler());
	}

	public void postInit(final FMLPostInitializationEvent event) {

	}

	public void checkLegacy(final File modConfigurationDirectory) {
		final File legacy = new File(modConfigurationDirectory, "EEWReciever");
		if (legacy.exists()) {
			Reference.logger.info("This directory is not currently in use[{}]", legacy);
			final File legacySetting = new File(legacy, "setting.dat");
			if (legacySetting.exists())
				try {
					FileUtils.copyFileToDirectory(legacySetting, CommonHandler.modConfigDir);
				} catch (final IOException e) {
					Reference.logger.error("Failed to reuse Legacy.");
				}
		}
	}
}
