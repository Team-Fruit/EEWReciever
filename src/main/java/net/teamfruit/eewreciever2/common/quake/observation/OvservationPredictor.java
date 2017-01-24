package net.teamfruit.eewreciever2.common.quake.observation;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.math.NumberUtils;

import com.google.common.collect.Sets;

import net.teamfruit.eewreciever2.common.Reference;
import net.teamfruit.eewreciever2.common.quake.observation.SeismicObservationPoints.PointsJson;
import net.teamfruit.eewreciever2.common.quake.observation.SeismicObservationPoints.PointsJson.Point;
import net.teamfruit.eewreciever2.common.quake.twitter.TweetQuakeNode;

public class OvservationPredictor {
	private static final OvservationPredictor INSTANCE = new OvservationPredictor();

	private PointsJson json;
	private final DecimalFormat format;

	private OvservationPredictor() {
		this.format = new DecimalFormat("#.#");
		this.format.setRoundingMode(RoundingMode.DOWN);
	}

	public static OvservationPredictor instance() {
		return INSTANCE;
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
		if (this.json==null)
			return new PointsJson();
		final PointsJson json = this.json.clone();
		for (final Iterator<Entry<String, Map<String, Map<String, List<Point>>>>> it1 = json.points.entrySet().iterator(); it1.hasNext();) {
			final Map<String, Map<String, List<Point>>> prefectures = it1.next().getValue();
			for (final Iterator<Entry<String, Map<String, List<Point>>>> it2 = prefectures.entrySet().iterator(); it2.hasNext();) {
				final Entry<String, Map<String, List<Point>>> line2 = it2.next();
				if (EnumPrefecture.fromName(line2.getKey()).getDistance(lat, lon, depth)>magnitude*(magnitude*10)*(depth/10)) {
					it2.remove();
					continue;
				}
				final Map<String, List<Point>> areas = line2.getValue();
				for (final Iterator<Entry<String, List<Point>>> it3 = areas.entrySet().iterator(); it3.hasNext();) {
					final Entry<String, List<Point>> line3 = it3.next();
					final List<Point> points = line3.getValue();
					for (final Iterator<Point> it4 = points.iterator(); it4.hasNext();) {
						final Point line4 = it4.next();
						if (3.5f>=NumberUtils.toFloat(this.format.format(getPointSeismic(magnitude, depth, lat, lon, line4))))
							it4.remove();
					}
					if (points.isEmpty())
						it3.remove();
				}
				if (areas.isEmpty())
					it2.remove();
			}
			if (json.points.isEmpty())
				it1.remove();
		}
		return this.json;
	}

	public static EnumPrefecture[] toPrefectures(final PointsJson json) {
		final Set<EnumPrefecture> list = Sets.newLinkedHashSet();
		for (final Entry<String, Map<String, Map<String, List<Point>>>> line1 : json.points.entrySet()) {
			label: for (final Entry<String, Map<String, List<Point>>> line2 : line1.getValue().entrySet()) {
				for (final Entry<String, List<Point>> line3 : line2.getValue().entrySet()) {
					for (final Point line4 : line3.getValue()) {
						final String prefecture = line2.getKey();
						final String area = line3.getKey();
						if ("東京都".equals(prefecture)) {
							if ("神津島".equals(area)||"伊豆大島".equals(area)||"新島".equals(area)||"三宅島".equals(area)||"八丈島".equals(area))
								list.add(EnumPrefecture.IZU);
							else if ("小笠原".equals(area))
								list.add(EnumPrefecture.OGASAWARA);
							else
								list.add(EnumPrefecture.TOKYO);
						} else if ("鹿児島県".equals(prefecture)) {
							if ("鹿児島県十島村".equals(area))
								list.add(EnumPrefecture.TOSHIMA);
							else if ("鹿児島県甑島".equals(area))
								list.add(EnumPrefecture.KOSHIKI);
							else if ("鹿児島県種子島".equals(area))
								list.add(EnumPrefecture.TANE);
							else if ("鹿児島県屋久島".equals(area))
								list.add(EnumPrefecture.YAKU);
							else if ("鹿児島県奄美北部".equals(area)||"鹿児島県奄美南部".equals(area))
								list.add(EnumPrefecture.AMAMI);
							else
								list.add(EnumPrefecture.KAGOSHIMA);
						} else if ("沖縄県".equals(prefecture)) {
							if ("沖縄県本島北部".equals(area)||"沖縄県本島中南部".equals(area))
								list.add(EnumPrefecture.OKINAWA_HONTO);
							else if ("沖縄県久米島".equals(area))
								list.add(EnumPrefecture.KUME);
							else if ("沖縄県大東島".equals(area))
								list.add(EnumPrefecture.DAITO);
							else if ("沖縄県宮古島".equals(area))
								list.add(EnumPrefecture.MIYAKO);
							else if ("沖縄県石垣島".equals(area))
								list.add(EnumPrefecture.ISHIGAKI);
							else if ("沖縄県与那国島".equals(area))
								list.add(EnumPrefecture.YONAGUNI);
							else if ("沖縄県西表島".equals(area))
								list.add(EnumPrefecture.IRIOMOTE);
						} else
							list.add(EnumPrefecture.fromName(prefecture));
						continue label;
					}
				}
			}
		}
		return list.toArray(new EnumPrefecture[list.size()]);
	}
}