package com.bebehp.mc.eewreciever.proxy;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.bebehp.mc.eewreciever.ConfigurationHandler;
import com.bebehp.mc.eewreciever.EEWCommand;
import com.bebehp.mc.eewreciever.EEWRecieverMod;
import com.bebehp.mc.eewreciever.Reference;
import com.bebehp.mc.eewreciever.ping.QuakeMain;
import com.bebehp.mc.eewreciever.twitter.TweetQuakeFileManager;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

public abstract class CommonProxy {

	public void preInit(final FMLPreInitializationEvent event) {
		Reference.logger = event.getModLog();
		EEWRecieverMod.folderDir = new File(event.getModConfigurationDirectory(), Reference.MODID);
		final File configFileDir = new File(EEWRecieverMod.folderDir, Reference.MODID + ".cfg");
		// EEWReciever.cfgの移動に伴うファイル移動
		checkConfigFile(event.getSuggestedConfigurationFile(), configFileDir);
		ConfigurationHandler.init(configFileDir);

		Reference.logger.info("Loading the key...");
		EEWRecieverMod.tweetQuakeKey = TweetQuakeFileManager.loadKey();
		Reference.logger.info("Loading the file...");
		EEWRecieverMod.accessToken = TweetQuakeFileManager.loadAccessToken();
	}

	public void init(final FMLInitializationEvent event) {
		FMLCommonHandler.instance().bus().register(new QuakeMain());
		FMLCommonHandler.instance().bus().register(ConfigurationHandler.INSTANCE);
	}

	public void serverLoad(final FMLServerStartingEvent event){
		event.registerServerCommand(new EEWCommand());
	}

	public static void createFolders() {
		if (!EEWRecieverMod.folderDir.exists()) {
			if (!EEWRecieverMod.folderDir.mkdirs()) {
				Reference.logger.warn("Could not create EEWReciever directory [{}]!", EEWRecieverMod.folderDir.getAbsolutePath());
			}
		}
	}

	public static void checkConfigFile(final File oldConfigFile, final File configDir) {
		if (oldConfigFile.exists()) {
			Reference.logger.warn("Found old Config File[{}]", oldConfigFile);
			try {
				FileUtils.copyFile(oldConfigFile, configDir);
				if (!oldConfigFile.delete()) {
					Reference.logger.warn("Failed to delete the Legacy Config File[{}]", oldConfigFile);
				}
			} catch (final IOException e) {
				Reference.logger.error(e);
			}
		}
	}

}
