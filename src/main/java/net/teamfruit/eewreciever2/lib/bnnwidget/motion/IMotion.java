package net.teamfruit.eewreciever2.lib.bnnwidget.motion;

public interface IMotion {

	IMotion restart();

	IMotion finish();

	IMotion pause();

	IMotion resume();

	IMotion setTime(float time);

	boolean isFinished();

	IMotion setAfter(Runnable r);

	float getDuration();

	float getEnd(float start);

	Runnable getAfter();

	void onFinished();

	float get(float start);

}