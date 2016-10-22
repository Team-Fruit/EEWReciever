package com.bebehp.mc.eewreciever.common;

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
	List<AbstractQuakeNode> getQuakeUpdate() throws QuakeException;

	void setStatus(boolean enabled);

}
