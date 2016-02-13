package com.bebehp.mc.eewreciever.ping;

import java.util.List;

import com.bebehp.mc.eewreciever.EEWRecieverMod;
import com.bebehp.mc.eewreciever.p2pquake.P2PQuake;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ServerTickEvent;

public class QuakeMain {

	final IQuake getter = new P2PQuake();

	long lasttime;
	@SubscribeEvent
	public void onServerTick(ServerTickEvent event) {
		try {
			printUpdate(getter.getQuakeUpdate());
		} catch (QuakeException e) {
			EEWRecieverMod.logger.error(e);
		}
	}

	public void printUpdate(List<AbstractQuakeNode> update)
	{
		if (!update.isEmpty())
		{
			for (AbstractQuakeNode up : update)
			{
//				if ("QUA".equals(up.type))
//				{
					EEWRecieverMod.sendServerChat(up.toString());
//				}
			}
		}
	}
}
