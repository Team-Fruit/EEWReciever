package com.bebehp.mc.eewreciever.ping;

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

public class APIPathP2PQUAKE implements IAPIPath {
//			EEWRecieverMod.logger.info(EEWRecieverMod.owner + " has some problem about [" + e.getMessage() + "].");

	public static final String API_PATH = "http://api.p2pquake.net/userquake";
	public static long WaitMilliSeconds = 1000 * 15;

	public static final String[] quakeType = new String[] {
			"震度速報",
			"震源情報",
			"震源・震度情報",
			"震源・詳細震度情報",
			"遠地地震情報"
	};

	public String getURL() {
		SimpleDateFormat format = new SimpleDateFormat("M/d");
		return API_PATH + "?date=" + format.format(new Date());
	}

	public List<QuakeNode> dlData(String path) throws IOException, QuakeException
	{
		List<QuakeNode> list = new LinkedList<QuakeNode>();

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
			list.add(parseString(line));
		}
		is.close();

		return list;
	}

	public static QuakeNode parseString(String text) throws QuakeException
	{
		QuakeNode node = new QuakeNode();
		try {
			String[] data = text.split("/");
			String[] time = data[0].split(",");

			node.uptime = new SimpleDateFormat("HH:mm:ss").parse(time[0]).getTime();
			node.type = time[1];
			node.time = time[2];
			node.strong = Integer.parseInt(data[1]);
			node.tsunami = "1".equals(data[2]);
			node.quaketype = quakeType[Integer.parseInt(data[3])-1];
			node.where = data[4];
			node.deep = data[5];
			node.magnitude = Float.parseFloat(data[6]);
			node.modified = "1".equals(data[7]);
			node.point = new String[] {data[8], data[9]};
		} catch (Exception e) {
			throw new QuakeException("parse error", e);
		}
		return node;
	}

	public List<QuakeNode> getQuake() throws QuakeException {
		try {
			return dlData(getURL());
		} catch (IOException e) {
			throw new QuakeException(e);
		}
	}

	long lasttime;
	List<QuakeNode> before;
	@Override
	public List<QuakeNode> getQuakeUpdate() throws QuakeException
	{
		List<QuakeNode> update = null;

		long nowtime = new Date().getTime();
		if (nowtime - lasttime > WaitMilliSeconds)
		{
			lasttime = nowtime;
			List<QuakeNode> now = this.getQuake();
			if (before != null) update = QuakeNode.getUpdate(before, now);
			before = now;
		}

		return update;
	}
}
