package com.bebehp.mc.eewreciever.common.twitter;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

import com.bebehp.mc.eewreciever.EEWRecieverMod;
import com.bebehp.mc.eewreciever.Reference;
import com.bebehp.mc.eewreciever.common.proxy.CommonProxy;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import twitter4j.auth.AccessToken;

/**
 * Twitter連携Helper
 * @author bebe
 */
public class TweetQuakeFileManager {

	protected static final File accessTokenFile = new File(EEWRecieverMod.folderDir, "setting.dat");

	/**
	 * 	 * jarファイル内のfile.eewを読み込みます<br>
	 * 但し、パスがファイルではなかった場合は、そちらを読み込みます(開発用)
	 * @return TweetQuakeKey Fileが存在しなかった場合等はnull
	 */
	public static TweetQuakeKey loadKey() {
		final ModContainer c = Loader.instance().getIndexedModList().get(Reference.MODID);
		final File runFile = c.getSource();
		final String fileName = "file.eew";
		JarFile jar = null;
		try {
			if (runFile.isFile()) {
				jar = new JarFile(runFile);
				final ZipEntry ze = jar.getEntry(fileName);
				if (ze != null)
					return load(jar.getInputStream(ze));
			} else {
				//				Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation(Reference.MODID));
				final File keyFile = new File(runFile, fileName);
				return load(new FileInputStream(keyFile));
			}
		} catch (final IOException e) {
			Reference.logger.error(e);
		} finally {
			IOUtils.closeQuietly(jar);
		}
		return null;
	}

	/**
	 * Keyをloadして利用可能な状態にします
	 * @param InputStream
	 * @return TweetQuakeKey
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private static TweetQuakeKey load(final InputStream is) throws IOException {
		final BufferedInputStream bis = new BufferedInputStream(is);
		final byte[] byteData = new byte[bis.available()];
		bis.read(byteData);
		bis.close();

		final ByteArrayInputStream bais = new ByteArrayInputStream(Base64.decodeBase64(byteData));
		final ObjectInputStream ois = new ObjectInputStream(bais);
		TweetQuakeKey tweetQuakeKey = null;
		try {
			tweetQuakeKey = (TweetQuakeKey)ois.readObject();
		} catch (final ClassNotFoundException e) {
			Reference.logger.error(e);
		}
		bais.close();
		ois.close();
		return tweetQuakeKey;
	}

	/**
	 * AccessToken保管
	 * @param accessToken
	 */
	public static void storeAccessToken(final AccessToken accessToken) {
		CommonProxy.createFolders();
		ObjectOutputStream outputStream = null;
		try {
			outputStream = new ObjectOutputStream(new FileOutputStream(accessTokenFile));
			outputStream.writeObject(accessToken);
		} catch (final IOException e) {
			Reference.logger.error(e);
		} finally {
			IOUtils.closeQuietly(outputStream);
		}
	}

	/**
	 * 保管されたAccessToken取り出し
	 * @return AccessToken ファイルが無いかファイルにAccessTokenが無い場合はnull
	 */
	public static AccessToken loadAccessToken() {
		ObjectInputStream inputStream = null;
		try {
			inputStream = new ObjectInputStream(new FileInputStream(accessTokenFile));
			return (AccessToken)inputStream.readObject();
		} catch (final FileNotFoundException e) {
			try {
				if (!accessTokenFile.createNewFile())
					Reference.logger.warn("Could not create AccessToken File[{}]!", accessTokenFile);
			} catch (final IOException e1) {
				Reference.logger.error(e1);
			}
		} catch (final EOFException e) {
			// NO-OP
		} catch (final IOException e) {
			Reference.logger.error(e);
		} catch (final ClassNotFoundException e) {
			Reference.logger.error(e);
		} finally {
			IOUtils.closeQuietly(inputStream);
		}
		return null;
	}

	/**
	 * AccessTokenFileを消去します
	 * @return 消去に成功した場合ture, 失敗した場合はfalse
	 */
	public static boolean deleteAccessTokenFile() {
		if (accessTokenFile.exists()) {
			if (accessTokenFile.delete()) {
				return true;
			} else {
				Reference.logger.warn("Failed to delete the AccessTokenFile({})", accessTokenFile);
			}
		} else {
			Reference.logger.warn("AccessTokenFile({}) Not found", accessTokenFile);
		}
		return false;
	}
}
