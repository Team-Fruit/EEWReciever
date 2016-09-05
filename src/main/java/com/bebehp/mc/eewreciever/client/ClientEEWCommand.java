package com.bebehp.mc.eewreciever.client;

import com.bebehp.mc.eewreciever.common.EEWCommandBase;

import net.minecraft.command.ICommandSender;

public class ClientEEWCommand extends EEWCommandBase {

	@Override
	public int getRequiredPermissionLevel() {
		return 0;
	}

	@Override
	public boolean canCommandSenderUseCommand(final ICommandSender p_71519_1_) {
		return true;
	}

	@Override
	public boolean limitInDebugMode() {
		return false;
	}

}
