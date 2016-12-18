package net.teamfruit.eewreciever2.common.quake.observation;

import net.teamfruit.eewreciever2.common.quake.observation.SeismicObservationPoints.PointsJson;

public interface IJsonCallBack {
	void onDone(PointsJson json);

	void onError(Throwable t);
}
