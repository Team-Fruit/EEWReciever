package com.bebehp.mc.eewreciever.server;

import com.bebehp.mc.eewreciever.common.EEWCommandBase;

import net.minecraft.command.ICommandSender;

public class ServerEEWCommand extends EEWCommandBase {

	@Override
	public int getRequiredPermissionLevel() {
		return 3;
	}

	@Override
	public boolean canCommandSenderUseCommand(final ICommandSender p_71519_1_) {
		return false;
	}

	@Override
	public boolean limitInDebugMode() {
		return true;
	}

}
