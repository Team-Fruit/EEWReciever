package com.bebehp.mc.eewreciever;

import java.io.File;
import java.util.Map;

import com.bebehp.mc.eewreciever.ping.QuakeMain;
import com.bebehp.mc.eewreciever.twitter.TweetQuakeCommands;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkCheckHandler;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.command.ICommandSender;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class EEWRecieverMod {

	public final TweetQuakeCommands tqm = new TweetQuakeCommands();
	public final EEWCommand command = new EEWCommand(this.tqm);
	public static File folderDir = null;

	@EventHandler
	public void preInit(final FMLPreInitializationEvent event) {
		Reference.logger = event.getModLog();
		EEWRecieverMod.folderDir = new File(event.getModConfigurationDirectory(), Reference.MODID);
		final File configFileDir = new File(folderDir, Reference.MODID + ".cfg");
		ConfigurationHandler.init(configFileDir);
	}

	@EventHandler
	public void init(final FMLInitializationEvent event) {
		Reference.logger.info("EEW is setting up.");
		FMLCommonHandler.instance().bus().register(new QuakeMain());
		FMLCommonHandler.instance().bus().register(ConfigurationHandler.INSTANCE);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@EventHandler
	public void serverLoad(final FMLServerStartingEvent event){
		event.registerServerCommand(this.command);
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

	public static void sendPlayerChat (final ICommandSender target, final String msg) {
		final String[] linemsg = msg.split("\n");
		for (final String line : linemsg) {
			ChatUtil.sendPlayerChat(target, ChatUtil.byText(line));
		}
	}

	public static void createFolders() {
		if (!folderDir.exists()) {
			if (!folderDir.mkdirs()) {
				Reference.logger.warn("Could not create EEWReciever directory [{}]!", folderDir.getAbsolutePath());
			}
		}
	}
}