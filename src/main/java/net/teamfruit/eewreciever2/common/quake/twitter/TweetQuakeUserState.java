package net.teamfruit.eewreciever2.common.quake.twitter;

public class TweetQuakeUserState {

	private final long userID;
	private boolean block;
	private boolean mute;
	private boolean follow;

	public TweetQuakeUserState(final long userID) {
		this.userID = userID;
	}

	public TweetQuakeUserState(final long userID, final boolean block, final boolean mute, final boolean follow) {
		this(userID);
		this.block = block;
		this.mute = mute;
		this.follow = follow;
	}

	public boolean isBlock() {
		return this.block;
	}

	public TweetQuakeUserState setBlock(final boolean block) {
		this.block = block;
		return this;
	}

	public boolean isMute() {
		return this.mute;
	}

	public TweetQuakeUserState setMute(final boolean mute) {
		this.mute = mute;
		return this;
	}

	public boolean isFollow() {
		return this.follow;
	}

	public TweetQuakeUserState setFollow(final boolean follow) {
		this.follow = follow;
		return this;
	}

	public long getUserID() {
		return this.userID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime*result+(this.block ? 1231 : 1237);
		result = prime*result+(this.follow ? 1231 : 1237);
		result = prime*result+(this.mute ? 1231 : 1237);
		result = prime*result+(int) (this.userID^(this.userID>>>32));
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this==obj)
			return true;
		if (obj==null)
			return false;
		if (!(obj instanceof TweetQuakeUserState))
			return false;
		final TweetQuakeUserState other = (TweetQuakeUserState) obj;
		if (this.block!=other.block)
			return false;
		if (this.follow!=other.follow)
			return false;
		if (this.mute!=other.mute)
			return false;
		if (this.userID!=other.userID)
			return false;
		return true;
	}
}