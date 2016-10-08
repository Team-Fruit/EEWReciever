package com.bebehp.mc.eewreciever.loader;

import java.util.Map;

import com.bebehp.mc.eewreciever.Reference;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

@IFMLLoadingPlugin.Name(value = Reference.MODID)
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
		final boolean isDev = !(Boolean) data.get("runtimeDeobfuscationEnabled");
		new CarrotInstaller().install(isDev);
	}

	@Override
	public String getAccessTransformerClass() {
		return null;
	}

}
