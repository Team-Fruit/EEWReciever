package net.teamfruit.eewreciever2.lib.bnnwidget;

import java.util.List;

public interface WContainer<W extends WCommon> {

	boolean remove(final W widget);

	boolean add(final W widget);

	List<W> getContainer();

}
