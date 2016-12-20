package net.teamfruit.eewreciever2.common.quake.observation;

import java.util.List;
import java.util.Queue;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Queues;

public enum EnumRegion {
	/*@formatter:off*/
	HOKKAIDO("北海道",EnumPrefecture.HOKKAIDO),
	TOHOKU("東北", EnumPrefecture.AOMORI, EnumPrefecture.IWATE, EnumPrefecture.MIYAGI, EnumPrefecture.AKITA, EnumPrefecture.YAMAGATA, EnumPrefecture.HUKUSHIMA),
	KANTO_KOSHIN("関東・甲信",EnumPrefecture.IBARAKI,EnumPrefecture.TOCHIGI, EnumPrefecture.GUNMA, EnumPrefecture.SAITAMA, EnumPrefecture.CHIBA, EnumPrefecture.TOKYO, EnumPrefecture.KANAGAWA, EnumPrefecture.YAMANASHI, EnumPrefecture.NAGANO),
	HOKURIKU("北陸",EnumPrefecture.NIIGATA, EnumPrefecture.TOYAMA,EnumPrefecture.ISHIKAWA, EnumPrefecture.FUKUI),
	TOKAI("東海",EnumPrefecture.GIFU,EnumPrefecture.SHIZUOKA,EnumPrefecture.AICHI, EnumPrefecture.MIE),
	KINKI("近畿",EnumPrefecture.SHIGA, EnumPrefecture.KYOTO, EnumPrefecture.OSAKA, EnumPrefecture.HYOGO, EnumPrefecture.NARA, EnumPrefecture.WAKAYAMA),
	CHUGOKU("中国", EnumPrefecture.TOTTORI, EnumPrefecture.SHIMANE, EnumPrefecture.OKAYAMA, EnumPrefecture.HIROSHIMA, EnumPrefecture.YAMAGUCHI),
	SHIKOKU("四国", EnumPrefecture.TOKUSHIMA, EnumPrefecture.KAGAWA, EnumPrefecture.EHIME, EnumPrefecture.KOCHI),
	KYUSYU("九州", EnumPrefecture.HUKUOKA, EnumPrefecture.SAGA, EnumPrefecture.NAGASAKI, EnumPrefecture.KUMAMOTO, EnumPrefecture.OITA, EnumPrefecture.MIYAZAKI, EnumPrefecture.KAGOSHIMA, EnumPrefecture.TOSHIMA, EnumPrefecture.KOSHIKI, EnumPrefecture.TANE, EnumPrefecture.YAKU, EnumPrefecture.AMAMI),
	OKINAWA("沖縄", EnumPrefecture.OKINAWA_HONTO, EnumPrefecture.KUME, EnumPrefecture.DAITO, EnumPrefecture.MIYAKO, EnumPrefecture.ISHIGAKI, EnumPrefecture.YONAGUNI, EnumPrefecture.IRIOMOTE),
	TOKYO_ISLANDS("東京島嶼部", EnumPrefecture.IZU, EnumPrefecture.OGASAWARA),
	;
	/*@formatter:on*/
	private final String name;
	private final EnumPrefecture[] prefectures;

	private EnumRegion(final String name, final EnumPrefecture... prefecture) {
		this.name = name;
		this.prefectures = prefecture;
	}

	public String getName() {
		return this.name;
	}

	public EnumPrefecture[] getPrefectures() {
		return this.prefectures;
	}

	@Override
	public String toString() {
		return this.name;
	}

	public static String format(final EnumPrefecture... prefectures) {
		final List<String> list = Lists.newArrayList();
		final Queue<String> tmp = Queues.newArrayDeque();
		for (final EnumPrefecture line2 : prefectures) {
			for (final EnumRegion region : values()) {
				boolean r = true;
				boolean c = false;
				for (final EnumPrefecture line3 : region.getPrefectures()) {
					if (!line2.equals(line3)) {
						r = false;
					} else
						c = true;
				}
				if (c)
					tmp.add(line2.toString());
				if (r) {
					list.add(0, region.toString());
					tmp.clear();
				} else {
					String line4 = null;
					while ((line4 = tmp.poll())!=null)
						list.add(line4);
				}
			}
		}
		return StringUtils.join(list, ' ');
	}
}
