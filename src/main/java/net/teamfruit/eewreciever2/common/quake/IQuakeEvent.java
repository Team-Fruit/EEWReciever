package net.teamfruit.eewreciever2.common.quake;

import net.teamfruit.eewreciever2.EEWReciever2;

/**
 * EEWReciever2の各種情報受信時には、このEventがpostされます。<br>
 * {@link EEWReciever2#EVENT_BUS}
 *
 * @author TeamFruit
 */
public interface IQuakeEvent {

	/**
	 * 各種情報がまとめられたクラスを返します。<br>
	 * それらは、直接チャットに送信する事にも利用出来ます。
	 * @return Node
	 */
	public IQuakeNode getNode();
}
