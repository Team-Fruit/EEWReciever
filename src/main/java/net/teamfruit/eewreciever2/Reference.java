package net.teamfruit.eewreciever2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Reference {
	public static final String MODID = "EEWReciever2";
	public static final String NAME = "EEWReciever2";
	public static final String VERSION = "${version}";
	public static final String FORGE = "${forgeversion}";
	public static final String MINECRAFT = "${mcversion}";
	public static final String PROXY_SERVER = "net.teamfruit.eewreciever2.server.proxy.ServerProxy";
	public static final String PROXY_CLIENT = "net.teamfruit.eewreciever2.client.proxy.ClientProxy";
	//	public static final String GUI_FACTORY = "";

	public static Logger logger = LogManager.getLogger(Reference.MODID);
}
