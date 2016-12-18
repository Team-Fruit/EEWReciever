package net.teamfruit.eewreciever2.common.quake.observation;

import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import net.teamfruit.eewreciever2.common.util.Downloader;

public class SeismicObservationPoints implements Runnable {
	public static final String JSON_PATH = "https://gist.githubusercontent.com/sjcl/7375640587ad7c21e24a7e5e77336cdd/raw/13a226f1cce1ee608d30ae8e012ee1561597186d/SeismicObservationPoints.json";
	private static Gson gson = new Gson();

	private IJsonCallBack callback;

	public void get(final IJsonCallBack callback) {
		this.callback = callback;
		new Thread(this).start();
	}

	@Override
	public void run() {
		JsonReader jr = null;
		try {
			final HttpGet get = new HttpGet(JSON_PATH);
			final HttpResponse res = Downloader.downloader.getClient().execute(get);

			jr = new JsonReader(new InputStreamReader(res.getEntity().getContent(), Consts.UTF_8));

			final PointsJson json = gson.fromJson(jr, PointsJson.class);
			this.callback.onDone(json);
		} catch (final Exception e) {
			this.callback.onError(e);
		} finally {
			IOUtils.closeQuietly(jr);
		}
	}

	public static class PointsJson {
		public Map<String, Map<String, Map<String, List<Point>>>> points;

		public static class Point {
			public String name;
			public float lat;
			public float lon;
			//			public float avs;
			public float arv;

			public Point(final String name, final float lat, final float lon, final float arv) {
				this.name = name;
				this.lat = lat;
				this.lon = lon;
				this.arv = arv;
			}
		}
	}
}