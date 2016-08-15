package com.bebehp.mc.eewreciever.twitter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

public class TweetQuakeHelper {

	public static Twitter twitter = TwitterFactory.getSingleton();

	/**
	 * OAuth認証用
	 * @param pin
	 * @return AccessToken
	 * @throws TwitterException
	 * @author bebe
	 */
	public static AccessToken getAccessToken(final String pin) throws TwitterException {
		AccessToken accessToken = null;
		final RequestToken requestToken = twitter.getOAuthRequestToken();

		accessToken = twitter.getOAuthAccessToken(requestToken, pin);
		return accessToken;
	}

	/**
	 * AccessToken保管
	 * @param accessToken
	 * @author bebe
	 */
	public static void storeAccessToken(final AccessToken accessToken) {
		final File filename = createAccessTokenFileName();
		EEWRecieverMod.createFolders();
		ObjectOutputStream out = null;
		try {
			out = new ObjectOutputStream(new FileOutputStream(filename));
			out.writeObject(accessToken);
		} catch (final FileNotFoundException e) {
			Reference.logger.error(e);
		} catch (final IOException e) {
			Reference.logger.error(e);
		} finally {
			IOUtils.closeQuietly(out);
		}
	}

	/**
	 * 保管されたAccessToken取り出し
	 * @return AccessToken ファイルが無いかファイルにAccessTokenが無い場合はnull
	 * @author bebe
	 */
	public static AccessToken loadAccessToken() {
		final File filename = createAccessTokenFileName();
		ObjectInputStream input = null;
		AccessToken accessToken = null;
		try {
			input = new ObjectInputStream(new FileInputStream(filename));
			accessToken = (AccessToken)input.readObject();
		} catch (final ClassNotFoundException e) {
			Reference.logger.error(e);
		} catch (final IOException e) {
			Reference.logger.error(e);
		} finally {
			IOUtils.closeQuietly(input);
		}
		return accessToken;
	}

	/**
	 * AccessTokenFileを消去します
	 * @return 消去に成功した場合ture, 失敗した場合やファイルが見つからなかった場合はfalse
	 * @author bebe
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
			Reference.logger.warn("AccessTokenFile({}) not found", filename);
		}
		return false;
	}

	/**
	 * AccessTokenを保管するFileNameを生成します
	 * @return File
	 * @author bebe
	 */
	public static File createAccessTokenFileName() {
		return new File(EEWRecieverMod.folderDir, "AccessToken.dat");
	}
}
