package net.teamfruit.eewreciever2.common;

import java.io.File;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;

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
				legacySetting.renameTo(new File(this.modCfgDir, "setting.dat"));
		}
	}
}
