package com.bebehp.mc.eewreciever.server;

import com.bebehp.mc.eewreciever.common.EEWCommandBase;
import com.bebehp.mc.eewreciever.common.twitter.TweetQuakeSetup;

public class ServerEEWCommand extends EEWCommandBase {

	public ServerEEWCommand(final TweetQuakeSetup tweetQuakeSetup) {
		super(tweetQuakeSetup);
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 3;
	}

	@Override
	public boolean limitInDebugMode() {
		return true;
	}

}
