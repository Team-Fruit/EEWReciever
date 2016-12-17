package net.teamfruit.eewreciever2.common.quake.calc;

public interface IJsonCallBack {
	void onDone(PointsJson json);

	void onError(Throwable t);
}
