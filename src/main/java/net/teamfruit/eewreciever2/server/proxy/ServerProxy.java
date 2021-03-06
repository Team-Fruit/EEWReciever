package net.teamfruit.eewreciever2.server.proxy;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import net.teamfruit.eewreciever2.EEWReciever2;
import net.teamfruit.eewreciever2.common.command.RootCommand;
import net.teamfruit.eewreciever2.common.proxy.CommonProxy;
import net.teamfruit.eewreciever2.server.AuthNoticeHandler;
import net.teamfruit.eewreciever2.server.QuakeHandler;
import net.teamfruit.eewreciever2.server.command.CommandAuth;

public class ServerProxy extends CommonProxy {

	@Override
	public void init(final FMLInitializationEvent event) {
		super.init(event);
		EEWReciever2.EVENT_BUS.register(new QuakeHandler());
		FMLCommonHandler.instance().bus().register(new AuthNoticeHandler());
	}

	@Override
	public void serverLoad(final FMLServerStartingEvent event) {
		super.serverLoad(event);
		RootCommand.INSTANCE.addChildCommand(new CommandAuth());
	}
}
