package com.bebehp.mc.eewreciever;
import java.io.File;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class ConfigurationHandler {
	public static final ConfigurationHandler INSTANCE = new ConfigurationHandler();
	public static final String VERSION = "1";
	public static Configuration configuration;

	public static final boolean TWITTER_ENABLE_DEFAULT = true;
	public static boolean twitterEnable = TWITTER_ENABLE_DEFAULT;
	public static Property propTwitterEnable = null;

	public static final boolean P2PQUAKE_ENABLE_DEFAULT = true;
	public static boolean p2pQuakeEnable = TWITTER_ENABLE_DEFAULT;
	public static Property propP2PQuakeEnable = null;

	public static final boolean FORCE_LEVEL_DEFAULT = true;
	public static boolean forceLevel = FORCE_LEVEL_DEFAULT;
	public static Property propForceLevel = null;

	public static final boolean DEBUG_MODE_DEFAULT = false;
	public static boolean debugMode = DEBUG_MODE_DEFAULT;
	public static Property propDebugMode = null;

	public static void init(final File configFile) {
		if (configuration == null) {
			configuration = new Configuration(configFile, VERSION);
			loadConfiguration();
		}
	}

	public static void loadConfiguration() {
		propTwitterEnable = configuration.get("EEW", "isTwitterAPIEnabled",
				TWITTER_ENABLE_DEFAULT, "Enabling this, it'll be cheking with twitter API.");
		twitterEnable = propTwitterEnable.getBoolean();

		propP2PQuakeEnable = configuration.get("EEW", "isP2PQuakeAPIEnabled",
				P2PQUAKE_ENABLE_DEFAULT, "Enabling this, it'll be cheking with p2pQuake API.");
		p2pQuakeEnable = propP2PQuakeEnable.getBoolean();

		propForceLevel = configuration.get("EEW", "forceLevel",
				FORCE_LEVEL_DEFAULT, "Enabling this, it'll be force mode.");
		forceLevel = propForceLevel.getBoolean();

		propDebugMode = configuration.get("EEW", "isDebugModeEnabled",
				DEBUG_MODE_DEFAULT, "Enabling this, it'll be debug mode.");
		debugMode = propDebugMode.getBoolean();

		if (configuration.hasChanged()) {
			configuration.save();
		}
	}

	private ConfigurationHandler() {
	}

	@SubscribeEvent
	public void onConfigurationChangedEvent(final ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.modID.equalsIgnoreCase(Reference.MODID)) {
			loadConfiguration();
		}
	}
}