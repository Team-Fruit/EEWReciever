package net.teamfruit.eewreciever2.common;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

public abstract class AbstractThreadPool implements IThreadPool {

	protected final ExecutorService threadPool;

	public AbstractThreadPool(final String nameFormat) {
		this.threadPool = Executors.newSingleThreadExecutor(new ThreadFactoryBuilder().setNameFormat(nameFormat).build());
	}

	@Override
	public <V> Future<V> submit(final Callable<V> task) {
		return this.threadPool.submit(task);
	}

	@Override
	public void execute(final Runnable command) {
		this.threadPool.execute(command);
	}
}
