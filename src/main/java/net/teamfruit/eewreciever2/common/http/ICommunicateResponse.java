package net.teamfruit.eewreciever2.common.http;

public interface ICommunicateResponse {
	boolean isSuccess();

	Throwable getError();
}
