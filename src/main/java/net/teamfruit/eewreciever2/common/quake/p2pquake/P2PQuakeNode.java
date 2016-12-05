package net.teamfruit.eewreciever2.common.quake.p2pquake;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.teamfruit.eewreciever2.common.quake.IQuakeNode;

public abstract class P2PQuakeNode<T extends P2PQuakeJson> implements IQuakeNode {
	protected static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");

	protected T data;

	public Date date;
	public int code;

	public T getData() {
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
		return isValid();
	}

	@Override
	public boolean isValid() {
		return this.data!=null&&this.date!=null&&this.code!=0;
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
