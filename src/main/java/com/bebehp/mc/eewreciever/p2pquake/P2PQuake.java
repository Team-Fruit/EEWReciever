package com.bebehp.mc.eewreciever.p2pquake;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
//			EEWRecieverMod.logger.info(EEWRecieverMod.owner + " has some problem about [" + e.getMessage() + "].");

	public static final String API_PATH = "http://api.p2pquake.net/userquake";
	public static long WaitMilliSeconds = 1000 * 15;

	private static List<AbstractQuakeNode> empty = new LinkedList<AbstractQuakeNode>();

	public String getURL() {
		SimpleDateFormat format = new SimpleDateFormat("M/d");
		return API_PATH + "?date=" + format.format(new Date());
	}

	public List<AbstractQuakeNode> dlData(String path) throws IOException, QuakeException
	{
		List<AbstractQuakeNode> list = new LinkedList<AbstractQuakeNode>();

		URL url = new URL(path);
		URLConnection connection = url.openConnection();
		connection.setConnectTimeout(5000);
		connection.setReadTimeout(5000);
		connection.setRequestProperty("User-Agent", EEWRecieverMod.owner);

		InputStream is = connection.getInputStream();
		InputStreamReader isr = new InputStreamReader(is, "Shift_JIS");
		BufferedReader reader = new BufferedReader(isr);

		String line;
		while ((line = reader.readLine()) != null) {
			list.add(new P2PQuakeNode().parseString(line));
		}
		is.close();

		return list;
	}



	public List<AbstractQuakeNode> getQuake() throws QuakeException {
		try {
			return dlData(getURL());
		} catch (IOException e) {
			throw new QuakeException(e);
		}
	}

	long lasttime;
	List<AbstractQuakeNode> before;
	@Override
	public List<AbstractQuakeNode> getQuakeUpdate() throws QuakeException
	{
		List<AbstractQuakeNode> update = empty;

		long nowtime = new Date().getTime();
		if (nowtime - lasttime > WaitMilliSeconds)
		{
			lasttime = nowtime;
			List<AbstractQuakeNode> now = this.getQuake();
			if (before != null) update = AbstractQuakeNode.getUpdate(before, now);
			before = now;
		}

		return update;
	}
}
