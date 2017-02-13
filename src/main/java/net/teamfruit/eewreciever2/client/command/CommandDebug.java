package net.teamfruit.eewreciever2.client.command;

import net.minecraft.client.Minecraft;
import net.minecraft.command.ICommandSender;
import net.teamfruit.eewreciever2.client.gui.GuiYesNo;
import net.teamfruit.eewreciever2.client.gui.YesNoCallback;
import net.teamfruit.eewreciever2.common.command.SubCommand;

public class CommandDebug extends SubCommand {

	public CommandDebug() {
		super("debug");
	}

	@Override
	public void processSubCommand(final ICommandSender sender, final String[] args) {
		final YesNoCallback callback = new YesNoCallback() {

			@Override
			public boolean onYes() {
				return true;
			}

			@Override
			public boolean onNo() {
				return true;
			}

		};
		Minecraft.getMinecraft().displayGuiScreen(new GuiYesNo(null, callback).setDescText("Warframeっぽい二択Gui"));
	}

}
