package net.teamfruit.eewreciever2.common.quake.p2pquake;

public enum P2PQuakeNodeIssue {
	/*@formatter:off*/
	SCALEPROMPT("地震速報"),
	DESTINATION("震源情報"),
	SCALEANDDESTINATION("震源・震度情報"),
	DETAILSCALE("各地の震度情報"),
	FOREGIN("遠地地震情報"),
	OTHER("不明な地震情報")
	;
	/*@formatter:on*/

	private final String str;

	private P2PQuakeNodeIssue(final String str) {
		this.str = str;
	}

	@Override
	public String toString() {
		return this.str;
	}
}
