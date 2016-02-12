package com.bebehp.mc.eewreciever.ping;

import java.util.List;

import com.bebehp.mc.eewreciever.EEWRecieverMod;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ServerTickEvent;

public class QuakeMain {

	final IAPIPath getter = new APIPathP2PQUAKE();

	long lasttime;
	@SubscribeEvent
	public void onServerTick(ServerTickEvent event) {
		try {
			printUpdate(getter.getQuakeUpdate());
		} catch (QuakeException e) {
			EEWRecieverMod.logger.error(e);
		}
	}

	public void printUpdate(List<QuakeNode> update)
	{
		if (!update.isEmpty())
		{
			for (QuakeNode up : update)
			{
				if ("QUA".equals(up.type))
				{
					EEWRecieverMod.sendServerChat(up.toString());
				}
			}
		}
	}
}
