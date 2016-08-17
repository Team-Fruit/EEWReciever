package com.bebehp.mc.eewreciever.twitter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

import org.apache.commons.compress.utils.IOUtils;

import com.bebehp.mc.eewreciever.EEWRecieverMod;
import com.bebehp.mc.eewreciever.Reference;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

/**
 * Twitter連携Helper
 * @author bebe
 */
public class TweetQuakeFileHelper {

	private static final File accessTokenFile = new File(EEWRecieverMod.folderDir, "AccessToken.dat");
	private static final Twitter twitter = TwitterFactory.getSingleton();

	/**
	 * jarファイル内のfile.eewを読み込みます<br>
	 * 但し、クラスパスがディレクトリだった場合、
	 * そちらを優先して読み込みます(開発用)
	 * @return TweetQuakeKey Fileが存在しなかった場合等はnull
	 */
	public static TweetQuakeKey loadKey() {
		final String absolutePath = System.getProperty("java.class.path");
		final String[] filePath = absolutePath.split(System.getProperty("path.separator"));
		final File runFile = new File(filePath[0]);
		if (runFile.isDirectory()) {
			final File keyFile = new File(runFile, "file.eew");
			if (keyFile.exists()) {
				ObjectInputStream is = null;
				try {
					is = new ObjectInputStream(new FileInputStream(keyFile));
					return (TweetQuakeKey)is.readObject();
				} catch (final IOException e) {
					Reference.logger.error(e);
				} catch (final ClassNotFoundException e) {
					Reference.logger.error(e);
				} finally {
					IOUtils.closeQuietly(is);
				}
			} else {
				Reference.logger.warn("keyFile({}) Not Found", keyFile);
			}
		} else {
			JarFile jar = null;
			ObjectInputStream is = null;
			try {
				jar = new JarFile(filePath[0]);
				final ZipEntry ze = jar.getEntry("file.eew");
				is = new ObjectInputStream(jar.getInputStream(ze));
				return (TweetQuakeKey)is.readObject();
			} catch (final IOException e) {
				Reference.logger.error(e);
			} catch (final ClassNotFoundException e) {
				Reference.logger.error(e);
			} finally {
				IOUtils.closeQuietly(is);
			}
		}
		return null;
	}

	/**
	 * AccessToken保管
	 * @param accessToken
	 */
	public static void storeAccessToken(final AccessToken accessToken) {
		EEWRecieverMod.createFolders();
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
		AccessToken accessToken = null;
		try {
			inputStream = new ObjectInputStream(new FileInputStream(accessTokenFile));
			accessToken = (AccessToken)inputStream.readObject();
		} catch (final FileNotFoundException e) {
			try {
				if (!accessTokenFile.createNewFile())
					Reference.logger.warn("Could not create AccessToken File[{}]!", accessTokenFile);
			} catch (final IOException e1) {
				Reference.logger.error(e1);
			}
		} catch (final IOException e) {
			Reference.logger.error(e);
		} catch (final ClassNotFoundException e) {
			Reference.logger.error(e);
		} finally {
			IOUtils.closeQuietly(inputStream);
		}
		return accessToken;
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
