package net.teamfruit.eewreciever2.common.quake.calc;

import java.util.List;
import java.util.Map;

public class PointsJson {
	public Map<String, Region> points;

	public static class Region {
		Map<String, Prefecture> regions;

		public static class Prefecture {
			Map<String, List<Point>> prefactures;

			public static class Point {
				public String name;
				public float lat;
				public float lon;
				public float arv;
				public float avs;
			}
		}
	}
}
