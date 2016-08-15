package com.bebehp.mc.eewreciever;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.google.gson.JsonParseException;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.SyntaxErrorException;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;

public abstract class ChatUtil {
	public static void sendPlayerChat(final ICommandSender target, final IChatComponent... components) {
		for (final IChatComponent line : components) {
			target.addChatMessage(line);
		}
	}

	public static void sendServerChat(final IChatComponent... components) {
		final ServerConfigurationManager sender = FMLCommonHandler.instance().getMinecraftServerInstance()
				.getConfigurationManager();

		for (final IChatComponent line : components) {
			sender.sendChatMsg(line);
		}
	}

	public static IChatComponent byText(final String text) {
		return new ChatComponentText(text);
	}

	public static IChatComponent byTranslation(final String text, final Object... args) {
		return new ChatComponentTranslation(text, args);
	}

	public static IChatComponent byJson(final String json) {
		try {
			return IChatComponent.Serializer.func_150699_a(json);
		}
		catch (final JsonParseException jsonparseexception)
		{
			final Throwable throwable = ExceptionUtils.getRootCause(jsonparseexception);
			throw new SyntaxErrorException("commands.tellraw.jsonException", new Object[] {throwable == null ? "" : throwable.getMessage()});
		}
	}
}
