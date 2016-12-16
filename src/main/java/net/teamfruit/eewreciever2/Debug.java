package net.teamfruit.eewreciever2;

import net.teamfruit.eewreciever2.common.Reference;
import net.teamfruit.eewreciever2.common.quake.calc.QuakeCalculator;
import net.teamfruit.eewreciever2.common.quake.twitter.TweetQuakeNode;

public class Debug {

	public static void main(final String[] args) throws Exception {
		final TweetQuakeNode node = new TweetQuakeNode().parseString("37,00,2016/11/22 06:00:33,0,8,ND20161122055958,2016/11/22 05:59:45,37.3,141.6,福島県沖,10,7.1,5強,1,1");
		//		final double km = QuakeCalculator.angularDistance(node.lat, node.lon, 35.7067, 139.8683, 0);
		final double km = QuakeCalculator.angularDistance(48.829322, 2.220172, 41.8905, 12.4926, 0);
		Reference.logger.info(km);
	}
}
