package net.teamfruit.eewreciever2.common.quake;

import java.util.Queue;
import java.util.Set;

import com.google.common.collect.Sets;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ServerTickEvent;
import net.teamfruit.eewreciever2.EEWReciever2;
import net.teamfruit.eewreciever2.common.Reference;

public class QuakeEventExecutor {
	private static final QuakeEventExecutor INSTANCE = new QuakeEventExecutor();

	private final Set<IQuake> quakes = Sets.newHashSet();

	private QuakeEventExecutor() {
	}

	public static QuakeEventExecutor instance() {
		return INSTANCE;
	}

	public void register(final IQuake quake) {
		this.quakes.add(quake);
	}

	public void unregister(final IQuake quake) {
		this.quakes.remove(quake);
	}

	public static void init() {
		FMLCommonHandler.instance().bus().register(instance());
	}

	@SubscribeEvent
	public void onServerTick(final ServerTickEvent event) {
		try {
			for (final IQuake quake : this.quakes) {
				final Queue<IQuakeNode> nodes = quake.getQuakeUpdate();
				IQuakeNode line;
				while ((line = nodes.poll())!=null)
					EEWReciever2.EVENT_BUS.post(line.getEvent());
			}
		} catch (final QuakeException e) {
			Reference.logger.error(e.getMessage(), e);
		}
	}
}
