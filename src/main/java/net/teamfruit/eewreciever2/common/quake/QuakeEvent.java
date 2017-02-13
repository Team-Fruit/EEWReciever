package net.teamfruit.eewreciever2.common.quake;

import cpw.mods.fml.common.eventhandler.Event;
import net.teamfruit.eewreciever2.common.quake.p2pquake.P2PQuakeQuakeInfoNode;
import net.teamfruit.eewreciever2.common.quake.p2pquake.P2PQuakeSensingInfoNode;
import net.teamfruit.eewreciever2.common.quake.p2pquake.P2PQuakeTsunamiInfoNode;
import net.teamfruit.eewreciever2.common.quake.twitter.TweetQuakeNode;

public abstract class QuakeEvent<E extends IQuakeNode> extends Event implements IQuakeEvent {

	private final E node;

	public QuakeEvent(final E node) {
		this.node = node;
	}

	@Override
	public E getNode() {
		return this.node;
	}

	/**
	 * 緊急地震速報
	 * @author bebe
	 *
	 */
	public static final class EEWEvent extends QuakeEvent<TweetQuakeNode> {
		public EEWEvent(final TweetQuakeNode node) {
			super(node);
		}
	}

	/**
	 * 地震情報(震度速報・震源情報等を含む)
	 * @author bebe
	 *
	 */
	public static final class QuakeInfoEvent extends QuakeEvent<P2PQuakeQuakeInfoNode> {
		public QuakeInfoEvent(final P2PQuakeQuakeInfoNode node) {
			super(node);
		}
	}

	/**
	 * 津波予報、予報解除
	 * @author bebe
	 *
	 */
	public static final class TsunamiWarnEvent extends QuakeEvent<P2PQuakeTsunamiInfoNode> {
		public TsunamiWarnEvent(final P2PQuakeTsunamiInfoNode node) {
			super(node);
		}
	}

	/**
	 * P2P地震情報 集計済み地震感知情報<br>ラグが発生するので使用非推奨
	 * @author bebe
	 * @deprecated
	 */
	@Deprecated
	public static final class QuakeSensingEvent extends QuakeEvent<P2PQuakeSensingInfoNode> {
		public QuakeSensingEvent(final P2PQuakeSensingInfoNode node) {
			super(node);
		}
	}
}
