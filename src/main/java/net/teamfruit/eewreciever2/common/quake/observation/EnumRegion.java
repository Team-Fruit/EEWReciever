package net.teamfruit.eewreciever2.common.quake.observation;

import static net.teamfruit.eewreciever2.common.quake.observation.EnumPrefecture.*;

import java.util.List;
import java.util.Queue;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Queues;

public enum EnumRegion {
	/*@formatter:off*/
	HOKKAIDO("北海道",EnumPrefecture.HOKKAIDO),
	TOHOKU("東北", AOMORI, IWATE, MIYAGI, AKITA, YAMAGATA, HUKUSHIMA),
	KANTO_KOSHIN("関東・甲信",IBARAKI,TOCHIGI, GUNMA, SAITAMA, CHIBA, TOKYO, KANAGAWA, YAMANASHI, NAGANO),
	HOKURIKU("北陸",NIIGATA, TOYAMA,ISHIKAWA, FUKUI),
	TOKAI("東海",GIFU,SHIZUOKA,AICHI, MIE),
	KINKI("近畿",SHIGA, KYOTO, OSAKA, HYOGO, NARA, WAKAYAMA),
	CHUGOKU("中国", TOTTORI, SHIMANE, OKAYAMA, HIROSHIMA, YAMAGUCHI),
	SHIKOKU("四国", TOKUSHIMA, KAGAWA, EHIME, KOCHI),
	KYUSYU("九州", HUKUOKA, SAGA, NAGASAKI, KUMAMOTO, OITA, MIYAZAKI, KAGOSHIMA, TOSHIMA, KOSHIKI, TANE, YAKU, AMAMI),
	OKINAWA("沖縄", OKINAWA_HONTO, KUME, DAITO, MIYAKO, ISHIGAKI, YONAGUNI, IRIOMOTE),
	TOKYO_ISLANDS("東京島嶼部", IZU, OGASAWARA),
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
