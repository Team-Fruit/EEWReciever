package com.bebehp.mc.eewreciever.loader;

import java.util.Map;

import com.bebehp.mc.eewreciever.Reference;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import net.minecraft.launchwrapper.LaunchClassLoader;

@IFMLLoadingPlugin.MCVersion(value = "1.7.10")
@IFMLLoadingPlugin.Name(value = "CarrotLoader")
public class CarrotLoader implements IFMLLoadingPlugin {

	private final LaunchClassLoader loader = (LaunchClassLoader) CarrotLoader.class.getClassLoader();

	@Override
	public String[] getASMTransformerClass() {
		return new String[0];
	}

	@Override
	public String getModContainerClass() {
		return Reference.MOD_CONTAINER;
	}

	@Override
	public String getSetupClass() {
		return null;
	}

	@Override
	public void injectData(final Map<String, Object> data) {

	}

	@Override
	public String getAccessTransformerClass() {
		return null;
	}

}
