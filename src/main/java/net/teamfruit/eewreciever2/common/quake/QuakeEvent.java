package net.teamfruit.eewreciever2.common.quake;

import cpw.mods.fml.common.eventhandler.Event;
import net.teamfruit.eewreciever2.common.quake.p2pquake.P2PQuakeQuakeInfoNode;
import net.teamfruit.eewreciever2.common.quake.p2pquake.P2PQuakeSensingInfoNode;
import net.teamfruit.eewreciever2.common.quake.p2pquake.P2PQuakeTsunamiInfoNode;
import net.teamfruit.eewreciever2.common.quake.twitter.TweetQuakeNode;

public abstract class QuakeEvent<E> extends Event {

	private final E node;

	public QuakeEvent(final E node) {
		this.node = node;
	}

	public E getNode() {
		return this.node;
	}

	public static final class EEWEvent extends QuakeEvent<TweetQuakeNode> {
		public EEWEvent(final TweetQuakeNode node) {
			super(node);
		}
	}

	public static final class QuakeInfoEvent extends QuakeEvent<P2PQuakeQuakeInfoNode> {
		public QuakeInfoEvent(final P2PQuakeQuakeInfoNode node) {
			super(node);
		}
	}

	public static final class TsunamiWarnEvent extends QuakeEvent<P2PQuakeTsunamiInfoNode> {
		public TsunamiWarnEvent(final P2PQuakeTsunamiInfoNode node) {
			super(node);
		}
	}

	public static final class QuakeSensingEvent extends QuakeEvent<P2PQuakeSensingInfoNode> {
		public QuakeSensingEvent(final P2PQuakeSensingInfoNode node) {
			super(node);
		}
	}
}
