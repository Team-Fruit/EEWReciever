package net.teamfruit.eewreciever2.common;

import java.util.List;
import java.util.Queue;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ServerTickEvent;
import net.teamfruit.eewreciever2.Reference;

public class QuakeEventExecutor {

	private final Set<IQuake> quakes = Sets.newHashSet();

	public void register(final IQuake quake) {
		this.quakes.add(quake);
	}

	public void unregister(final IQuake quake) {
		this.quakes.remove(quake);
	}

	@SubscribeEvent
	public void onServerTick(final ServerTickEvent event) {
		try {
			for (final IQuake quake : this.quakes) {
				final Queue<IQuakeNode> nodes = quake.getQuakeUpdate();
				IQuakeNode line;
				while ((line = nodes.poll())!=null)
					FMLCommonHandler.instance().bus().post(line.getEvent());
			}
		} catch (final QuakeException e) {
			Reference.logger.error(e.getMessage(), e);
		}
	}

	@Deprecated
	public static List<IQuakeNode> getUpdate(final List<IQuakeNode> older, final List<IQuakeNode> newer) {
		final List<IQuakeNode> list = Lists.newLinkedList(newer);
		list.removeAll(older);
		return list;
	}
}
