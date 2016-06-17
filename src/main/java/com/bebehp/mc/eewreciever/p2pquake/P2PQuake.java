package com.bebehp.mc.eewreciever.p2pquake;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.bebehp.mc.eewreciever.EEWRecieverMod;
import com.bebehp.mc.eewreciever.ping.AbstractQuakeNode;
import com.bebehp.mc.eewreciever.ping.IQuake;
import com.bebehp.mc.eewreciever.ping.QuakeException;

public class P2PQuake implements IQuake {
	// EEWRecieverMod.logger.info(EEWRecieverMod.owner + " has some problem
	// about [" + e.getMessage() + "].");

	public static final String API_PATH = "http://api.p2pquake.net/userquake";
	public static long WaitMilliSeconds = 1000 * 15;

	private static List<AbstractQuakeNode> empty = new LinkedList<AbstractQuakeNode>();

	public String getURL() {
		final SimpleDateFormat format = new SimpleDateFormat("M/d");
		return API_PATH + "?date=" + format.format(new Date());
	}

	public List<AbstractQuakeNode> dlData(final String path) throws IOException, QuakeException {
		final List<AbstractQuakeNode> list = new LinkedList<AbstractQuakeNode>();
		final URL url = new URL(path);
		final URLConnection connection = url.openConnection();
		final InputStream is = connection.getInputStream();
		try {
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(5000);
			connection.setRequestProperty("User-Agent", EEWRecieverMod.owner);

			final InputStreamReader isr = new InputStreamReader(is, "Shift_JIS");
			final BufferedReader reader = new BufferedReader(isr);

			String line;
			while ((line = reader.readLine()) != null) {
				list.add(new P2PQuakeNode().parseString(line));
			}
		} catch (final SocketTimeoutException e) {
		} finally {
			is.close();
		}
		return list;
	}

	public List<AbstractQuakeNode> getQuake() throws QuakeException {
		try {
			return dlData(getURL());
		} catch (final IOException e) {
			throw new QuakeException(e);
		}
	}

	long lasttime;
	List<AbstractQuakeNode> before;

	@Override
	public List<AbstractQuakeNode> getQuakeUpdate() throws QuakeException {
		List<AbstractQuakeNode> update = empty;

		final long nowtime = new Date().getTime();
		if (nowtime - this.lasttime > WaitMilliSeconds) {
			this.lasttime = nowtime;
			final List<AbstractQuakeNode> now = getQuake();
			if (this.before != null)
				update = AbstractQuakeNode.getUpdate(this.before, now);
			this.before = now;
		}

		return update;
	}
}
