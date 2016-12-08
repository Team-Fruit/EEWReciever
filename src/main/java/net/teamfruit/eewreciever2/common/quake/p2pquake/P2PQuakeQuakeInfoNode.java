package net.teamfruit.eewreciever2.common.quake.p2pquake;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.google.common.collect.Maps;
import com.google.gson.JsonParseException;

import net.teamfruit.eewreciever2.common.quake.IQuakeNode;
import net.teamfruit.eewreciever2.common.quake.QuakeEvent;
import net.teamfruit.eewreciever2.common.quake.QuakeException;
import net.teamfruit.eewreciever2.common.quake.SeismicIntensity;
import net.teamfruit.eewreciever2.common.quake.QuakeEvent.QuakeInfoEvent;
import net.teamfruit.eewreciever2.common.quake.p2pquake.P2PQuakeJson.QuakeInfo;
import net.teamfruit.eewreciever2.common.quake.p2pquake.P2PQuakeJson.QuakeInfo.Point;

public class P2PQuakeQuakeInfoNode extends P2PQuakeNode<P2PQuakeJson.QuakeInfo> {
	private static final SimpleDateFormat dateformat2 = new SimpleDateFormat("dd日HH時mm分");
	/**
	 * 発表元を表します。一部発表では値がnullになっていることがあります。
	 */
	public String source;
	/**
	 * 発表種類を表します。
	 */
	public P2PQuakeNodeIssue type;
	/**
	 * 発生日時を表します。
	 */
	public Date time;
	/**
	 * 震源地名を表します。
	 */
	public String name;
	/**
	 * 緯度
	 */
	public float lat;
	/**
	 * 経度
	 */
	public float lon;
	/**
	 * 深さを表します。取得失敗時は「ごく浅い」が入ることがあります。<br>また、「ごく浅い」は0になります。
	 */
	public int depth;
	/**
	 * マグニチュードを表します。取得失敗時は「-1.0」となります。
	 */
	public float magnitude;
	/**
	 * 最大震度を表します。
	 */
	public SeismicIntensity scale;
	/**
	 * 国内への津波の有無を表します。
	 */
	public P2PQuakeNodeTsunami tsunami;
	/**
	 * 震度観測点に関する情報を持ちます。
	 */
	public List<Point> points;

	@Override
	public IQuakeNode parseString(final String source) throws QuakeException {
		try {
			final QuakeInfo data = P2PQuake.gson.fromJson(source, QuakeInfo.class);

			this.date = dateFormat.parse(data.time);
			this.code = data.code;
			this.source = data.issue.source;
			this.type = P2PQuakeNodeIssue.valueOf(data.issue.type.toUpperCase());
			this.time = dateformat2.parse(data.earthquake.time);
			this.name = data.earthquake.hypocenter.name;
			this.lat = NumberUtils.toFloat(StringUtils.substring(data.earthquake.hypocenter.latitude, 1));
			this.lon = NumberUtils.toFloat(StringUtils.substring(data.earthquake.hypocenter.longitude, 1));
			this.depth = StringUtils.equals(data.earthquake.hypocenter.depth, "ごく浅い") ? 0 : NumberUtils.toInt(data.earthquake.hypocenter.depth);
			this.magnitude = NumberUtils.toFloat(data.earthquake.hypocenter.magnitude, -1.0f);
			this.scale = SeismicIntensity.getP2PfromIntensity(data.earthquake.maxScale);
			this.tsunami = EnumUtils.getEnum(P2PQuakeNodeTsunami.class, StringUtils.upperCase(data.earthquake.domesticTsunami));
			this.points = data.points;

			this.data = data;
		} catch (final JsonParseException e) {
			throw new QuakeException("Parse Error", e);
		} catch (final ParseException e) {
			throw new QuakeException("Parse Error", e);
		}
		return this;
	}

	@Override
	public QuakeEvent getEvent() {
		return new QuakeInfoEvent(this);
	}

	@Override
	public String getChatFormat() {
		switch (this.type) {
			case SCALEPROMPT:
				final Map<String, String> map = Maps.newTreeMap(Collections.reverseOrder());
				for (final Point line : this.points) {
					final String scale = SeismicIntensity.getP2PfromIntensity(line.scale).toString();
					final String str = map.get(scale);
					if (str!=null)
						map.put(scale, str+' '+line.addr);
					else
						map.put(scale, line.addr);
				}
				final StringBuilder sb = new StringBuilder();
				int i = 0;
				for (final Entry<String, String> line : map.entrySet()) {
					if (i!=0)
						sb.append(' ');
					sb.append("■震度").append(line.getKey()).append(' ').append(line.getValue());
					i++;
				}
				return String.format("[%s] %tH時%tm分頃 %s", this.type, this.time, this.time, sb);
			case DESTINATION:
				return String.format("[%s] %td日%tH時%tM分頃 震源地は%s 深さ%s%s M%s\n%s",
						this.type,
						this.time,
						this.time,
						this.time,
						this.name,
						this.depth!=0 ? "約" : "",
						this.depth!=0 ? this.depth : "ごく浅い",
						this.magnitude!=-1.0f ? this.magnitude : "不明",
						this.tsunami);
			case SCALEANDDESTINATION:
				return String.format("[%s]【最大震度%s】%s\n深さ%s%s M%s %td日%tH時%tM分頃発生\n%s",
						this.type,
						this.scale,
						this.name,
						this.depth!=0 ? "約" : "",
						this.depth!=0 ? this.depth : "ごく浅い",
						this.magnitude!=-1.0f ? this.magnitude : "不明",
						this.time,
						this.time,
						this.time,
						this.tsunami!=P2PQuakeNodeTsunami.NONE ? this.tsunami : "");
			case FOREGIN:
				return String.format("[%s](%s発表) %s\nM%s %td日%tH時%tM分頃発生(日本時間)\n%s",
						this.type,
						this.source,
						this.name,
						this.magnitude!=-1.0f ? this.magnitude : "不明",
						this.time,
						this.time,
						this.time,
						this.tsunami);
			default:
				return "[EEWReciever2] この情報はサポートされていません。";
		}
	}

	@Override
	public boolean canChat() {
		return super.canChat()&&this.type!=P2PQuakeNodeIssue.DETAILSCALE&&this.type!=P2PQuakeNodeIssue.OTHER;
	}
}
