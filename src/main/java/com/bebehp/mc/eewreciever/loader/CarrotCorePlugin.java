package com.bebehp.mc.eewreciever.loader;

import java.util.Map;

import com.bebehp.mc.eewreciever.Reference;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

@IFMLLoadingPlugin.Name(value = "EEWReciever")
@IFMLLoadingPlugin.MCVersion(value = "1.7.10")
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
		Reference.logger.info("Core Load... EEWReciever");
		final boolean isDev = !(Boolean) data.get("runtimeDeobfuscationEnabled");
		new CarrotInstaller().install(isDev);
	}

	@Override
	public String getAccessTransformerClass() {
		return null;
	}

}
