package com.bebehp.mc.eewreciever.server;

import com.bebehp.mc.eewreciever.common.EEWCommandBase;

public class ServerEEWCommand extends EEWCommandBase {

	@Override
	public int getRequiredPermissionLevel() {
		return 3;
	}

	@Override
	public boolean limitInDebugMode() {
		return true;
	}

}
