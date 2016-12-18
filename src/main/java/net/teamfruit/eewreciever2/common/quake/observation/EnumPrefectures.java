package net.teamfruit.eewreciever2.common.quake.observation;

public enum EnumPrefectures {
	/*@formatter:off*/
	HOKKAIDO("JP-01", "北海道", 43.063968f, 141.347899f),
	AOMORI("JP-02", "青森", 40.824623f, 140.740593f),
	IWATE("JP-03", "岩手", 39.703531f, 141.152667f),
	MIYAGI("JP-04", "宮城", 38.268839f, 140.872103f),
	AKITA("JP-05", "秋田", 39.718600f, 140.102334f),
	YAMAGATA("JP-06", "山形", 38.240437f, 140.363634f),
	HUKUSHIMA("JP-07", "福島", 37.750299f, 140.467521f),
	IBARAKI("JP-08", "茨城", 36.341813f, 140.446793f),
	TOCHIGI("JP-09", "栃木", 36.565725f, 139.883565f),
	GUNMA("JP-10", "群馬", 36.391208f, 139.060156f),
	SAITAMA("JP-11", "埼玉", 35.857428f, 139.648933f),
	CHIBA("JP-12", "千葉", 35.605058f, 140.123308f),
	TOKYO("JP-13", "東京", 35.689521f, 139.691704f),
	KANAGAWA("JP-14", "神奈川", 35.447753f, 139.642514f),
	NIIGATA("JP-15", "新潟", 37.902418f, 139.023221f),
	TOYAMA("JP-16", "富山", 36.695290f, 137.211338f),
	ISHIKAWA("JP-17", "石川", 36.594682f, 136.625573f),
	FUKUI("JP-18", "福井", 36.065219f, 136.221642f),
	YAMANASHI("JP-19", "山梨", 35.664158f, 138.568449f),
	NAGANO("JP-20", "長野", 36.651289f, 138.181224f),
	GIFU("JP-21", "岐阜",35.391227f, 136.722291f),
	SHIZUOKA("JP-22", "静岡", 34.976978f, 138.383054f),
	AICHI("JP-23", "愛知", 35.180188f, 136.906565f),
	MIE("JP-24", "三重", 34.730283f, 136.508591f),
	SHIGA("JP-25", "滋賀", 35.004531f, 135.868590f),
	KYOTO("JP-26", "京都", 35.021004f, 135.755608f),
	OSAKA("JP-27", "大阪", 34.686297f, 135.519661f),
	HYOGO("JP-28", "兵庫", 34.691279f, 135.183025f),
	NARA("JP-29", "奈良", 34.685333f, 135.832744f),
	WAKAYAMA("JP-30", "和歌山", 34.226034f, 135.167506f),
	TOTTORI("JP-31", "鳥取", 35.503869f, 134.237672f),
	SHIMANE("JP-32", "島根", 35.472297f, 133.050499f),
	OKAYAMA("JP-33", "岡山", 34.661772f, 133.934675f),
	HIROSHIMA("JP-34", "広島", 34.396560f, 132.459622f),
	YAMAGUCHI("JP-35", "山口", 34.186121f, 131.470500f),
	TOKUSHIMA("JP-36", "徳島", 34.065770f, 134.559303f),
	KAGAWA("JP-37", "香川", 34.340149f, 134.043444f),
	EHIME("JP-38", "愛媛", 33.841660f, 132.765362f),
	KOCHI("JP-39", "高知", 33.559705f, 133.531080f),
	HUKUOKA("JP-40", "福岡", 33.606785f, 130.418314f),
	SAGA("JP-41", "佐賀", 33.249367f, 130.298822f),
	NAGASAKI("JP-42", "長崎", 32.744839f, 129.873756f),
	KUMAMOTO("JP-43", "熊本", 32.789828f, 130.741667f),
	OITA("JP-44", "大分", 33.238194f, 131.612591f),
	MIYAZAKI("JP-45", "宮崎", 31.911090f, 131.423855f),
	KAGOSHIMA("JP-46", "鹿児島", 31.560148f, 130.557981f),
	OKINAWA("JP-47", "沖縄", 26.212401f, 127.680932f),

	IZU("JP-13", "伊豆諸島", 34.737492f, 139.400538f),
	OGASAWARA("JP-13", "小笠原諸島", 27.0103987f,142.2091361f),
	AMAMI("JP-46", "奄美諸島", 28.186892f, 129.449791f),
	MIYAKO("JP-47", "宮古島", 24.825225f, 125.302156f),
	YAEYAMA("JP-47", "八重山", 24.343023f, 123.881790f),
	;
	/*@formatter:on*/

	private final String code;
	private final String name;
	private final float lat;
	private final float lon;

	private EnumPrefectures(final String code, final String name, final float lat, final float lon) {
		this.code = code;
		this.name = name;
		this.lat = lat;
		this.lon = lon;
	}

	public double getDistance(final float lat, final float lon, final float depth) {
		return QuakeCalculator.getDistance(this.lat, this.lon, lat, lon, depth);
	}

	public double getDistance(final float lat, final float lon) {
		return getDistance(lat, lon, 0);
	}

	public String getCode() {
		return this.code;
	}

	public String getName() {
		return this.name;
	}

	public float getLat() {
		return this.lat;
	}

	public float getLon() {
		return this.lon;
	}

	@Override
	public String toString() {
		return this.name;
	}
}
