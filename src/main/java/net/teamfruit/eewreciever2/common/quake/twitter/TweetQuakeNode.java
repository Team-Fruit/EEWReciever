package net.teamfruit.eewreciever2.common.quake.twitter;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;

import javax.annotation.Nullable;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.FastDateFormat;

import net.minecraft.util.EnumChatFormatting;
import net.teamfruit.eewreciever2.common.quake.IQuakeNode;
import net.teamfruit.eewreciever2.common.quake.QuakeEvent;
import net.teamfruit.eewreciever2.common.quake.QuakeEvent.EEWEvent;
import net.teamfruit.eewreciever2.common.quake.QuakeException;
import net.teamfruit.eewreciever2.common.quake.SeismicIntensity;
import net.teamfruit.eewreciever2.common.quake.observation.EnumPrefecture;
import net.teamfruit.eewreciever2.common.quake.observation.EnumRegion;
import net.teamfruit.eewreciever2.common.quake.observation.OvservationPredictor;
import net.teamfruit.eewreciever2.common.quake.observation.SeismicObservationPoints.PointsJson;

/**
 * Twitter@eewbotのパース
 * @author bebe
 * @see <a href="http://d.hatena.ne.jp/Magi/20110403">仕様詳細</a>
 */
public class TweetQuakeNode implements IQuakeNode {
	private static final FastDateFormat dateFormat = FastDateFormat.getInstance("yyyy/MM/dd HH:mm:ss");
	private String raw;

	public String getRaw() {
		return this.raw;
	}

	/**
	 * 35：最大震度のみ。Mの推定なし<br>36、37：最大震度、Mの推定あり<br>39：キャンセル
	 */
	public int code;
	/**
	 * this.code.equals(39)
	 */
	public boolean cancel;
	/**
	 * 訓練報
	 */
	public boolean training;
	/**
	 * EEW発表時刻
	 */
	public Date eewTime;
	/**
	 * キャンセルを誤って発表(地震ID以降の項目は空)
	 */
	public boolean cancelCancellation;
	/**
	 * 最終報
	 */
	public boolean last;
	/**
	 * 電文番号
	 */
	public int number;
	/**
	 * 地震ID
	 */
	public String id;
	/**
	 * 地震発生時刻
	 */
	public Date quakeTime;
	/**
	 * 緯度
	 */
	public float lat;
	/**
	 * 経度
	 */
	public float lon;
	/**
	 * 震央地名
	 */
	public String name;
	/**
	 * 深さ
	 */
	public int depth;
	/**
	 * マグニチュード<br>不明の場合は0
	 */
	public float magnitude;
	/**
	 * 予想最大震度
	 */
	public SeismicIntensity seismic;
	/**
	 * 震源の海陸判定
	 */
	public boolean sea;
	/**
	 * EEW警報
	 */
	public boolean alarm;

	/**
	 * EEW警報の場合、警報地域の文字列が入ります。
	 */
	public String alarmArea;

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public QuakeEvent getEvent() {
		return new EEWEvent(this);
	}

	@Override
	public TweetQuakeNode parseString(final String source) throws QuakeException {
		final String[] tnode = Arrays.copyOf(source.split(","), 15);
		this.code = NumberUtils.toInt(tnode[0]);
		this.cancel = "39".equals(tnode[0]);
		this.training = "01".equals(tnode[1]);
		this.eewTime = getDate(tnode[2]);
		this.cancelCancellation = "7".equals(tnode[3]);
		this.last = "9".equals(tnode[3]);
		this.number = NumberUtils.toInt(tnode[4]);
		this.id = tnode[5];
		this.quakeTime = getDate(tnode[6]);
		this.lat = NumberUtils.toFloat(tnode[7]);
		this.lon = NumberUtils.toFloat(tnode[8]);
		this.name = tnode[9];
		this.depth = NumberUtils.toInt(tnode[10]);
		this.magnitude = NumberUtils.toFloat(tnode[11]);
		this.seismic = SeismicIntensity.fromJP(tnode[12]);
		this.sea = "1".equals(tnode[13]);
		this.alarm = "1".equals(tnode[14]);

		this.raw = source;

		if (this.alarm) {
			final PointsJson json = OvservationPredictor.INSTANCE.getAlarmAreas(this);
			final EnumPrefecture[] prefectures = OvservationPredictor.toPrefectures(json);
			this.alarmArea = EnumRegion.format(prefectures);
		}
		return this;
	}

	private Date getDate(@Nullable final String source) throws QuakeException {
		try {
			if (source!=null)
				return dateFormat.parse(source);
		} catch (final ParseException e) {
			throw new QuakeException("Parse Error", e);
		}
		return null;
	}

	@Override
	public String toString() {
		return getChatFormat();
	}

	@Override
	public String getChatFormat() {
		if (this.cancel)
			return "先ほどの緊急地震速報はキャンセルされました。";
		final StringBuilder sb = new StringBuilder();
		if (this.cancelCancellation)
			sb.append(EnumChatFormatting.RED).append("先ほどのキャンセル報は取り消されました。").append(EnumChatFormatting.RESET).append("\n");
		if (this.training)
			sb.append("[訓練報]");
		if (this.last)
			sb.append(String.format("緊急地震速報(%s) %sで地震 予想震度%s %skm M%s (最終報)",
					this.alarm ? "警報" : "予報",
					this.name,
					this.seismic,
					this.depth,
					this.magnitude));
		else
			sb.append(String.format("緊急地震速報(%s) %sで地震 予想震度%s %skm M%s (第%s報)",
					this.alarm ? "警報" : "予報",
					this.name,
					this.seismic,
					this.depth,
					this.magnitude,
					this.number));
		return sb.toString();
	}

	public String getAlarmArea() {
		final StringBuilder sb = new StringBuilder();
		if (this.alarm) {
			sb.append("\n");
			sb.append(EnumChatFormatting.RED);
			sb.append("次の地域では強い揺れに警戒して下さい :");
			sb.append(this.alarmArea);
			sb.append("\n");
			sb.append(EnumChatFormatting.GRAY);
			sb.append("独自に計算した予測値の為、他と異なる場合があります。");
		}
		return sb.toString();
	}

	@Override
	public boolean canChat() {
		return this.raw!=null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime*result+this.code;
		result = prime*result+((this.eewTime==null) ? 0 : this.eewTime.hashCode());
		result = prime*result+((this.id==null) ? 0 : this.id.hashCode());
		result = prime*result+this.number;
		result = prime*result+((this.raw==null) ? 0 : this.raw.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this==obj)
			return true;
		if (obj==null)
			return false;
		if (!(obj instanceof TweetQuakeNode))
			return false;
		final TweetQuakeNode other = (TweetQuakeNode) obj;
		if (this.code!=other.code)
			return false;
		if (this.eewTime==null) {
			if (other.eewTime!=null)
				return false;
		} else if (!this.eewTime.equals(other.eewTime))
			return false;
		if (this.id==null) {
			if (other.id!=null)
				return false;
		} else if (!this.id.equals(other.id))
			return false;
		if (this.number!=other.number)
			return false;
		if (this.raw==null) {
			if (other.raw!=null)
				return false;
		} else if (!this.raw.equals(other.raw))
			return false;
		return true;
	}
}