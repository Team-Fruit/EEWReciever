package net.teamfruit.eewreciever2.common.http;

public class Communicator {
	public static Communicator instance = new Communicator();

	public void communicate(final ICommunicate communicate) {
		final Runnable thread = new Runnable() {
			@Override
			public void run() {
				communicate.communicate();
			}
		};
		new Thread(thread).start();
	}
}
