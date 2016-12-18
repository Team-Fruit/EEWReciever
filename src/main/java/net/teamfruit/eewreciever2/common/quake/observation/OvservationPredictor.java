package net.teamfruit.eewreciever2.common.quake.observation;

import net.teamfruit.eewreciever2.common.Reference;
import net.teamfruit.eewreciever2.common.quake.SeismicIntensity;
import net.teamfruit.eewreciever2.common.quake.observation.SeismicObservationPoints.PointsJson;
import net.teamfruit.eewreciever2.common.quake.observation.SeismicObservationPoints.PointsJson.Point;
import net.teamfruit.eewreciever2.common.quake.twitter.TweetQuakeNode;

public class OvservationPredictor {
	public static final OvservationPredictor INSTANCE = new OvservationPredictor();

	private PointsJson points;

	private OvservationPredictor() {
	}

	public PointsJson getPoints() {
		return this.points;
	}

	public void init() {
		new SeismicObservationPoints().get(new IJsonCallBack() {
			@Override
			public void onError(final Throwable t) {
				Reference.logger.error(t.getMessage(), t);
			}

			@Override
			public void onDone(final PointsJson json) {
				OvservationPredictor.this.points = json;
			}
		});
	}

	public SeismicIntensity getPointSeismic(final TweetQuakeNode node, final Point point) {
		return getPointSeismic(node.magnitude, node.depth, node.lat, node.lon, point);
	}

	public SeismicIntensity getPointSeismic(final float magnitude, final float depth, final float lat, final float lon, final Point point) {
		final float measured = QuakeCalculator.getMeasured(magnitude, depth, lat, lon, point.lat, point.lon, point.arv);
		return SeismicIntensity.fromMeasured(measured);
	}
}
