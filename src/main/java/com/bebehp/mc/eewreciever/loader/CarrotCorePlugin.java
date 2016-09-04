package com.bebehp.mc.eewreciever.loader;

import java.util.Map;

import com.bebehp.mc.eewreciever.Reference;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

@IFMLLoadingPlugin.MCVersion(value = "1.7.10")
@IFMLLoadingPlugin.Name(value = "CarrotLoader")
public class CarrotCorePlugin implements IFMLLoadingPlugin {

	//	private final LaunchClassLoader loader = (LaunchClassLoader) CarrotCorePlugin.class.getClassLoader();

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
		final Boolean isDev = (Boolean) data.get("runtimeDeobfuscationEnabled");
		final CarrotInstaller carrotInstaller = new CarrotInstaller();
		if (!isDev)
			carrotInstaller.install();
	}

	@Override
	public String getAccessTransformerClass() {
		return null;
	}

}
