package net.teamfruit.eewreciever2.common.quake.calc;

import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;

import net.teamfruit.eewreciever2.common.util.Downloader;

public class SeismicObservationPoints implements Runnable {
	public static final String JSON_PATH = "https://gist.githubusercontent.com/sjcl/7375640587ad7c21e24a7e5e77336cdd/raw/84df893f745ed68539c096e12e5780b59b8bb696/SeismicObservationPoints.json";
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
			@SerializedName("arv") //バグ回避
			public float avs;
			@SerializedName("avs") //バグ回避
			public float arv;
		}
	}
}