package net.teamfruit.eewreciever2.common.p2pquake;

import java.util.List;

import com.sun.javafx.collections.MappingChange.Map;

public class P2PQuakeJson {
	public String time;
	public int code;

	public static class QuakeInfo extends P2PQuakeJson {
		public Issue issue;
		public EarthQuake earthquake;
		public List<Point> points;

		public static class Issue {
			public String source;
			public String type;
		}

		public static class EarthQuake {
			public String time;
			public Hypocenter hypocenter;
			public int maxScale;
			public String domesticTsunami;

			public static class Hypocenter {
				public String name;
				public String latitude;
				public String longitude;
				public String depth;
				public String magnitude;
			}
		}

		public static class Point {
			public String addr;
			public int scale;
		}
	}

	public static class TsunamiInfo extends P2PQuakeJson {
		public Issue issue;
		public boolean cancelled;
		public List<Area> areas;

		public static class Issue {
			public String type;
		}

		public static class Area {
			public String name;
			public String grade;
			public boolean immediate;
		}
	}

	public static class QuakeSensingInfo extends P2PQuakeJson {
		int count;
		public Map<String, Integer> areas;
		public Map<String, Integer> prefs;
		public Map<String, Integer> regions;
	}
}
