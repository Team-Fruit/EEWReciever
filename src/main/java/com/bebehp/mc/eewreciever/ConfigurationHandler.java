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

	public static final boolean TWITTER_LEVEL_DEFAULT = true;
	public static boolean twitterLevel = TWITTER_LEVEL_DEFAULT;
	public static Property propTwitterLevel = null;

	public static final boolean P2PQUAKE_ENABLE_DEFAULT = true;
	public static boolean p2pQuakeEnable = TWITTER_ENABLE_DEFAULT;
	public static Property propP2PQuakeEnable = null;

	public static void init(File configFile) {
		if (configuration == null) {
			configuration = new Configuration(configFile, VERSION);
			loadConfiguration();
		}
	}

	public static void loadConfiguration() {
		propTwitterEnable = configuration.get("EEW", "isTwitterAPIEnabled",
				TWITTER_ENABLE_DEFAULT, "Enabling this, it'll be cheking with twitter API.");
		twitterEnable = propTwitterEnable.getBoolean();

		propTwitterLevel = configuration.get("EEW", "levelTwitter",
				TWITTER_LEVEL_DEFAULT, "Enabling this, it'll be strict mode.");
		twitterLevel = propTwitterLevel.getBoolean();

		propP2PQuakeEnable = configuration.get("EEW", "isP2PQuakeAPIEnabled",
				P2PQUAKE_ENABLE_DEFAULT, "Enabling this, it'll be cheking with p2pQuake API.");
		p2pQuakeEnable = propP2PQuakeEnable.getBoolean();

		if (configuration.hasChanged()) {
			configuration.save();
		}
	}

	private ConfigurationHandler() {
	}

	@SubscribeEvent
	public void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.modID.equalsIgnoreCase(EEWRecieverMod.owner)) {
			loadConfiguration();
		}
	}
}