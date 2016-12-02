package net.teamfruit.eewreciever2.common.twitter;

import java.io.BufferedInputStream;
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

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.teamfruit.eewreciever2.EEWReciever2;
import net.teamfruit.eewreciever2.Reference;
import twitter4j.auth.AccessToken;

public class TweetQuakeSecure {
	public static final TweetQuakeSecure instance = new TweetQuakeSecure();
	private static final String SUSHI = "4e6e4a4d4933524962475636543156714d57467a64413d3d";

	private TweetQuakeKey tweetQuakeKey;
	private AccessToken accessToken;

	private TweetQuakeSecure() {
	}

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

	public void setAccessToken(final AccessToken accessToken) {
		this.accessToken = accessToken;
	}

	public void init(final FMLPreInitializationEvent event) {
		try {
			this.tweetQuakeKey = decodeTweetQuakeKey(getResourceInputStream(event, "file.eew"));
			this.accessToken = loadAccessToken(getConfigResourceInputStream(event, "setting.dat"));
		} catch (final TweetQuakeSecureException e) {
			Reference.logger.error("Decode Error", e);
		} catch (final FileNotFoundException e) {
			Reference.logger.error("File not found", e);
		} catch (final IOException e) {
			Reference.logger.error("IO Error", e);
		}
	}

	public static InputStream getResourceInputStream(final FMLPreInitializationEvent event, final String fileName) throws FileNotFoundException, IOException {
		final File sourceFile = event.getSourceFile();
		JarFile jf = null;
		try {
			if (sourceFile.isFile()) {
				jf = new JarFile(sourceFile);
				final ZipEntry ze = jf.getEntry(fileName);
				if (ze!=null)
					return jf.getInputStream(ze);
			} else {
				final File resource = new File(sourceFile, fileName);
				if (resource.exists()&&resource.isFile())
					return new FileInputStream(resource);
			}
		} finally {
			IOUtils.closeQuietly(jf);
		}
		return null;
	}

	public static InputStream getConfigResourceInputStream(final FMLPreInitializationEvent event, final String fileName) throws FileNotFoundException {
		final File resource = new File(EEWReciever2.locations.modCfgDir, fileName);
		return new FileInputStream(resource);
	}

	public static OutputStream getConfigResourceOutputStream(final FMLPreInitializationEvent event, final String fileName) throws FileNotFoundException {
		final File resource = new File(EEWReciever2.locations.modCfgDir, fileName);
		return new FileOutputStream(resource);
	}

	private TweetQuakeKey decodeTweetQuakeKey(final InputStream is) throws TweetQuakeSecureException {
		CipherInputStream cis = null;
		ByteArrayInputStream bais = null;
		ByteArrayOutputStream baos = null;
		ObjectInputStream ois = null;
		try {
			final BufferedInputStream fis = new BufferedInputStream(is);
			final byte[] iv = new byte[16];
			fis.read(iv);

			final byte[] keyarray = Base64.decodeBase64(Hex.decodeHex(SUSHI.toCharArray()));
			final Key key = new SecretKeySpec(keyarray, "AES");
			final IvParameterSpec ivspec = new IvParameterSpec(iv);
			final Cipher cipher = Cipher.getInstance("AES/PCBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, key, ivspec);

			cis = new CipherInputStream(fis, cipher);
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

	public void storeAccessToken(final OutputStream os, final AccessToken token) throws TweetQuakeSecureException {
		storeAccessToken(os, token, false);
	}

	public void storeAccessToken(final OutputStream os, final AccessToken token, final boolean overwrite) throws TweetQuakeSecureException {
		ObjectOutputStream outputStream = null;
		try {
			outputStream = new ObjectOutputStream(os);
			outputStream.writeObject(this.accessToken);
		} catch (final IOException e) {
			throw new TweetQuakeSecureException(e);
		} finally {
			IOUtils.closeQuietly(outputStream);
		}
	}
}
