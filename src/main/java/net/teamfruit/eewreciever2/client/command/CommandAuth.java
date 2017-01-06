package net.teamfruit.eewreciever2.client.command;

import net.minecraft.client.Minecraft;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.teamfruit.eewreciever2.client.gui.GuiAuth;
import net.teamfruit.eewreciever2.common.command.SubCommand;
import net.teamfruit.eewreciever2.common.quake.twitter.TweetQuake;
import net.teamfruit.eewreciever2.common.util.ChatBuilder;

public class CommandAuth extends SubCommand {

	public CommandAuth() {
		super("auth");
	}

	@Override
	public void processSubCommand(final ICommandSender sender, final String[] args) {
		if (TweetQuake.INSTANCE.isAuthRequired)
			Minecraft.getMinecraft().displayGuiScreen(new GuiAuth());
		else
			ChatBuilder.create("認証の必要はありません！").setStyle(new ChatStyle().setColor(EnumChatFormatting.RED)).sendPlayer(sender);
	}
}
