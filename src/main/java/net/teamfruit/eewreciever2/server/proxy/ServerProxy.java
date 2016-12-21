package net.teamfruit.eewreciever2.server.proxy;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import net.teamfruit.eewreciever2.common.command.CommandAuth;
import net.teamfruit.eewreciever2.common.command.RootCommand;
import net.teamfruit.eewreciever2.common.proxy.CommonProxy;
import net.teamfruit.eewreciever2.server.AuthNotice;
import net.teamfruit.eewreciever2.server.QuakeHandler;

public class ServerProxy extends CommonProxy {

	@Override
	public void init(final FMLInitializationEvent event) {
		super.init(event);
		FMLCommonHandler.instance().bus().register(new QuakeHandler());
		FMLCommonHandler.instance().bus().register(new AuthNotice());
	}

	@Override
	public void serverLoad(final FMLServerStartingEvent event) {
		super.serverLoad(event);
		RootCommand.INSTANCE.addChildCommand(new CommandAuth());
	}
}
