package net.teamfruit.eewreciever2.common;

public enum SeismicIntensity {
	/*@formatter:off*/
	ZERO(0, "0", Float.MIN_VALUE, .5F),
	ONE(10, "1", .5F, 1.5F),
	TWO(20, "2", 1.5F, 2.5F),
	THREE(30, "3", 2.5F, 3.5F),
	FOUR(40, "4", 3.5F, 4.5F),
	FIVE_MINUS(45, "5弱", 4.5F, 5F),
	FIVE_PLUS(50, "5強", 5F, 5.5F),
	SIX_MINUS(55, "6弱", 5.5F, 6F),
	SIX_PLUS(60, "6強", 6F, 6.5F),
	SEVEN(70, "7", 6.5F, Float.MAX_VALUE),
	;
	/*@formatter:on*/

	private int p2pIntensity;
	private String jpIntensity;
	private float minMeasured;
	private float maxMeasured;

	private SeismicIntensity(final int p2pIntensity, final String jpIntensity, final float minMeasured, final float maxMeasured) {
		this.p2pIntensity = p2pIntensity;
		this.jpIntensity = jpIntensity;
		this.minMeasured = minMeasured;
		this.maxMeasured = maxMeasured;
	}

	/**
	 * P2P地震情報 JSON APIの震度表現
	 * @return 0~70
	 */
	public int getIntensity() {
		return this.p2pIntensity;
	}

	/**
	 * 日本の震度階級
	 * @return 0~7
	 */
	public String getJPNIntensity() {
		return this.jpIntensity;
	}

	/**
	 * P2P地震情報 JSON APIの震度からSeismicIntensityを取得します
	 * @param p2pIntensity
	 * @return
	 */
	public static SeismicIntensity getP2PfromIntensity(final int p2pIntensity) {
		for (final SeismicIntensity line : SeismicIntensity.values())
			if (line.getIntensity()==p2pIntensity)
				return line;
		return null;
	}

	/**
	 * 気象庁震度階級からSeismicIntensityを取得します
	 * @param jpIntensity
	 * @return
	 */
	public static SeismicIntensity getJPfromIntensity(final String jpIntensity) {
		for (final SeismicIntensity line : SeismicIntensity.values())
			if (line.getJPNIntensity().equals(jpIntensity))
				return line;
		return null;
	}

	/**
	 * 計測震度からSeismicIntensityを取得します
	 * @param measuredIntensity
	 * @return
	 */
	public static SeismicIntensity getMeasuredfromIntensity(final float measuredIntensity) {
		for (final SeismicIntensity line : SeismicIntensity.values())
			if (line.minMeasured<=measuredIntensity&&line.maxMeasured>measuredIntensity)
				return line;
		return null;
	}
}
