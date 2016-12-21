package net.teamfruit.eewreciever2.common.command;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.teamfruit.eewreciever2.common.Reference;
import net.teamfruit.eewreciever2.common.quake.twitter.TweetQuake;
import net.teamfruit.eewreciever2.common.quake.twitter.TweetQuakeHelper;
import net.teamfruit.eewreciever2.common.quake.twitter.TweetQuakeNode;
import net.teamfruit.eewreciever2.common.util.ChatBuilder;

public class CommandTest extends SubCommand {

	public CommandTest() {
		super("test");
	}

	@Override
	public void processSubCommand(final ICommandSender sender, final String[] args) {
		if (args.length>2) {
			try {
				final String arg = RootCommand.func_82360_a(sender, args, 1);
				String text = null;
				if (NumberUtils.isNumber(arg))
					text = TweetQuakeHelper.getAuthedTwitter().showStatus(NumberUtils.toLong(arg)).getText();
				else if (arg.startsWith("https://twitter.com/eewbot/status/"))
					TweetQuakeHelper.getAuthedTwitter().showStatus(NumberUtils.toLong(StringUtils.remove(arg, "https://twitter.com/eewbot/status/"))).getText();
				else
					text = args[2];
				TweetQuake.INSTANCE.getQuakeUpdate().add(new TweetQuakeNode().parseString(text));
			} catch (final Exception e) {
				ChatBuilder.create(e.getClass().getName()).setStyle(new ChatStyle().setColor(EnumChatFormatting.RED)).sendPlayer(sender);
				Reference.logger.error(e.getMessage(), e);
			}
		}
	}
}
