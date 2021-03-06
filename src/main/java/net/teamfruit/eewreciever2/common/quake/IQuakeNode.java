package net.teamfruit.eewreciever2.common.quake;

public interface IQuakeNode {
	/**
	 * 識別子を返します。情報元によってどんなものが入るかは異なります
	 * @return 識別子
	 */
	String getId();

	/**
	 * ForgeのEventを返します
	 * @return Event
	 */
	QuakeEvent getEvent();

	/**
	 * 情報をオブジェクトにパースします
	 * @param source
	 * @return Node
	 * @throws QuakeException
	 */
	IQuakeNode parseString(String source) throws QuakeException;

	/**
	 * チャットにフォーマット可能かどうか
	 * @return フォーマット可能な場合はtrue<br>それ以外はfalse
	 */
	boolean canChat();

	/**
	 * チャット出力用の形式
	 * @return String
	 */
	String getChatFormat();
}
