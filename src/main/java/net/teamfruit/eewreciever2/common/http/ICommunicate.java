package net.teamfruit.eewreciever2.common.http;

public interface ICommunicate {
	void communicate();

	void setCallback(ICommunicateCallback callback);

	void cancel();
}
