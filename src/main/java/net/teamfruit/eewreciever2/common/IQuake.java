package net.teamfruit.eewreciever2.common;

import java.util.List;

public interface IQuake {
	List<IQuakeNode> getQuakeUpdate() throws QuakeException;
}
