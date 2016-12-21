package net.teamfruit.eewreciever2.common.command;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import com.google.common.collect.Lists;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

public class RootCommand extends CommandBase implements IModCommand {
	public static final RootCommand INSTANCE = new RootCommand();
	public static final String ROOT_COMMAND_NAME = "eewreciever";

	private RootCommand() {
	}

	private final SortedSet<SubCommand> children = new TreeSet<SubCommand>(new Comparator<SubCommand>() {
		@Override
		public int compare(final SubCommand o1, final SubCommand o2) {
			return o1.compareTo(o2);
		}
	});

	public void addChildCommand(final SubCommand child) {
		child.setParent(this);
		this.children.add(child);
	}

	@Override
	public SortedSet<SubCommand> getChildren() {
		return this.children;
	}

	@Override
	public String getCommandName() {
		return ROOT_COMMAND_NAME;
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 0;
	}

	@Override
	public List<String> getCommandAliases() {
		final ArrayList<String> aliases = Lists.newArrayList();
		aliases.add("eew");
		return aliases;
	}

	@Override
	public String getCommandUsage(final ICommandSender sender) {
		return "/"+getCommandName()+" help";
	}

	@Override
	public void processCommand(final ICommandSender sender, final String[] args) {
		if (!CommandHelper.processCommands(sender, this, args))
			CommandHelper.throwWrongUsage(sender, this);
	}

	@Override
	public List<String> addTabCompletionOptions(final ICommandSender sender, final String[] args) {
		return CommandHelper.completeCommands(sender, this, args);
	}

	@Override
	public String getFullCommandString() {
		return getCommandName();
	}

	@Override
	public void printHelp(final ICommandSender sender) {
		CommandHelper.printHelp(sender, this);
	}
}