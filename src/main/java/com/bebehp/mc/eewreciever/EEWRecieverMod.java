package com.bebehp.mc.eewreciever;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.bebehp.mc.eewreciever.ping.QuakeMain;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkCheckHandler;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = EEWRecieverMod.owner, name = EEWRecieverMod.owner, version = "2.4.6")
public class EEWRecieverMod {
	public static final String owner = "EEWReciever";
	public static final Logger logger = LogManager.getLogger(owner);

	@EventHandler
	public void preInit(final FMLPreInitializationEvent event) {
		ConfigurationHandler.init(event.getSuggestedConfigurationFile());
	}

	@EventHandler
	public void init(final FMLInitializationEvent event) {
		logger.info("EEW is setting up.");
		FMLCommonHandler.instance().bus().register(new QuakeMain());
		FMLCommonHandler.instance().bus().register(ConfigurationHandler.INSTANCE);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@NetworkCheckHandler
	public boolean netCheckHandler(final Map<String, String> mods, final Side side) {
		return true;
	}

	public static void sendServerChat(final String msg) {
		final ServerConfigurationManager sender = FMLCommonHandler.instance().getMinecraftServerInstance()
				.getConfigurationManager();

		final String[] linemsg = msg.split("\n");
		for (final String line : linemsg) {
			sender.sendChatMsg(new ChatComponentText(line));
		}
	}

	//	@SubscribeEvent
	//	public void onServerChat(ServerChatEvent event) {
	//	if (event.message.contains("fuck"))
	//		{
	//	 	sendServerChat("kill you");
	//	 	}
	//	}
}