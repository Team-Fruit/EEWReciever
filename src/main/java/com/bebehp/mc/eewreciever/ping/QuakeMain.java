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
			List<AbstractQuakeNode> p2pQuakeNode = p2pQuake.getQuakeUpdate();
			if(ConfigurationHandler.p2pQuakeEnable) checkUpdate(p2pQuakeNode);

			List<AbstractQuakeNode> tweetQuakeNode = tweetQuake.getQuakeUpdate();
			if(ConfigurationHandler.twitterEnable) checkUpdate(tweetQuakeNode);
		} catch (QuakeException e) {
			EEWRecieverMod.logger.error(e);
		}
	}

	public void checkUpdate(List<AbstractQuakeNode> update)
	{
		for (AbstractQuakeNode up : update)
		{
			if (ConfigurationHandler.forceLevel || up.isAlarm())
			{
				EEWRecieverMod.sendServerChat(up.toString());
			}
		}
	}
}
