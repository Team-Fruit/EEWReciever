package net.teamfruit.eewreciever2.client;

import net.teamfruit.eewreciever2.common.AbstractThreadPool;

public class ClientThreadPool extends AbstractThreadPool {
	private static final ClientThreadPool INSTANCE = new ClientThreadPool();

	public ClientThreadPool() {
		super("eewreciever2-Client Thread");
	}

	public static ClientThreadPool instance() {
		return INSTANCE;
	}
}
