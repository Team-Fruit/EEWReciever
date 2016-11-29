package net.teamfruit.eewreciever2.common;

import javax.annotation.Nullable;

public enum SeismicIntensity {
	/*@formatter:off*/
	ZERO(0, "0"),
	ONE(10, "1"),
	TWO(20, "2"),
	THREE(30, "3"),
	FOUR(40, "4"),
	FIVE_MINUS(45, "5弱"),
	FIVE_PLUS(50, "5強"),
	SIX_MINUS(55, "6弱"),
	SIX_PLUS(60, "6強"),
	SEVEN(70, "7"),
	;
	/*@formatter:on*/

	private int p2pIntensity;
	private String jpnIntensity;

	private SeismicIntensity(final int p2pIntensity, final String jpnIntensity) {
		this.p2pIntensity = p2pIntensity;
		this.jpnIntensity = jpnIntensity;
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
		return this.jpnIntensity;
	}

	@Nullable
	public static SeismicIntensity getSeismicIntensity(final int intensity) {
		for (final SeismicIntensity line : SeismicIntensity.values()) {
			if (line.getIntensity()==intensity)
				return line;
		}
		return null;
	}

	@Nullable
	public static SeismicIntensity getSeismicIntensity(final String jpn_Intensity) {
		for (final SeismicIntensity line : SeismicIntensity.values()) {
			if (line.getJPNIntensity().equals(jpn_Intensity))
				return line;
		}
		return null;
	}
}
