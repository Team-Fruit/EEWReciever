package net.teamfruit.eewreciever2.common.p2pquake;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;

import org.apache.commons.io.IOUtils;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;

import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import net.teamfruit.eewreciever2.common.IQuake;
import net.teamfruit.eewreciever2.common.IQuakeNode;
import net.teamfruit.eewreciever2.common.QuakeException;
import net.teamfruit.eewreciever2.common.util.Downloader;

public class P2PQuake implements IQuake {
	public static Gson gson = new Gson();
	public static long WaitMilliSeconds = 1000*15;

	private static Queue<IQuakeNode> empty = Queues.newArrayDeque();

	private final IP2PCallback callback;
	private Thread current;
	private String result;
	private Throwable error;

	public P2PQuake() {
		this.callback = new IP2PCallback() {
			@Override
			public void onDone(final String json) {
				P2PQuake.this.result = P2PQuake.this.result;
				P2PQuake.this.current = null;
			}

			@Override
			public void onError(final Throwable t) {
				P2PQuake.this.error = t;
				P2PQuake.this.current = null;
			}
		};
	}

	long lasttime;
	List<P2PQuakeJson> before;

	@Override
	public Queue<IQuakeNode> getQuakeUpdate() throws QuakeException {
		if (this.result!=null) {
			try {
				final Queue<IQuakeNode> nodes = Queues.newArrayDeque();
			/*@formatter:off*/
			final Type type = new TypeToken<Collection<P2PQuakeJson>>(){}.getType();
			/*@formatter:on*/
				final List<P2PQuakeJson> now = gson.fromJson(this.result, type);
				if (this.before!=null)
					for (final P2PQuakeJson line : getUpdate(this.before, now)) {
						switch (line.code) {
							case 551:
								nodes.add(new P2PQuakeQuakeInfoNode().parseString(this.result));
							case 552:
								nodes.add(new P2PQuakeTsunamiInfoNode().parseString(this.result));
							case 5610:
								nodes.add(new P2PQuakeSensingInfoNode().parseString(this.result));
						}
					}
				this.result = null;
				this.before = now;
				return nodes;
			} catch (final JsonParseException e) {
				throw new QuakeException("Parse Error", e);
			}
		} else if (this.error!=null) {
			final QuakeException e = new QuakeException(this.error);
			this.error = null;
			throw e;
		}

		if (this.current==null) {
			final long nowtime = System.currentTimeMillis();
			if (nowtime-this.lasttime>WaitMilliSeconds) {
				this.lasttime = nowtime;
				this.current = new P2PCommunicator(this.callback);
				this.current.start();
			}
		}
		return empty;
	}

	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");

	public static List<P2PQuakeJson> getUpdate(final List<P2PQuakeJson> older, final List<P2PQuakeJson> newer) throws QuakeException {
		if (older.size()<=0)
			return newer;

		try {
			final Date olderDate = dateFormat.parse(older.get(0).time);

			final List<P2PQuakeJson> list = Lists.newArrayList(newer);
			for (final Iterator<P2PQuakeJson> it = list.iterator(); it.hasNext();) {
				final P2PQuakeJson line = it.next();
				final Date newerDate = dateFormat.parse(line.time);
				if (olderDate.after(newerDate))
					it.remove();
			}
			return list;
		} catch (final ParseException e) {
			throw new QuakeException("Parse Error", e);
		}
	}

	public static class P2PCommunicator extends Thread {
		public static final String API_PATH = "http://api.p2pquake.com/v1/human-readable?limit=3";

		private final IP2PCallback callback;

		public P2PCommunicator(final IP2PCallback callback) {
			this.callback = callback;
		}

		@Override
		public void run() {
			InputStream is = null;
			try {
				final HttpGet httpPost = new HttpGet(API_PATH);
				final HttpResponse response = Downloader.downloader.getClient().execute(httpPost);

				is = response.getEntity().getContent();

				final InputStreamReader isr = new InputStreamReader(is, Consts.UTF_8);
				final BufferedReader reader = new BufferedReader(isr);

				final StringBuilder sb = new StringBuilder();
				String line;
				while ((line = reader.readLine())!=null)
					sb.append(line);
				this.callback.onDone(line);
			} catch (final IOException e) {
				this.callback.onError(e);
			} finally {
				IOUtils.closeQuietly(is);
			}
		}
	}
}