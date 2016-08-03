package com.bebehp.mc.eewreciever.p2pquake;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;

import com.bebehp.mc.eewreciever.Reference;
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
		InputStream is = null;
		try {
			final HttpGet httpPost = new HttpGet(path);
			final HttpResponse response = this.httpClient.execute(httpPost);

			is = response.getEntity().getContent();

			final InputStreamReader isr = new InputStreamReader(is, "Shift_JIS");
			final BufferedReader reader = new BufferedReader(isr);

			String line;
			while ((line = reader.readLine()) != null) {
				list.add(new P2PQuakeNode().parseString(line));
			}
		} catch (final SocketTimeoutException e) {
			return null;
		} finally {
			IOUtils.closeQuietly(is);
		}
		return list;
	}

	public final HttpClient httpClient = getHttpClient();
	public static HttpClient getHttpClient() {
		// request configuration
		final RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000).setSocketTimeout(5000).build();
		// headers
		final List<Header> headers = new ArrayList<Header>();
		headers.add(new BasicHeader("Accept-Charset", "Shift_JIS"));
		headers.add(new BasicHeader("Accept-Language", "ja, en;q=0.8"));
		headers.add(new BasicHeader("User-Agent", Reference.MODID));
		// create client
		return HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).setDefaultHeaders(headers).build();
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
			if (now != null) {
				if (this.before != null)
					update = AbstractQuakeNode.getUpdate(this.before, now);
				this.before = now;
			}
		}
		return update;
	}
}
