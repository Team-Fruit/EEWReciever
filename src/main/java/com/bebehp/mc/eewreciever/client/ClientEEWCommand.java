package com.bebehp.mc.eewreciever.client;

import com.bebehp.mc.eewreciever.server.ServerEEWCommand;

import net.minecraft.command.ICommandSender;

public class ClientEEWCommand extends ServerEEWCommand {

	public ClientEEWCommand() { //NO-OP
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
