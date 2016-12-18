package net.teamfruit.eewreciever2.common.quake.calc;

import net.teamfruit.eewreciever2.common.quake.calc.SeismicObservationPoints.PointsJson;

public interface IJsonCallBack {
	void onDone(PointsJson json);

	void onError(Throwable t);
}
