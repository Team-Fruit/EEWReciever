package com.bebehp.mc.eewreciever.ping;

/**
 * APIの通信にまつわるクラスです
 * @author b7n
 */
public interface IAPIPath {

	/**
	 * 地震情報を取得します。
	 * @return 地震情報
	 */
	public QuakeData getQuake() throws QuakeException;
}
