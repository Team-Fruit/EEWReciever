package com.bebehp.mc.eewreciever.ping;

import java.util.List;

import com.bebehp.mc.eewreciever.ConfigurationHandler;
import com.bebehp.mc.eewreciever.EEWRecieverMod;
import com.bebehp.mc.eewreciever.p2pquake.P2PQuake;
import com.bebehp.mc.eewreciever.twitter.TweetQuake;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ServerTickEvent;

public class QuakeMain {

	final IQuake p2pQuake = new P2PQuake();
	final IQuake tweetQuake = new TweetQuake();

	long lasttime;
	@SubscribeEvent
	public void onServerTick(ServerTickEvent event) {
		try {
			if(ConfigurationHandler.p2pQuakeEnable) printUpdate(p2pQuake.getQuakeUpdate());
			if(ConfigurationHandler.twitterEnable) printUpdate(tweetQuake.getQuakeUpdate());
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
