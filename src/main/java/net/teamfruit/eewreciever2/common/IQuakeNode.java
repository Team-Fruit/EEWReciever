package net.teamfruit.eewreciever2.common;

public interface IQuakeNode {
	/**
	 * 識別子を返します。情報元によってどんなものが入るかは異なります。
	 * @return 識別子
	 */
	String getId();

	QuakeEvent getEvent();

	IQuakeNode parseString(String source) throws QuakeException;
}
