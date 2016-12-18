package net.teamfruit.eewreciever2;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.teamfruit.eewreciever2.common.Reference;
import net.teamfruit.eewreciever2.common.quake.calc.IJsonCallBack;
import net.teamfruit.eewreciever2.common.quake.calc.SeismicObservationPoints;
import net.teamfruit.eewreciever2.common.quake.calc.SeismicObservationPoints.PointsJson;
import net.teamfruit.eewreciever2.common.quake.calc.SeismicObservationPoints.PointsJson.Point;

public class Debug {

	public static void main(final String[] args) throws Exception {
		//		final TweetQuakeNode node = new TweetQuakeNode().parseString("37,00,2016/11/22 06:00:33,0,8,ND20161122055958,2016/11/22 05:59:45,37.3,141.6,福島県沖,10,7.1,5強,1,1");
		//		final double km = QuakeCalculator.angularDistance(node.lat, node.lon, 35.7067, 139.8683, 0);
		//		final double km = QuakeCalculator.angularDistance(48.829322, 2.220172, 41.8905, 12.4926, 0);
		Reference.logger.info(Math.cos(600d/700d));
		Reference.logger.info(Math.pow(10, 1.83d-(0.66*Math.log10(260.2))));

		new SeismicObservationPoints().get(new IJsonCallBack() {
			@Override
			public void onError(final Throwable t) {
				Reference.logger.error(t.getMessage(), t);
			}

			@Override
			public void onDone(final PointsJson json) {
				for (final Entry<String, Map<String, Map<String, List<Point>>>> line1 : json.points.entrySet()) {
					for (final Entry<String, Map<String, List<Point>>> line2 : line1.getValue().entrySet()) {
						for (final Entry<String, List<Point>> line3 : line2.getValue().entrySet()) {
							for (final Point line4 : line3.getValue()) {
								line4.arv = (float) Math.pow(10, 1.83d-(0.66*Math.log10(line4.avs)));
								//								Reference.logger.info("avs{}", line4.avs);
								Reference.logger.info("arv{}", line4.arv);
							}
						}
					}
				}
				//				try {
				//					final JsonWriter jw = new JsonWriter(new FileWriter(new File("E:/new.json")));
				//					new Gson().toJson(json, PointsJson.class, jw);
				//					jw.close();
				//				} catch (final IOException e) {
				//					// TODO 自動生成された catch ブロック
				//					e.printStackTrace();
				//				}
			}
		});
	}
}
