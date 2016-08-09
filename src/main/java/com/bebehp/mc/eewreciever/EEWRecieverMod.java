package com.bebehp.mc.eewreciever;

import java.util.Map;

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

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class EEWRecieverMod {

	@EventHandler
	public void preInit(final FMLPreInitializationEvent event) {
		ConfigurationHandler.init(event.getSuggestedConfigurationFile());
	}

	@EventHandler
	public void init(final FMLInitializationEvent event) {
		Reference.logger.info("EEW is setting up.");
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
}