package net.teamfruit.eewreciever2.common.quake.p2pquake;

public interface IP2PCallback {

	void onDone(String json);

	void onError(Throwable t);

}