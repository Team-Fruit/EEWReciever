package net.teamfruit.eewreciever2.common.quake.observation;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.common.collect.Sets;

import net.teamfruit.eewreciever2.common.Reference;
import net.teamfruit.eewreciever2.common.quake.observation.SeismicObservationPoints.PointsJson;
import net.teamfruit.eewreciever2.common.quake.observation.SeismicObservationPoints.PointsJson.Point;
import net.teamfruit.eewreciever2.common.quake.twitter.TweetQuakeNode;

public class OvservationPredictor {
	public static final OvservationPredictor INSTANCE = new OvservationPredictor();

	private PointsJson json;

	private OvservationPredictor() {
	}

	public PointsJson getPoints() {
		return this.json;
	}

	public void init() {
		new SeismicObservationPoints().get(new IJsonCallBack() {
			@Override
			public void onError(final Throwable t) {
				Reference.logger.error(t.getMessage(), t);
			}

			@Override
			public void onDone(final PointsJson json) {
				OvservationPredictor.this.json = json;
			}
		});
	}

	public static float getPointSeismic(final TweetQuakeNode node, final Point point) {
		return getPointSeismic(node.magnitude, node.depth, node.lat, node.lon, point);
	}

	public static float getPointSeismic(final float magnitude, final float depth, final float lat, final float lon, final Point point) {
		return QuakeCalculator.getMeasured(magnitude, depth, lat, lon, point.lat, point.lon, point.arv);
	}

	public PointsJson getAlarmAreas(final TweetQuakeNode node) {
		return getAlarmPoints(node.magnitude, node.depth, node.lat, node.lon);
	}

	public PointsJson getAlarmPoints(final float magnitude, final float depth, final float lat, final float lon) {
		final Map<String, Map<String, Map<String, List<Point>>>> regions = this.json.points;
		//地方
		for (final Entry<String, Map<String, Map<String, List<Point>>>> line1 : regions.entrySet()) {
			final Map<String, Map<String, List<Point>>> prefectures = line1.getValue();
			//都道府県
			for (final Entry<String, Map<String, List<Point>>> line2 : prefectures.entrySet()) {
				if (EnumPrefectures.fromName(line2.getKey()).getDistance(lat, lon, depth)>magnitude*100)
					continue;
				final Map<String, List<Point>> areas = line2.getValue();
				//震央区分
				for (final Entry<String, List<Point>> line3 : areas.entrySet()) {
					final List<Point> points = line3.getValue();
					//観測所
					for (final Point line4 : points) {
						if (3.5>=getPointSeismic(magnitude, depth, lat, lon, line4))
							points.remove(line4);
					}
					if (points.isEmpty())
						areas.remove(line3.getKey());
				}
				if (areas.isEmpty())
					regions.remove(line2.getKey());
			}
			if (regions.isEmpty())
				this.json.points.remove(line1.getKey());
		}
		return this.json;
	}

	public static EnumPrefectures[] toPrefectures(final PointsJson json) {
		final Set<EnumPrefectures> list = Sets.newHashSet();
		for (final Entry<String, Map<String, Map<String, List<Point>>>> line1 : json.points.entrySet()) {
			label: for (final Entry<String, Map<String, List<Point>>> line2 : line1.getValue().entrySet()) {
				for (final Entry<String, List<Point>> line3 : line2.getValue().entrySet()) {
					for (final Point line4 : line3.getValue()) {
						final String prefecture = line2.getKey();
						final String area = line3.getKey();
						if ("東京都".equals(prefecture)) {
							if ("神津島".equals(area)||"伊豆大島".equals(area)||"新島".equals(area)||"三宅島".equals(area)||"八丈島".equals(area))
								list.add(EnumPrefectures.IZU);
							else if ("小笠原".equals(area))
								list.add(EnumPrefectures.OGASAWARA);
							else
								list.add(EnumPrefectures.TOKYO);
						} else if ("鹿児島県".equals(prefecture)) {
							if ("鹿児島県十島村".equals(area))
								list.add(EnumPrefectures.TOSHIMA);
							else if ("鹿児島県甑島".equals(area))
								list.add(EnumPrefectures.KOSHIKI);
							else if ("鹿児島県種子島".equals(area))
								list.add(EnumPrefectures.TANE);
							else if ("鹿児島県屋久島".equals(area))
								list.add(EnumPrefectures.YAKU);
							else if ("鹿児島県奄美北部".equals(area)||"鹿児島県奄美南部".equals(area))
								list.add(EnumPrefectures.AMAMI);
							else
								list.add(EnumPrefectures.KAGOSHIMA);
						} else if ("沖縄県".equals(prefecture)) {
							if ("沖縄本島北部".equals(area)||"沖縄本島中南部".equals(area))
								list.add(EnumPrefectures.OKINAWA_HONTO);
							else if ("沖縄県久米島".equals(area))
								list.add(EnumPrefectures.KUME);
							else if ("沖縄県大東島".equals(area))
								list.add(EnumPrefectures.DAITO);
							else if ("沖縄県宮古島".equals(area))
								list.add(EnumPrefectures.MIYAKO);
							else if ("沖縄県石垣島".equals(area))
								list.add(EnumPrefectures.ISHIGAKI);
							else if ("沖縄県与那国島".equals(area))
								list.add(EnumPrefectures.YONAGUNI);
							else if ("沖縄県西表島".equals(area))
								list.add(EnumPrefectures.IRIOMOTE);
						} else
							list.add(EnumPrefectures.fromName(prefecture));
						continue label;
					}
				}
			}
		}
		return list.toArray(new EnumPrefectures[list.size()]);
	}
}
