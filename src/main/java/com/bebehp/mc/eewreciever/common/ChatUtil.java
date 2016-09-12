package com.bebehp.mc.eewreciever.common;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.google.gson.JsonParseException;

import net.minecraft.command.ICommandSender;
import net.minecraft.command.SyntaxErrorException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.FMLCommonHandler;

public abstract class ChatUtil {
	public static void sendPlayerChat(final ICommandSender target, final ITextComponent... components) {
		for (final ITextComponent line : components) {
			target.addChatMessage(line);
		}
	}

	public static void sendServerChat(final ITextComponent... components) {
		final MinecraftServer mc = FMLCommonHandler.instance().getMinecraftServerInstance();

		for (final ITextComponent line : components) {
			mc.addChatMessage(line);
		}
	}

	public static ITextComponent byText(final String text) {
		return new TextComponentString(text);
	}

	public static ITextComponent byTranslation(final String text, final Object... args) {
		return new TextComponentTranslation(text, args);
	}

	public static ITextComponent byJson(final String json) throws SyntaxErrorException {
		try {
			return ITextComponent.Serializer.jsonToComponent(json);
		}
		catch (final JsonParseException e) {
			final Throwable throwable = ExceptionUtils.getRootCause(e);
			String s = "";

			if (throwable != null) {
				s = throwable.getMessage();

				if (s.contains("setLenient"))
					s = s.substring(s.indexOf("to accept ") + 10);
			}
			throw new SyntaxErrorException("commands.tellraw.jsonException", new Object[] {s});
		}
	}
}
