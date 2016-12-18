package net.teamfruit.eewreciever2;

import net.teamfruit.eewreciever2.common.Reference;
import net.teamfruit.eewreciever2.common.quake.observation.QuakeCalculator;
import net.teamfruit.eewreciever2.common.quake.twitter.TweetQuakeNode;

public class Debug {

	public static void main(final String[] args) throws Exception {
		final TweetQuakeNode node = new TweetQuakeNode().parseString("37,00,2016/11/22 06:00:33,0,8,ND20161122055958,2016/11/22 05:59:45,37.3,141.6,福島県沖,10,7.1,5強,1,1");
		Reference.logger.info(QuakeCalculator.getMeasured(node.magnitude, node.depth, node.lat, node.lon, 36.9483f, 140.9033f, 1.7214609f));
		//		Reference.logger.info(QuakeCalculator.getDistance(node.lat, node.lon, 36.9483, 140.9033, node.depth));
	}
}
