package net.teamfruit.eewreciever2.common.quake;

import java.util.Queue;

public interface IQuake {
	Queue<IQuakeNode> getQuakeUpdate() throws QuakeException;
}
