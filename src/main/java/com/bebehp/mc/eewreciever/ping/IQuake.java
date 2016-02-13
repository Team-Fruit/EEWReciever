package com.bebehp.mc.eewreciever.ping;

import java.util.List;

/**
 * APIの通信にまつわるクラスです
 * @author b7n
 */
public interface IQuake {

	/**
	 * 地震情報の差分を取得します。
	 * @return 地震情報
	 */
	public List<AbstractQuakeNode> getQuakeUpdate() throws QuakeException;

}
