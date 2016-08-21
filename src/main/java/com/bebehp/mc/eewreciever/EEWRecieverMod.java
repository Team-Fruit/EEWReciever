package com.bebehp.mc.eewreciever;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.bebehp.mc.eewreciever.ping.QuakeMain;
import com.bebehp.mc.eewreciever.twitter.TweetQuakeAuthChecker;
import com.bebehp.mc.eewreciever.twitter.TweetQuakeFileManager;
import com.bebehp.mc.eewreciever.twitter.TweetQuakeKey;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkCheckHandler;
import cpw.mods.fml.relauncher.Side;
import net.minecraftforge.common.MinecraftForge;
import twitter4j.auth.AccessToken;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class EEWRecieverMod {

	public static File folderDir = null;
	public static TweetQuakeKey tweetQuakeKey;
	public static AccessToken accessToken;

	@EventHandler
	public void preInit(final FMLPreInitializationEvent event) {
		Reference.logger = event.getModLog();
		EEWRecieverMod.folderDir = new File(event.getModConfigurationDirectory(), Reference.MODID);
		final File configFileDir = new File(folderDir, Reference.MODID + ".cfg");
		checkConfigFile(event.getSuggestedConfigurationFile(), configFileDir);
		ConfigurationHandler.init(configFileDir);
		EEWRecieverMod.tweetQuakeKey = TweetQuakeFileManager.loadKey();
		EEWRecieverMod.accessToken = TweetQuakeFileManager.loadAccessToken();
	}

	@EventHandler
	public void init(final FMLInitializationEvent event) {
		Reference.logger.info("EEW is setting up.");
		FMLCommonHandler.instance().bus().register(new QuakeMain());
		FMLCommonHandler.instance().bus().register(ConfigurationHandler.INSTANCE);
		FMLCommonHandler.instance().bus().register(new TweetQuakeAuthChecker());
		MinecraftForge.EVENT_BUS.register(this);
	}

	@EventHandler
	public void serverLoad(final FMLServerStartingEvent event){
		event.registerServerCommand(new EEWCommand());
	}

	@NetworkCheckHandler
	public boolean netCheckHandler(final Map<String, String> mods, final Side side) {
		return true;
	}

	public static void sendServerChat(final String msg) {
		final String[] linemsg = msg.split("\n");
		for (final String line : linemsg) {
			ChatUtil.sendServerChat(ChatUtil.byText(line));
		}
	}

	public static void createFolders() {
		if (!folderDir.exists()) {
			if (!folderDir.mkdirs()) {
				Reference.logger.warn("Could not create EEWReciever directory [{}]!", folderDir.getAbsolutePath());
			}
		}
	}

	public static void checkConfigFile(final File suggestedConfigFile, final File configDir) {
		if (suggestedConfigFile.exists()) {
			Reference.logger.warn("Found old Config File[{}]", suggestedConfigFile);
			try {
				FileUtils.copyFile(suggestedConfigFile, configDir);
				if (!suggestedConfigFile.delete()) {
					Reference.logger.warn("Failed to delete the Legacy Config File[{}]", suggestedConfigFile);
				}
			} catch (final IOException e) {
				Reference.logger.error(e);
			}
		}
	}
}