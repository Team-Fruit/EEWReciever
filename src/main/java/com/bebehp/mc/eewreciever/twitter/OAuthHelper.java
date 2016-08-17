package com.bebehp.mc.eewreciever.twitter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;

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

	public static final File accessTokenFile = new File(EEWRecieverMod.folderDir, "AccessToken.dat");

	/**
	 * jarファイル内のKeyを読み込みます
	 * @return TweetQuakeKey
	 */
	public static TweetQuakeKey loadKey() {
		URL keyURL = null;
		ObjectInputStream objectInputStream = null;
		InputStream inputStream = null;
		TweetQuakeKey tweetQuakeKey = null;
		try {
			keyURL = new URL(OAuthHelper.class.getResource("."), "file.eew");
			inputStream = keyURL.openConnection().getInputStream();
			objectInputStream = new ObjectInputStream(inputStream);
			tweetQuakeKey = (TweetQuakeKey)objectInputStream.readObject();
		} catch (final ClassNotFoundException e) {
			Reference.logger.error(e);
		} catch (final IOException e) {
			Reference.logger.error(e);
		} finally {
			IOUtils.closeQuietly(objectInputStream);
		}
		return tweetQuakeKey;
	}

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
}
