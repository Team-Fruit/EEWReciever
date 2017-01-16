package net.teamfruit.eewreciever2.common.quake.observation;

public enum SeismicIntensity {
	/*@formatter:off*/
	ZERO("0", Float.MIN_VALUE, .5F),
	ONE("1", .5F, 1.5F),
	TWO("2", 1.5F, 2.5F),
	THREE("3", 2.5F, 3.5F),
	FOUR("4", 3.5F, 4.5F),
	FIVE_MINUS("5弱", 4.5F, 5F),
	FIVE_PLUS("5強", 5F, 5.5F),
	SIX_MINUS("6弱", 5.5F, 6F),
	SIX_PLUS("6強", 6F, 6.5F),
	SEVEN("7", 6.5F, Float.MAX_VALUE),
	UNKNOWN_FIVE_MINUS_UP("5弱以上", Float.NaN, Float.NaN),
	UNKNOWN("不明", Float.NaN, Float.NaN);
	/*@formatter:on*/

	private final String jpIntensity;
	private final float minMeasured;
	private final float maxMeasured;

	private SeismicIntensity(final String jpIntensity, final float minMeasured, final float maxMeasured) {
		this.jpIntensity = jpIntensity;
		this.minMeasured = minMeasured;
		this.maxMeasured = maxMeasured;
	}

	/**
	 * 日本の震度階級
	 * @return 0~7<br>UNKNOWNは"不明"
	 */
	public String getJPNIntensity() {
		return this.jpIntensity;
	}

	@Override
	public String toString() {
		return getJPNIntensity();
	}

	/**
	 * P2P地震情報 JSON APIの震度からSeismicIntensityを取得します
	 * @param p2pIntensity
	 * @return
	 */
	public static SeismicIntensity fromP2P(final int p2pIntensity) {
		switch (p2pIntensity) {
			case 0:
				return SeismicIntensity.ZERO;
			case 10:
				return SeismicIntensity.ONE;
			case 20:
				return SeismicIntensity.TWO;
			case 30:
				return SeismicIntensity.THREE;
			case 40:
				return SeismicIntensity.FOUR;
			case 45:
				return SeismicIntensity.FIVE_MINUS;
			case 50:
				return SeismicIntensity.FIVE_PLUS;
			case 55:
				return SeismicIntensity.SIX_MINUS;
			case 60:
				return SeismicIntensity.SIX_PLUS;
			case 70:
				return SeismicIntensity.SEVEN;
			case 46:
				return SeismicIntensity.UNKNOWN_FIVE_MINUS_UP;
			default:
				return SeismicIntensity.UNKNOWN;
		}
	}

	/**
	 * 気象庁震度階級からSeismicIntensityを取得します
	 * @param jpIntensity
	 * @return
	 */
	public static SeismicIntensity fromJP(final String jpIntensity) {
		for (final SeismicIntensity line : SeismicIntensity.values())
			if (line.getJPNIntensity().equals(jpIntensity))
				return line;
		return SeismicIntensity.UNKNOWN;
	}

	/**
	 * 計測震度からSeismicIntensityを取得します
	 * @param measuredIntensity
	 * @return
	 */
	public static SeismicIntensity fromMeasured(final float measuredIntensity) {
		for (final SeismicIntensity line : SeismicIntensity.values())
			if (line.minMeasured<=measuredIntensity&&line.maxMeasured>measuredIntensity)
				return line;
		return SeismicIntensity.UNKNOWN;
	}
}
