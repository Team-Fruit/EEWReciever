package com.bebehp.mc.eewreciever;

import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Reference {
	public static final String MODID = "EEWReciever";
	public static final String NAME = "EEWReciever";
	public static final String VERSION = "3.1.0";
	public static final String FORGE = "10.13.4.1558";
	public static final String MINECRAFT = "1.7.10";
	public static final String PROXY_SERVER = "com.bebehp.mc.eewreciever.server.proxy.ServerProxy";
	public static final String PROXY_CLIENT = "com.bebehp.mc.eewreciever.client.proxy.ClientProxy";
	public static final String MOD_CONTAINER = "com.bebehp.mc.eewreciever.loader.CarrotModContainer";
	public static final List<String> AUTHOR_LIST = Arrays.asList("sjcl", "Kamesuta");
	public static final String DESCRIPTION = "Watch EEW Informations.";
	public static final String URL = "http://fruit.bebehp.com/eewreciever";
	public static final String CREDITS = "Copyright (C) 2016 TeamFruit";

	public static Logger logger = LogManager.getLogger(Reference.MODID);
}
