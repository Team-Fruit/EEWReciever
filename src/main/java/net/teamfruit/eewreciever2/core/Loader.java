package net.teamfruit.eewreciever2.core;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import cpw.mods.fml.relauncher.FMLInjectionData;
import net.minecraft.launchwrapper.LaunchClassLoader;
import net.teamfruit.eewreciever2.Reference;

public class Loader {
	public static final String DEPINFO_FILENAME = "carrotdep.info";
	private static final Gson gson = new Gson();
	private static final LaunchClassLoader classLoader = (LaunchClassLoader) Loader.class.getClassLoader();

	public final File coremodLocation;
	public final File mcLocation;
	public final boolean runtimeDeobfuscationEnabled;
	public final File libDir;

	public Loader(final File codemodLocation, final File mcLocation, final boolean runtimeDeobfuscationEnabled) {
		this.coremodLocation = codemodLocation;
		this.mcLocation = mcLocation;
		this.runtimeDeobfuscationEnabled = runtimeDeobfuscationEnabled;
		this.libDir = new File(this.mcLocation, "mods/"+(String) FMLInjectionData.data()[4]);
	}

	public void load() {
		if (this.runtimeDeobfuscationEnabled) {
			JarFile jf = null;
			try {
				jf = new JarFile(this.coremodLocation);
				final ZipEntry ze = jf.getEntry(DEPINFO_FILENAME);
				if (ze==null)
					Reference.logger.warn("Please don't delete the file in the Jar file or rename it!");
				else {
					final InputStreamReader isr = new InputStreamReader(jf.getInputStream(ze));
					/*@formatter:off*/
					final Type type = new TypeToken<Collection<Dep>>(){}.getType();
					/*@formatter:on*/
					final List<Dep> list = gson.fromJson(new JsonReader(isr), type);
					for (final Dep line : list) {
						File downloadingFile = null;
						InputStream is = null;
						try {
							downloadingFile = new File(this.libDir, line.local);
							final URL remote = new URL(line.remote);

							final HttpURLConnection connection = (HttpURLConnection) remote.openConnection();
							connection.setConnectTimeout(10000);
							connection.setReadTimeout(10000);
							connection.setRequestProperty("User-Agent", Reference.MODID+" Downloader");

							if (connection.getResponseCode()!=HttpURLConnection.HTTP_OK)
								throw new RuntimeException();

							is = connection.getInputStream();
							FileUtils.copyInputStreamToFile(is, downloadingFile);
							Reference.logger.info("Download complete {}", downloadingFile);
						} catch (final Exception e) {
							if (downloadingFile!=null)
								downloadingFile.delete();
							Reference.logger.error(e);
							return;
						} finally {
							IOUtils.closeQuietly(is);
						}
						classLoader.addURL(downloadingFile.toURI().toURL());
					}
				}
			} catch (final JsonSyntaxException e) {
				Reference.logger.warn("Please don't rewrite the file in the Jar file!");
			} catch (final IOException e) {
				Reference.logger.error(e);
			} finally {
				if (jf!=null)
					IOUtils.closeQuietly(jf);
			}
		}
	}

	public static class Dep {
		public String remote;
		public String local;
	}
}
