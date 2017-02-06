package net.teamfruit.eewreciever2.client.command;

import net.minecraft.client.Minecraft;
import net.minecraft.command.ICommandSender;
import net.teamfruit.eewreciever2.client.gui.GuiYesNo;
import net.teamfruit.eewreciever2.client.gui.YesNoCallback;
import net.teamfruit.eewreciever2.common.command.SubCommand;

public class CommandAuth extends SubCommand {

	public CommandAuth() {
		super("auth");
	}

	@Override
	public void processSubCommand(final ICommandSender sender, final String[] args) {
		//		Minecraft.getMinecraft().displayGuiScreen(new GuiAuth());
		final YesNoCallback callback = new YesNoCallback() {

			@Override
			public void onYes() {

			}

			@Override
			public void onNo() {

			}

		};
		Minecraft.getMinecraft().displayGuiScreen(new GuiYesNo(null, callback));
	}
}
