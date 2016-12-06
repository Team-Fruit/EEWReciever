package net.teamfruit.eewreciever2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Maps;

import net.teamfruit.eewreciever2.common.Reference;
import net.teamfruit.eewreciever2.common.quake.SeismicIntensity;
import net.teamfruit.eewreciever2.common.quake.p2pquake.P2PQuakeJson.QuakeInfo.Point;

public class Debug {

	public static List<Point> points = new ArrayList<Point>() {
		{
			add(new Point() {
				{
					this.addr = "宮崎県南部山沿い";
					this.scale = 40;
				}
			});
			add(new Point() {
				{
					this.addr = "熊本県阿蘇地方";
					this.scale = 45;
				}
			});
			add(new Point() {
				{
					this.addr = "福岡県福岡地方";
					this.scale = 40;
				}
			});
			add(new Point() {
				{
					this.addr = "熊本県天草・芦北地方";
					this.scale = 45;
				}
			});
			add(new Point() {
				{
					this.addr = "佐賀県南部";
					this.scale = 40;
				}
			});
			add(new Point() {
				{
					this.addr = "鹿児島県薩摩地方";
					this.scale = 40;
				}
			});
			add(new Point() {
				{
					this.addr = "長崎県南西部";
					this.scale = 40;
				}
			});
			add(new Point() {
				{
					this.addr = "熊本県熊本地方";
					this.scale = 70;
				}
			});
			add(new Point() {
				{
					this.addr = "長崎県島原半島";
					this.scale = 40;
				}
			});
			add(new Point() {
				{
					this.addr = "福岡県筑後地方";
					this.scale = 40;
				}
			});
			add(new Point() {
				{
					this.addr = "宮崎県北部山沿い";
					this.scale = 45;
				}
			});
			add(new Point() {
				{
					this.addr = "山口県西部";
					this.scale = 40;
				}
			});
			add(new Point() {
				{
					this.addr = "熊本県球磨地方";
					this.scale = 40;
				}
			});
			add(new Point() {
				{
					this.addr = "大分県南部";
					this.scale = 40;
				}
			});
			add(new Point() {
				{
					this.addr = "宮崎県北部平野部";
					this.scale = 40;
				}
			});
		}
	};

	public static void main(final String[] args) {
		final long start = System.currentTimeMillis();
		final Map<String, String> map = Maps.newTreeMap(Collections.reverseOrder());
		for (final Point line : points) {
			final String scale = SeismicIntensity.getP2PfromIntensity(line.scale).toString();
			final String str = map.get(scale);
			if (str!=null)
				map.put(scale, str+' '+line.addr);
			else
				map.put(scale, line.addr);
		}

		final StringBuilder sb = new StringBuilder();
		int i = 0;
		for (final Entry<String, String> line : map.entrySet()) {
			if (i!=0)
				sb.append(' ');
			sb.append("■震度").append(line.getKey()).append(' ').append(line.getValue());
			i++;
		}
		final String str = sb.toString();
		Reference.logger.info(System.currentTimeMillis()-start);
		Reference.logger.info(str);
	}
}
