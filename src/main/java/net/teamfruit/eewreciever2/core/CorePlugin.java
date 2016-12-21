package net.teamfruit.eewreciever2.core;

import java.io.File;
import java.util.Map;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

@IFMLLoadingPlugin.Name(value = "EEWReciever2")
public class CorePlugin implements IFMLLoadingPlugin {

	@Override
	public String[] getASMTransformerClass() {
		return null;
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
		new Loader((File) data.get("coremodLocation"), (File) data.get("mcLocation"), (Boolean) data.get("runtimeDeobfuscationEnabled")).load();
	}

	@Override
	public String getAccessTransformerClass() {
		return null;
	}
}
