package com.bebehp.mc.eewreciever.server;

import java.io.File;

import cpw.mods.fml.relauncher.FMLInjectionData;
import net.minecraft.launchwrapper.LaunchClassLoader;

public class DepLoader {

	private final LaunchClassLoader loader = (LaunchClassLoader)DepLoader.class.getClassLoader();
	private final File modsDir;

	public DepLoader() {
		final String mcVer = (String) FMLInjectionData.data()[4];
		final File mcDir = (File) FMLInjectionData.data()[6];

		this.modsDir = new File(mcDir, "mods/" + mcVer);
		if (this.modsDir.exists())
			this.modsDir.mkdir();
	}

}
