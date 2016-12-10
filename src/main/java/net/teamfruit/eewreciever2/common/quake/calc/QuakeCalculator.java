package net.teamfruit.eewreciever2.common.quake.calc;

/**
 * 緊急地震速報のデータからの計算用クラスです
 * @author bebe, BSC24公式チャットの皆さん
 *
 */
public class QuakeCalculator {

	private QuakeCalculator() {
	}

	/**
	 * 地理緯度を地心緯度にします
	 * @param geographicalLatitude 地理緯度
	 * @return 地心緯度
	 */
	public static double toGeospatialLatitude(final double geographicalLatitude) {
		return geographicalLatitude-Math.toRadians(11.55d/60)*Math.sin(2*geographicalLatitude);
	}

	public static final double A = 6370.291d;

	/**
	 * 角距離を求めます
	 * @param lat1
	 * @param lon1
	 * @param lat2
	 * @param lon2
	 * @return 角距離
	 */
	public static double angularDistance(final double lat1, final double lon1, final double lat2, final double lon2, final double depth) {
		final double ae = Math.cos(lat1)*Math.cos(lon1)*(A-depth)/A;
		final double ax = Math.cos(lat2)*Math.cos(lon2);
		final double be = Math.cos(lat1)*Math.sin(lon1)*(A-depth)/A;
		final double bx = Math.cos(lat2)*Math.sin(lon2);
		final double ce = Math.sin(lat1)*(A-depth)/A;
		final double cx = Math.sin(lat2);
		return Math.cos(Math.sqrt(Math.pow(ae-ax, 2)+Math.pow(be-bx, 2)+Math.pow(ce-cx, 2))/2/2);
	}

	/**
	 * 距離を求めます
	 * @param angularDistance 角距離
	 * @return 距離(km)
	 */
	public static double toEpicenterDistance(final double angularDistance) {
		return angularDistance*A;
	}

	/**
	 * 断層最短距離を計算します
	 * @param focusDistance 震源距離
	 * @param faultLength 断層長
	 * @return 断層最短距離
	 */
	public static double faultShortestDistance(final double focusDistance, final double faultLength) {
		return Math.max(focusDistance-faultLength/2, 3.0d);
	}

	/**
	 * 断層の長さを計算します
	 * @param mw モーメントマグニチュード
	 * @return 断層長
	 */
	public static double faultLength(final double mw) {
		return Math.pow(10, mw/2-1.85d);
	}

	/**
	 * 気象庁マグニチュードからモーメントマグニチュードを計算します
	 * @param mjma 気象庁マグニチュード
	 * @return モーメントマグニチュード
	 */
	public static double momentMagnitude(final double mjma) {
		return mjma-0.171d;
	}

	/**
	 * 基準基盤上における最大速度
	 * @param mw モーメントマグニチュード
	 * @param depth 深さ
	 * @param faultShortestDistance 断層最短距離
	 * @return S波の伝播速度が 600m/sに相当する硬質地盤上における地震動の最大速度振幅 (cm/s)
	 */
	public static double pgv600(final double mw, final double depth, final double faultShortestDistance) {
		return Math.pow(10, mw*0.58d+0.0038d*depth-1.29d-Math.log10(faultShortestDistance+0.0028*Math.pow(10, mw*0.50d))-0.002*faultShortestDistance);
	}

	/**
	 * 地表面での最大速度
	 * @param arv600 地盤増幅度
	 * @param pgv600 S波の伝播速度が 600m /sに相当する硬質地盤上における地震動の最大速度振幅 (cm/s)
	 * @return PGV
	 */
	public static double pgv(final double arv600, final double pgv600) {
		return arv600*pgv600;
	}

	/**
	 * 震度4以上を対象とした計測震度
	 * @param pgv 地表面における最大速度 (cm/s)
	 * @return 計測震度
	 */
	public static double measured(final double pgv) {
		return 2.68d+1.72*Math.log10(pgv);
	}
}
