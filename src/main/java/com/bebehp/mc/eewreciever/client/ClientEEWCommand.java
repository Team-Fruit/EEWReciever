package com.bebehp.mc.eewreciever.client;

import com.bebehp.mc.eewreciever.common.EEWCommandBase;

import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

public class ClientEEWCommand extends EEWCommandBase {

	@Override
	public int getRequiredPermissionLevel() {
		return 0;
	}

	@Override
	public boolean checkPermission(final MinecraftServer server, final ICommandSender sender) {
		return true;
	}

	@Override
	public boolean limitInDebugMode() {
		return false;
	}

}
