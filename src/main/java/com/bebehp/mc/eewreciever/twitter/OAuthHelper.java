package com.bebehp.mc.eewreciever.twitter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.commons.compress.utils.IOUtils;

import com.bebehp.mc.eewreciever.EEWRecieverMod;
import com.bebehp.mc.eewreciever.Reference;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

/**
 * Twitter連携Helper
 * @author bebe
 */
public class OAuthHelper {

	/**
	 * OAuth認証用
	 * @param pin
	 * @return AccessToken
	 * @throws TwitterException
	 */
	public static AccessToken getAccessTokentoPin(final String pin) throws TwitterException {
		final Twitter twitter = TwitterFactory.getSingleton();
		final RequestToken requestToken = twitter.getOAuthRequestToken();
		final AccessToken accessToken = twitter.getOAuthAccessToken(requestToken, pin);
		return accessToken;
	}

	/**
	 * AccessToken保管
	 * @param accessToken
	 */
	public static void storeAccessToken(final AccessToken accessToken) {
		final File filename = createAccessTokenFileName();
		EEWRecieverMod.createFolders();
		ObjectOutputStream outputStream = null;
		try {
			outputStream = new ObjectOutputStream(new FileOutputStream(filename));
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
		final File filename = createAccessTokenFileName();
		ObjectInputStream inputStream = null;
		AccessToken accessToken = null;
		try {
			inputStream = new ObjectInputStream(new FileInputStream(filename));
			accessToken = (AccessToken)inputStream.readObject();
		} catch (final IOException e) {
			Reference.logger.error(e);
		} catch (final ClassNotFoundException
				e) {
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
		final File filename = createAccessTokenFileName();
		if (filename.exists()) {
			if (filename.delete()) {
				return true;
			} else {
				Reference.logger.warn("Failed to delete the AccessTokenFile({})", filename);
			}
		} else {
			Reference.logger.warn("AccessTokenFile({}) Not found", filename);
		}
		return false;
	}

	/**
	 * OAuth認証用URLを作成します
	 * @return 認証URL(String)
	 * @throws TwitterException
	 */
	public static String ceateAuthorizationURL() throws TwitterException {
		final Twitter twitter = TwitterFactory.getSingleton();
		final RequestToken requestToken = twitter.getOAuthRequestToken();
		return requestToken.getAuthenticationURL();
	}

	/**
	 * AccessTokenを保管するFileNameを生成します
	 * @return File
	 */
	public static File createAccessTokenFileName() {
		return new File(EEWRecieverMod.folderDir, "AccessToken.dat");
	}

}
