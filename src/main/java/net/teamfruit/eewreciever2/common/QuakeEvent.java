package net.teamfruit.eewreciever2.common;

import cpw.mods.fml.common.eventhandler.Event;
import net.teamfruit.eewreciever2.common.p2pquake.P2PQuakeQuakeInfoNode;
import net.teamfruit.eewreciever2.common.p2pquake.P2PQuakeSensingInfoNode;
import net.teamfruit.eewreciever2.common.p2pquake.P2PQuakeTsunamiInfoNode;
import net.teamfruit.eewreciever2.common.twitter.TweetQuakeNode;

public abstract class QuakeEvent extends Event {

	public static enum Type {
		EEW, INFO, TSUNAMI, SENSING;
	}

	private final Type type;

	public QuakeEvent(final Type type) {
		this.type = type;
	}

	public Type getType() {
		return this.type;
	}

	abstract public IQuakeNode getNode();

	public static class EEWEvent extends QuakeEvent {
		private final TweetQuakeNode node;

		public EEWEvent(final TweetQuakeNode node) {
			super(Type.EEW);
			this.node = node;
		}

		@Override
		public TweetQuakeNode getNode() {
			return this.node;
		}
	}

	public static class QuakeInfoEvent extends QuakeEvent {
		private final P2PQuakeQuakeInfoNode node;

		public QuakeInfoEvent(final P2PQuakeQuakeInfoNode node) {
			super(Type.INFO);
			this.node = node;
		}

		@Override
		public P2PQuakeQuakeInfoNode getNode() {
			return this.node;
		}
	}

	public static class TsunamiWarnEvent extends QuakeEvent {
		private final P2PQuakeTsunamiInfoNode node;

		public TsunamiWarnEvent(final P2PQuakeTsunamiInfoNode node) {
			super(Type.TSUNAMI);
			this.node = node;
		}

		@Override
		public P2PQuakeTsunamiInfoNode getNode() {
			return this.node;
		}
	}

	public static class QuakeSensingEvent extends QuakeEvent {
		private final P2PQuakeSensingInfoNode node;

		public QuakeSensingEvent(final P2PQuakeSensingInfoNode node) {
			super(Type.SENSING);
			this.node = node;
		}

		@Override
		public P2PQuakeSensingInfoNode getNode() {
			return this.node;
		}
	}
}
