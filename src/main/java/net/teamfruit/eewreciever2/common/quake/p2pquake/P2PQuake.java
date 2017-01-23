package net.teamfruit.eewreciever2.common.quake.p2pquake;

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
import java.util.ListIterator;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.IOUtils;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;

import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import net.teamfruit.eewreciever2.common.CommonThreadPool;
import net.teamfruit.eewreciever2.common.IThreadPool;
import net.teamfruit.eewreciever2.common.quake.IQuake;
import net.teamfruit.eewreciever2.common.quake.IQuakeNode;
import net.teamfruit.eewreciever2.common.quake.QuakeException;
import net.teamfruit.eewreciever2.common.util.Downloader;

public class P2PQuake implements IQuake {
	private static final P2PQuake INSTANCE = new P2PQuake();
	private static Gson gson = new Gson();
	private static long WaitMilliSeconds = TimeUnit.SECONDS.toMillis(20);
	private static final Queue<IQuakeNode> empty = Queues.newArrayDeque();

	private final IThreadPool threadPool = CommonThreadPool.instance();

	private P2PQuake() {
	}

	public static P2PQuake instance() {
		return INSTANCE;
	}

	private Future<String> future;
	private long lasttime;
	private List<P2PQuakeJson> before;

	@Override
	public Queue<IQuakeNode> getQuakeUpdate() throws QuakeException {
		if (this.future!=null) {
			if (this.future.isDone()) {
				try {
					final Queue<IQuakeNode> nodes = Queues.newArrayDeque();
			/*@formatter:off*/
			final Type type = new TypeToken<Collection<P2PQuakeJson>>(){}.getType();
			/*@formatter:on*/
					final String result = this.future.get();
					final List<P2PQuakeJson> now = gson.fromJson(result, type);
					if (this.before!=null)
						for (final P2PQuakeJson line : getUpdate(this.before, now)) {
							switch (line.code) {
								case 551:
									nodes.add(new P2PQuakeQuakeInfoNode().parseString(result));
								case 552:
									nodes.add(new P2PQuakeTsunamiInfoNode().parseString(result));
								case 5610:
									nodes.add(new P2PQuakeSensingInfoNode().parseString(result));
							}
						}
					this.future = null;
					this.before = now;
					return nodes;
				} catch (final JsonParseException e) {
					throw new QuakeException("Parse Error", e);
				} catch (final InterruptedException e) {
					throw new QuakeException(e);
				} catch (final ExecutionException e) {
					throw new QuakeException(e);
				}
			}
		} else {
			final long nowtime = System.currentTimeMillis();
			if (nowtime-this.lasttime>WaitMilliSeconds) {
				this.lasttime = nowtime;
				this.future = this.threadPool.submit(new P2PCommunicator());
			}
		}
		return empty;
	}

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");

	public static List<P2PQuakeJson> getUpdate(final List<P2PQuakeJson> older, final List<P2PQuakeJson> newer) throws QuakeException {
		if (older.size()<=0)
			return newer;

		try {
			final Date olderDate = dateFormat.parse(older.get(0).time);

			final List<P2PQuakeJson> list = Lists.newArrayList();
			for (final ListIterator<P2PQuakeJson> it = newer.listIterator(); it.hasPrevious();) {
				final P2PQuakeJson json = it.previous();
				list.add(json);
			}

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

	public static class P2PCommunicator implements Callable<String> {
		private static final String API_PATH = "http://api.p2pquake.com/v1/human-readable?limit=3";

		@Override
		public String call() throws IOException {
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
				return line;
			} finally {
				IOUtils.closeQuietly(is);
			}
		}
	}
}