package net.teamfruit.eewreciever2.common.quake.twitter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.security.Key;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.IOUtils;

import net.teamfruit.eewreciever2.EEWReciever2;
import net.teamfruit.eewreciever2.common.Reference;
import twitter4j.auth.AccessToken;

public final class TweetQuakeSecure {
	private static boolean doneInit;
	private static final String SUSHI = "4e6e4a4d4933524962475636543156714d57467a64413d3d";

	private TweetQuakeKey tweetQuakeKey;
	private AccessToken accessToken;

	public boolean isKeyValid() {
		return this.tweetQuakeKey!=null;
	}

	public boolean isTokenValid() {
		return this.accessToken!=null;
	}

	public TweetQuakeKey getTweetQuakeKey() {
		return this.tweetQuakeKey;
	}

	public AccessToken getAccessToken() {
		return this.accessToken;
	}

	public TweetQuakeSecure setAccessToken(final AccessToken accessToken) {
		this.accessToken = accessToken;
		return this;
	}

	public TweetQuakeSecure init() {
		if (doneInit)
			throw new IllegalStateException();
		try {
			this.tweetQuakeKey = decodeTweetQuakeKey(getResourceInputStream("file.eew"));
			this.accessToken = loadAccessToken(getConfigResourceInputStream("setting.dat"));
		} catch (final FileNotFoundException e) {
			Reference.logger.error(e.getMessage());
		} catch (final TweetQuakeSecureException e) {
			Reference.logger.error("Decode Error", e);
		} catch (final IOException e) {
			Reference.logger.error(e.getMessage(), e);
		}
		doneInit = true;
		return this;
	}

	public static InputStream getResourceInputStream(final String fileName) throws IOException {
		final File sourceFile = EEWReciever2.locations.modFile;
		if (sourceFile.isFile()) {
			JarFile jf = null;
			try {
				jf = new JarFile(sourceFile);
				final ZipEntry ze = jf.getEntry(fileName);
				if (ze!=null)
					return jf.getInputStream(ze);
				else
					return null;
			} finally {
				IOUtils.closeQuietly(jf);
			}
		} else {
			final File resource = new File(sourceFile, fileName);
			return new FileInputStream(resource);
		}
	}

	public static File getConfigResourceFile(final String fileName) {
		return new File(EEWReciever2.locations.modCfgDir, fileName);
	}

	/**
	 * Mod用ConfigDirectoryからResourceのInputStreamを取得します
	 * @param fileName
	 * @return ResourceのInputStream
	 * @throws FileNotFoundException ファイルが存在しないか、通常ファイルではなくディレクトリであるか、またはなんらかの理由で開くことができない場合。
	 */
	public static InputStream getConfigResourceInputStream(final String fileName) throws FileNotFoundException {
		return new FileInputStream(getConfigResourceFile(fileName));
	}

	/**
	 * Mod用ConfigDirectoryからResourceのOutputStreamを取得します
	 * @param fileName
	 * @return ResourceのOutputStream
	 * @throws FileNotFoundException ファイルが存在するが通常ファイルではなくディレクトリである場合、存在せず作成もできない場合、またはなんらかの理由で開くことができない場合
	 */
	public static OutputStream getConfigResourceOutputStream(final String fileName) throws FileNotFoundException {
		return new FileOutputStream(getConfigResourceFile(fileName));
	}

	private TweetQuakeKey decodeTweetQuakeKey(final InputStream is) throws TweetQuakeSecureException {
		CipherInputStream cis = null;
		ByteArrayInputStream bais = null;
		ByteArrayOutputStream baos = null;
		ObjectInputStream ois = null;
		try {
			final byte[] iv = new byte[16];
			is.read(iv);

			final byte[] keyarray = Base64.decodeBase64(Hex.decodeHex(SUSHI.toCharArray()));
			final Key key = new SecretKeySpec(keyarray, "AES");
			final IvParameterSpec ivspec = new IvParameterSpec(iv);
			final Cipher cipher = Cipher.getInstance("AES/PCBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, key, ivspec);

			cis = new CipherInputStream(is, cipher);
			baos = new ByteArrayOutputStream();
			final byte[] buf = new byte[1024];
			while (cis.read(buf)!=-1)
				baos.write(buf);

			bais = new ByteArrayInputStream(Base64.decodeBase64(baos.toByteArray()));
			ois = new ObjectInputStream(bais);
			return (TweetQuakeKey) ois.readObject();
		} catch (final Exception e) {
			throw new TweetQuakeSecureException(e.getMessage(), e);
		} finally {
			IOUtils.closeQuietly(cis);
			IOUtils.closeQuietly(bais);
			IOUtils.closeQuietly(baos);
			IOUtils.closeQuietly(ois);
		}
	}

	private AccessToken loadAccessToken(final InputStream is) throws TweetQuakeSecureException {
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(is);
			return (AccessToken) ois.readObject();
		} catch (final Exception e) {
			throw new TweetQuakeSecureException(e);
		} finally {
			IOUtils.closeQuietly(ois);
		}
	}

	public void storeAccessToken(final AccessToken token) throws IOException {
		storeAccessToken(token, false);
	}

	public void storeAccessToken(final AccessToken token, final boolean overwrite) throws IOException {
		if (overwrite) {
			final File file = getConfigResourceFile("setting.dat");
			if (file.exists()&&file.isFile())
				Files.delete(file.toPath());
		}

		ObjectOutputStream outputStream = null;
		try {
			outputStream = new ObjectOutputStream(getConfigResourceOutputStream("setting.dat"));
			outputStream.writeObject(token);
		} finally {
			IOUtils.closeQuietly(outputStream);
		}
	}
}
