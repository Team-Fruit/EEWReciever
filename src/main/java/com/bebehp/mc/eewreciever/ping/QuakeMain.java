package com.bebehp.mc.eewreciever.ping;

import java.util.Date;
import java.util.List;

import com.bebehp.mc.eewreciever.EEWRecieverMod;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ServerTickEvent;

public class QuakeMain {

	public static long WaitMilliSeconds = 1000 * 15;
	final IAPIPath getter = new APIPathP2PQUAKE();

	long lasttime;
	@SubscribeEvent
	public void onServerTick(ServerTickEvent event) {
		long now = new Date().getTime();
		if (now - lasttime > WaitMilliSeconds)
		{
			lasttime = now;
			quakeUpdate();
		}
	}

	List<QuakeNode> before;
	public void quakeUpdate()
	{
		try {
			List<QuakeNode> now = getter.getQuake();
			if (before != null) {
				List<QuakeNode> update = QuakeNode.getUpdate(before, now);
				printUpdate(update);
			}
			before = now;
		} catch (QuakeException e) {
			EEWRecieverMod.logger.error("ping connection failed [" + e.getMessage() + "]");
			return;
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
