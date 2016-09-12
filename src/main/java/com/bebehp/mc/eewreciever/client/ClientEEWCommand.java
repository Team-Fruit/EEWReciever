package com.bebehp.mc.eewreciever.client;

import com.bebehp.mc.eewreciever.common.EEWCommandBase;

public class ClientEEWCommand extends EEWCommandBase {

	@Override
	public int getRequiredPermissionLevel() {
		return 0;
	}

	@Override
	public boolean limitInDebugMode() {
		return false;
	}

}
