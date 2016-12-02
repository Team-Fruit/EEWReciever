package net.teamfruit.eewreciever2.common;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.teamfruit.eewreciever2.Reference;

public class Locations {
	public File cfgDir;
	public File modCfgDir;
	public File modFile;

	public Locations(final FMLPreInitializationEvent event) {
		this.cfgDir = event.getModConfigurationDirectory();
		this.modCfgDir = new File(event.getModConfigurationDirectory(), Reference.MODID);
		this.modFile = event.getSourceFile();
	}

	public void checkLegacy(final File modConfigurationDirectory) {
		final File legacy = new File(modConfigurationDirectory, "EEWReciever");
		if (legacy.exists()) {
			Reference.logger.info("This directory is not currently in use[{}]", legacy);
			final File legacySetting = new File(legacy, "setting.dat");
			if (legacySetting.exists())
				try {
					FileUtils.copyFileToDirectory(legacySetting, this.modCfgDir);
				} catch (final IOException e) {
					Reference.logger.error("Failed to reuse Legacy.");
				}
		}
	}
}
