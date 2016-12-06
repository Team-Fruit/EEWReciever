package net.teamfruit.eewreciever2.client;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.teamfruit.eewreciever2.common.quake.QuakeEvent.EEWEvent;
import net.teamfruit.eewreciever2.common.quake.QuakeEvent.QuakeInfoEvent;
import net.teamfruit.eewreciever2.common.quake.QuakeEvent.TsunamiWarnEvent;

public class QuakeHandler {

	@SubscribeEvent
	public void onEEW(final EEWEvent event) {

	}

	@SubscribeEvent
	public void onQuakeInfo(final QuakeInfoEvent event) {

	}

	@SubscribeEvent
	public void onTsunamiWarn(final TsunamiWarnEvent event) {

	}

	@SubscribeEvent
	@SuppressWarnings("deprecation")
	public void onQuakeSensing(final net.teamfruit.eewreciever2.common.quake.QuakeEvent.QuakeSensingEvent event) {

	}
}
