package net.teamfruit.eewreciever2.common;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public interface IThreadPool {
	<V> Future<V> submit(Callable<V> task);

	void execute(Runnable command);
}
