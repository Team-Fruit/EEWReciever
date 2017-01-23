package net.teamfruit.eewreciever2.server;

import com.mojang.authlib.GameProfile;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import net.minecraft.event.ClickEvent;
import net.minecraft.util.ChatStyle;
import net.teamfruit.eewreciever2.common.quake.twitter.TweetQuake;
import net.teamfruit.eewreciever2.common.util.ChatBuilder;

public class AuthNotice {

	@SubscribeEvent
	public void onPlayerLogin(final PlayerEvent.PlayerLoggedInEvent event) {
		if (TweetQuake.instance().isAuthRequired()&&isOp(event.player.getGameProfile())) {
			final ChatStyle style = new ChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/eewreciever auth start"));
			ChatBuilder.create("[EEWReciever2] Twitter認証が完了していません。クリックして開始する(Twitterアカウントが必要です").setStyle(style).sendPlayer(event.player);
		}
	}

	public static boolean isOp(final GameProfile profile) {
		return FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().func_152596_g(profile);
	}
}
