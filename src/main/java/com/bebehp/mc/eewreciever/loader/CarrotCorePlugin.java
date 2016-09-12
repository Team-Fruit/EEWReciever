package com.bebehp.mc.eewreciever.loader;

import java.util.Map;

import com.bebehp.mc.eewreciever.Reference;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

@IFMLLoadingPlugin.MCVersion(value = Reference.MINECRAFT)
public class CarrotCorePlugin implements IFMLLoadingPlugin {

	@Override
	public String[] getASMTransformerClass() {
		return new String[0];
	}

	@Override
	public String getModContainerClass() {
		return null;
	}

	@Override
	public String getSetupClass() {
		return null;
	}

	@Override
	public void injectData(final Map<String, Object> data) {
		final Boolean isDev = !(Boolean) data.get("runtimeDeobfuscationEnabled");
		final CarrotInstaller carrotInstaller = new CarrotInstaller();
		if (isDev)
			carrotInstaller.devInstall();
		else
			carrotInstaller.install();
	}

	@Override
	public String getAccessTransformerClass() {
		return null;
	}

}
