package net.teamfruit.eewreciever2.common.quake.observation;

import net.teamfruit.eewreciever2.common.Reference;
import net.teamfruit.eewreciever2.common.quake.observation.SeismicObservationPoints.PointsJson;

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

}
