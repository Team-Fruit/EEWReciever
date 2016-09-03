package com.bebehp.mc.eewreciever.loader;

import com.bebehp.mc.eewreciever.Reference;
import com.google.common.eventbus.EventBus;

import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.ModMetadata;

public class CarrotModContainer extends DummyModContainer {

	public CarrotModContainer() {
		super(new ModMetadata());

		final ModMetadata meta = getMetadata();

		meta.modId = Reference.MODID;
		meta.name = Reference.NAME;
		meta.version = Reference.VERSION;
		meta.authorList = Reference.AUTHOR_LIST;
		meta.description = Reference.DESCRIPTION;
		meta.url = Reference.URL;
		meta.credits = Reference.CREDITS;
		setEnabledState(true);
	}

	@Override
	public boolean registerBus(final EventBus bus, final LoadController controller) {
		bus.register(this);
		return true;
	}
}
