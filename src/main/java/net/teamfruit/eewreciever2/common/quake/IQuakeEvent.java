package net.teamfruit.eewreciever2.common.quake;

public interface IQuakeEvent {

	/**
	 * 各種情報がまとめられたクラスを返します。<br>
	 * それらは、直接チャットに送信する事にも利用出来ます。
	 * @return Node
	 */
	public IQuakeNode getNode();
}
