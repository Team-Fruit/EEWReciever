package com.bebehp.mc.eewreciever;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ServerTickEvent;
import cpw.mods.fml.common.network.NetworkCheckHandler;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ServerChatEvent;

@Mod(modid="EEWReciever", name="EEWReciever", version="1.0")
public class EEWRecieverMod
{
	public static final String owner = "EEWReciever";
	public static final Logger logger = LogManager.getLogger();

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		logger.info("EEW is setting up.");
		FMLCommonHandler.instance().bus().register(this);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void onServerChat(ServerChatEvent event)
	{
		if (event.message.contains("fuck"))
		{
			EEWRecieverMod.sendServerChat("fuck you too");
		}
	}
	
	@SubscribeEvent
	public void onServerTick(ServerTickEvent event) {
		EEWRecieverMod.sendServerChat("orrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");
	}

	@NetworkCheckHandler
	public boolean netCheckHandler(Map<String, String> mods, Side side)
	{
		return true;
	}

	public static void sendServerChat(String msg)
	{
		FMLCommonHandler.instance().getMinecraftServerInstance()
			.getConfigurationManager().sendChatMsg(new ChatComponentText(msg));
	}
}