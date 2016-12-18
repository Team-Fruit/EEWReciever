package net.teamfruit.eewreciever2.common.quake.observation;

/**
 * 緊急地震速報のデータからの計算用クラスです
 * 計算式に問題がある場合、GithubにPullrequestを送っていただけるとありがたいです。
 * @author bebe, BSC24公式チャットの皆さん
 * @see <a href="http://repository.aitech.ac.jp/dspace/bitstream/11133/2521/1/%E9%98%B2%E7%81%BD4%E5%8F%B7%E7%A0%94%E7%A9%B6%E9%96%8B%E7%99%BA%E3%83%BB%E7%B7%8A%E6%80%A5%E5%9C%B0%E9%9C%87%E9%80%9F%E5%A0%B13(P21-26).pdf">参考文献</a>
 * @see <a href="http://www.data.jma.go.jp/svd/eew/data/nc/katsuyou/reference.pdf">参考文献</a>
 */
public class QuakeCalculator {

	private QuakeCalculator() {
	}

	public static float getMeasured(final float magnitude, final float depth, final float epicenterLat, final float epicenterLon, final float pointLat, final float pointLon) {
		final float mw = magnitude;
		final double distance = angularDistance(epicenterLat, epicenterLon, pointLat, pointLon, depth);
		final double fDistance = faultShortestDistance(distance, faultLength(mw));
		final double pgv600 = pgv600(mw, depth, distance);
		return 0;
	}

	/**
	 * 地理緯度を地心緯度にします
	 * @param geographicalLatitude 地理緯度
	 * @return 地心緯度
	 */
	@Deprecated
	private static double toGeospatialLatitude(final double geographicalLatitude) {
		return Math.atan(geographicalLatitude-Math.toRadians(11.55/60d)*Math.sin(2*geographicalLatitude));
	}

	public static final double A = 6378.137d;

	/**
	 * 震央距離を求めます
	 * @param lat1 震源緯度
	 * @param lon1 震源経度
	 * @param lat2 観測点緯度
	 * @param lon2 観測点経度
	 * @param depth 震源深さ
	 * @return 直線距離(Km)
	 */
	public static double angularDistance(double lat1, double lon1, double lat2, double lon2, final double depth) {
		lat1 = Math.toRadians(lat1);
		lat2 = Math.toRadians(lat2);
		lon1 = Math.toRadians(lon1);
		lon2 = Math.toRadians(lon2);
		final double x1 = Math.cos(lat1)*Math.cos(lon1)*((A-depth)/A);
		final double y1 = Math.cos(lat1)*Math.sin(lon1)*((A-depth)/A);
		final double z1 = Math.sin(lat1)*((A-depth)/A);
		final double x2 = Math.cos(lat2)*Math.cos(lon2);
		final double y2 = Math.cos(lat2)*Math.sin(lon2);
		final double z2 = Math.sin(lat2);
		final double a = Math.acos(x1*x2+y1*y2+z1*z2);
		return Math.acos(x1*x2+y1*y2+z1*z2)*A;
		//		final double ae = Math.cos(lat1)*Math.cos(lon1)*((A-depth)/A);
		//		final double ax = Math.cos(lat2)*Math.cos(lon2);
		//		final double be = Math.cos(lat1)*Math.sin(lon1)*((A-depth)/A);
		//		final double bx = Math.cos(lat2)*Math.sin(lon2);
		//		final double ce = Math.sin(lat1)*((A-depth)/A);
		//		final double cx = Math.sin(lat2);
		//		return Math.sqrt(Math.pow(ae-ax, 2)+Math.pow(be-bx, 2)+Math.pow(ce-cx, 2))*A;
	}

	//ヒュベニの公式
	/*	public static final double A = 6378137.000;
		public static final double E2 = 0.00669438002301188;
		public static final double MNUM = 6335439.32708317;

		*//**
			* 座標間の直線距離を求めます
			* @param lat1
			* @param lon1
			* @param lat2
			* @param lon2
			* @return メートル
			*//*
			public static double getDistanceBetween(final double lat1, final double lon1, final double lat2, final double lon2) {
			final double dx = Math.toRadians(lon1-lon2);
			final double dy = Math.toRadians(lat1-lat2);
			final double my = Math.toRadians((lat1+lat2)/2d);
			final double w = Math.sqrt(1d-E2*Math.pow(Math.sin(my), 2));
			final double n = A/w;
			final double m = MNUM/Math.pow(w, 3);
			return Math.sqrt(Math.pow(dy*m, 2)+Math.pow(dx*n*Math.cos(my), 2));
			}
			*/

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
