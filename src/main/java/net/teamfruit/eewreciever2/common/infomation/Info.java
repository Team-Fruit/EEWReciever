package net.teamfruit.eewreciever2.common.infomation;

import java.util.Map;

public class Info {
	public Map<String, Version> versions;
	public Api apis;

	public static class Version {
		public String version;
		public String remote;
		public String local;
		public String message;
		public String image;
		public String website;
		public String changelog;
	}

	public static class Api {
		public Twitter twitter;

		public static class Twitter {
			String config;
		}
	}
}
