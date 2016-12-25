package net.teamfruit.eewreciever2.client.command;

import net.minecraft.client.Minecraft;
import net.minecraft.command.ICommandSender;
import net.teamfruit.eewreciever2.client.gui.GuiAuth;
import net.teamfruit.eewreciever2.common.command.SubCommand;
import net.teamfruit.eewreciever2.common.quake.twitter.TweetQuakeHelper;

public class CommandAuth extends SubCommand {

	public CommandAuth() {
		super("auth");
	}

	@Override
	public void processSubCommand(final ICommandSender sender, final String[] args) {
		Minecraft.getMinecraft().displayGuiScreen(new GuiAuth(TweetQuakeHelper.getAuther()));
	}
}
