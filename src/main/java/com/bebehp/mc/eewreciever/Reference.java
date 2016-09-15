package com.bebehp.mc.eewreciever;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Reference {
	public static final String MODID = "EEWReciever";
	public static final String NAME = "EEWReciever";
	public static final String VERSION = "3.1.3";
	public static final String FORGE = "10.13.4.1558";
	public static final String MINECRAFT = "1.7.10";
	public static final String PROXY_SERVER = "com.bebehp.mc.eewreciever.server.proxy.ServerProxy";
	public static final String PROXY_CLIENT = "com.bebehp.mc.eewreciever.client.proxy.ClientProxy";

	public static Logger logger = LogManager.getLogger(Reference.MODID);
}
