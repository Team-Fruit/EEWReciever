package com.bebehp.mc.eewreciever.loader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.bebehp.mc.eewreciever.Reference;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import cpw.mods.fml.relauncher.FMLInjectionData;
import net.minecraft.launchwrapper.LaunchClassLoader;

public class CarrotInstaller {

	private final File modsDir;
	private final File v_modsDir;

	public CarrotInstaller() {
		final String mcVer = (String) FMLInjectionData.data()[4];
		final File mcDir = (File) FMLInjectionData.data()[6];

		this.modsDir = new File(mcDir, "mods");
		this.v_modsDir = new File(this.modsDir, mcVer);
		if (!this.v_modsDir.exists())
			this.v_modsDir.mkdirs();
	}

	class CarrotDep {
		public String remote;
		public String local;
	}

	public void install() {
		final List<CarrotDep> carrotDev = load("carrotdep.info");
		if (carrotDev != null)
			install(carrotDev);
	}

	public void devInstall() {
		final List<CarrotDep> carrotDev = devLoad("carrotdep.info");
		if (carrotDev != null)
			install(carrotDev);
	}

	private void install(final List<CarrotDep> carrotDev) {
		for (final CarrotDep line : carrotDev) {
			Reference.logger.info("Loading file {}", line.local);
			final File local = new File(this.v_modsDir, line.local);
			if (!local.exists())
				download(line);
			addClasspath(line.local);
		}
	}

	private List<CarrotDep> load(final String fileName) {
		final InputStream is = this.getClass().getClassLoader().getResourceAsStream(fileName);
		if (is != null)
			return read(is);
		else
			return null;
	}

	private List<CarrotDep> devLoad(final String fileName) {
		try {
			final String absolutePath = System.getProperty("java.class.path");
			final String[] filePath = absolutePath.split(System.getProperty("path.separator"));
			final File runFile = new File(filePath[0]);
			final File carrotDevFile = new File(runFile, fileName);
			return read(new FileInputStream(carrotDevFile));
		} catch (final FileNotFoundException e) {
			return null;
		}
	}

	private List<CarrotDep> read(final InputStream is) {
		final InputStreamReader isr = new InputStreamReader(is);
		final JsonReader jsr = new JsonReader(isr);
		final Type collectionType = new TypeToken<Collection<CarrotDep>>(){}.getType();
		final List<CarrotDep> carrotDev = new Gson().fromJson(jsr, collectionType);
		return carrotDev;
	}

	private void download(final CarrotDep dev) {
		File downloadingFile = null;
		try {
			downloadingFile = new File(this.v_modsDir, dev.local);
			final URL remote = new URL(dev.remote);

			final HttpURLConnection connection = (HttpURLConnection) remote.openConnection();
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(5000);
			connection.setRequestProperty("User-Agent", Reference.MODID + " Downloader");

			if (connection.getResponseCode() != HttpURLConnection.HTTP_OK)
				throw new RuntimeException();

			final InputStream is = connection.getInputStream();
			FileUtils.copyInputStreamToFile(is, downloadingFile);
			Reference.logger.info("Download complete {}", downloadingFile);
		} catch (final Exception e) {
			downloadingFile.delete();
			throw new RuntimeException("A download error occured", e);
		}
	}

	private void addClasspath(final String name) {
		try {
			((LaunchClassLoader) CarrotCorePlugin.class.getClassLoader()).addURL(new File(this.v_modsDir, name).toURI().toURL());
		} catch (final MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}
}
