package net.teamfruit.eewreciever2.client.proxy;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.common.MinecraftForge;
import net.teamfruit.eewreciever2.client.QuakeHandler;
import net.teamfruit.eewreciever2.client.command.CommandAuth;
import net.teamfruit.eewreciever2.client.command.CommandDebug;
import net.teamfruit.eewreciever2.client.gui.OverlayFrame;
import net.teamfruit.eewreciever2.common.command.RootCommand;
import net.teamfruit.eewreciever2.common.proxy.CommonProxy;

public class ClientProxy extends CommonProxy {

	@Override
	public void init(final FMLInitializationEvent event) {
		super.init(event);
		FMLCommonHandler.instance().bus().register(new QuakeHandler());
		MinecraftForge.EVENT_BUS.register(OverlayFrame.instance);
		FMLCommonHandler.instance().bus().register(OverlayFrame.instance);
	}

	@Override
	public void serverLoad(final FMLServerStartingEvent event) {
		super.serverLoad(event);
		RootCommand.INSTANCE.addChildCommand(new CommandAuth());
		final Boolean bool = (Boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");
		if (bool)
			RootCommand.INSTANCE.addChildCommand(new CommandDebug());
	}
}
