package net.teamfruit.eewreciever2.common.quake.p2pquake;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.teamfruit.eewreciever2.common.quake.IQuakeNode;

/**
 * APIレスポンスのパース
 * @see <a href="http://www.p2pquake.com/dev/?q=json-api">API仕様詳細</a>
 * @author bebe
 *
 * @param <E>
 */
public abstract class P2PQuakeNode<E extends P2PQuakeJson> implements IQuakeNode {
	protected static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");

	protected E data;

	/**
	 * 情報の配信日時で，yyyy/MM/dd HH:mm:ss.SSS形式。地震感知情報については，集計の始点となる日時。
	 */
	public Date date;
	/**
	 * 情報の種類。 551: 地震情報，552: 津波予報，5610: 集計済み地震感知情報。
	 */
	public int code;

	public E getData() {
		return this.data;
	}

	@Override
	public String getId() {
		return String.valueOf(this.code);
	}

	@Override
	public String toString() {
		return getChatFormat();
	}

	@Override
	public boolean canChat() {
		return this.data!=null&&this.code!=0;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime*result+this.code;
		result = prime*result+((this.date==null) ? 0 : this.date.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this==obj)
			return true;
		if (obj==null)
			return false;
		if (!(obj instanceof P2PQuakeNode))
			return false;
		final P2PQuakeNode other = (P2PQuakeNode) obj;
		if (this.code!=other.code)
			return false;
		if (this.date==null) {
			if (other.date!=null)
				return false;
		} else if (!this.date.equals(other.date))
			return false;
		return true;
	}
}
