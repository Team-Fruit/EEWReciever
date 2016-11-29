package net.teamfruit.eewreciever2.common;

import cpw.mods.fml.common.eventhandler.Event;

public abstract class QuakeEvent extends Event {

	public static enum Type {
		EEW, INFO, TSUNAMI;
	}

	private final Type type;
	private final AbstractQuakeNode node;

	public QuakeEvent(final Type type, final AbstractQuakeNode node) {
		this.type = type;
		this.node = node;
	}

	public Type getType() {
		return this.type;
	}

	public AbstractQuakeNode getNode() {
		return this.node;
	}

	public static class EEWEvent extends QuakeEvent {
		public EEWEvent(final AbstractQuakeNode node) {
			super(Type.EEW, node);
		}
	}

	public static class QuakeInfoEvent extends QuakeEvent {
		public QuakeInfoEvent(final AbstractQuakeNode node) {
			super(Type.INFO, node);
		}
	}

	public static class TsunamiWarnEvent extends QuakeEvent {
		public TsunamiWarnEvent(final AbstractQuakeNode node) {
			super(Type.TSUNAMI, node);
		}
	}
}
