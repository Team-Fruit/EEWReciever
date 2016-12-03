package net.teamfruit.eewreciever2.common;

import java.util.Queue;

public interface IQuake {
	Queue<IQuakeNode> getQuakeUpdate() throws QuakeException;
}
