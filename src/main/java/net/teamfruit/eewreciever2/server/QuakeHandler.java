package net.teamfruit.eewreciever2.server;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.teamfruit.eewreciever2.common.quake.IQuakeNode;
import net.teamfruit.eewreciever2.common.quake.QuakeEvent.EEWEvent;
import net.teamfruit.eewreciever2.common.quake.QuakeEvent.QuakeInfoEvent;
import net.teamfruit.eewreciever2.common.quake.QuakeEvent.TsunamiWarnEvent;
import net.teamfruit.eewreciever2.common.util.ChatBuilder;

public class QuakeHandler {

	@SubscribeEvent
	public void onEEW(final EEWEvent event) {
		sendChat(event.getNode());
	}

	@SubscribeEvent
	public void onQuakeInfo(final QuakeInfoEvent event) {
		sendChat(event.getNode());
	}

	@SubscribeEvent
	public void onTsunamiWarn(final TsunamiWarnEvent event) {
		sendChat(event.getNode());
	}

	private void sendChat(final IQuakeNode node) {
		if (node.canChat())
			ChatBuilder.sendServer(ChatBuilder.create(node.getChatFormat()));
	}
}
