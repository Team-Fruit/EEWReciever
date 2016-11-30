package net.teamfruit.eewreciever2.common;

import java.util.List;

import com.google.common.collect.Lists;

import cpw.mods.fml.common.gameevent.TickEvent.ServerTickEvent;
import net.minecraftforge.common.MinecraftForge;
import net.teamfruit.eewreciever2.Reference;

public class QuakeEventExecutor {

	private final List<IQuake> quakes = Lists.newLinkedList();

	public void register(final IQuake quake) {
		this.quakes.add(quake);
	}

	public void unregister(final IQuake quake) {
		this.quakes.remove(quake);
	}

	public void onServerTick(final ServerTickEvent event) {
		try {
			for (final IQuake quake : this.quakes) {
				final List<AbstractQuakeNode> nodes = quake.getQuakeUpdate();
				for (final AbstractQuakeNode node : nodes)
					MinecraftForge.EVENT_BUS.post(node.getEvent());
			}
		} catch (final QuakeException e) {
			Reference.logger.error(e.getMessage(), e);
		}
	}
}
