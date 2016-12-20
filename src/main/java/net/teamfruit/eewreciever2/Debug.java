package net.teamfruit.eewreciever2;

import net.teamfruit.eewreciever2.common.quake.observation.OvservationPredictor;

public class Debug {

	public static void main(final String[] args) throws Exception {
		OvservationPredictor.INSTANCE.init();
		//		new SeismicObservationPoints().get(new IJsonCallBack() {
		//			@Override
		//			public void onError(final Throwable t) {
		//				Reference.logger.error(t.getMessage(), t);
		//			}
		//
		//			@Override
		//			public void onDone(final PointsJson json) {
		//				for (final Entry<String, Map<String, Map<String, List<Point>>>> line1 : json.points.entrySet()) {
		//					for (final Entry<String, Map<String, List<Point>>> line2 : line1.getValue().entrySet()) {
		//						for (final Entry<String, List<Point>> line3 : line2.getValue().entrySet()) {
		//							for (final Point line4 : line3.getValue()) {
		//								Reference.logger.info("arv{}", line4.arv);
		//							}
		//						}
		//					}
		//				}
		//			}
		//		});
	}
}
