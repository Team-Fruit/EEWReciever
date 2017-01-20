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
}
