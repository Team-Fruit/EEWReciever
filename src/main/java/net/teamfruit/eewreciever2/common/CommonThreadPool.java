package net.teamfruit.eewreciever2.common;

public class CommonThreadPool extends AbstractThreadPool {
	private static final CommonThreadPool INSTANCE = new CommonThreadPool();

	public CommonThreadPool() {
		super("eewreciever2-Common Thread");
	}

	public static CommonThreadPool instance() {
		return INSTANCE;
	}
}
