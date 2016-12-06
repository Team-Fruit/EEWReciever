package net.teamfruit.eewreciever2.client.gui;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.client.config.IConfigElement;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.ConfigElement;
import net.teamfruit.eewreciever2.common.ConfigHandler;
import net.teamfruit.eewreciever2.common.Reference;

public class GuiModConfig extends GuiConfig {

	public GuiModConfig(final GuiScreen parent) {
		super(parent, getConfigElements(), Reference.MODID, false, false, GuiConfig.getAbridgedConfigPath(ConfigHandler.instance.getFilePath()));
	}

	@SuppressWarnings("rawtypes")
	private static List<IConfigElement> getConfigElements() {
		final List<IConfigElement> list = new ArrayList<IConfigElement>();

		for (final String cat : ConfigHandler.instance.getCategoryNames()) {
			final ConfigCategory cc = ConfigHandler.instance.getCategory(cat);

			if (cc.isChild())
				continue;

			final ConfigElement ce = new ConfigElement<String>(cc);
			list.add(ce);
		}

		return list;
	}
}
