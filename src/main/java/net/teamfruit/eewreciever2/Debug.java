package net.teamfruit.eewreciever2;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.teamfruit.eewreciever2.common.Reference;
import net.teamfruit.eewreciever2.common.quake.observation.IJsonCallBack;
import net.teamfruit.eewreciever2.common.quake.observation.SeismicObservationPoints;
import net.teamfruit.eewreciever2.common.quake.observation.SeismicObservationPoints.PointsJson;
import net.teamfruit.eewreciever2.common.quake.observation.SeismicObservationPoints.PointsJson.Point;

public class Debug {

	public static void main(final String[] args) throws Exception {
		//		final TweetQuakeNode node = new TweetQuakeNode().parseString("37,00,2016/11/22 06:00:33,0,8,ND20161122055958,2016/11/22 05:59:45,37.3,141.6,福島県沖,10,7.1,5強,1,1");
		//		Reference.logger.info(node.toString());
		//		Reference.logger.info(QuakeCalculator.getDistance(node.lat, node.lon, 36.9483, 140.9033, node.depth));
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
								Reference.logger.info("arv{}", line4.arv);
							}
						}
					}
				}
			}
		});
	}
}
