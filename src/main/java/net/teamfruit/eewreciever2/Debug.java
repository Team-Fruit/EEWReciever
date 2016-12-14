package net.teamfruit.eewreciever2;

import net.teamfruit.eewreciever2.common.Reference;
import net.teamfruit.eewreciever2.common.quake.calc.QuakeCalculator;
import net.teamfruit.eewreciever2.common.quake.twitter.TweetQuakeNode;

public class Debug {

	public static TweetQuakeNode node;

	public static void main(final String[] args) throws Exception {
		node = new TweetQuakeNode().parseString("37,00,2016/11/22 06:00:33,0,8,ND20161122055958,2016/11/22 05:59:45,37.3,141.6,福島県沖,10,7.1,5強,1,1");
		final double lat1 = node.lat;
		final double lon1 = node.lon;
		final double lat2 = 35.7067d;
		final double lon2 = 139.8683d;
		final double km = QuakeCalculator.getDistanceBetween(lat1, lon1, lat2, lon2);
		Reference.logger.info(km/1000);
		//		Reference.logger.info(Math.pow(10, 1.83d-0.66*Math.log(260.2d)));

		//		Reference.logger.info(Math.pow(10, 5.0f/2-1.85f));
		//		Reference.logger.info(Math.exp(2.0f+0.6f*8.0f));

	}

}
