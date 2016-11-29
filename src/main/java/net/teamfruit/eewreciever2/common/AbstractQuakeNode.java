package net.teamfruit.eewreciever2.common;

import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;

public abstract class AbstractQuakeNode {

	/**
	 * 識別子<br>
	 * P2PQuakeではupdateTimeと同じ
	 */
	protected String id;
	/**
	 * 更新日時
	 */
	protected Date updateTime;
	/**
	 * 発生日時
	 */
	protected Date time;
	/**
	 * 震度
	 */
	protected SeismicIntensity seismicIntensity;
	/**
	 * 震央
	 */
	protected String where;
	/**
	 * 深さ<br>
	 * ごく浅いは0になります
	 */
	protected int depth;
	/**
	 * マグニチュード
	 */
	protected float magnitude;
	/**
	 * 訓練
	 */
	protected boolean isTraining;
	/**
	 * 緯度
	 */
	protected float lat;
	/**
	 * 経度
	 */
	protected float lng;

	@Override
	public String toString() {
		return this.id;
	}

	public abstract QuakeEvent getEvent();

	public abstract AbstractQuakeNode parseString(String source) throws QuakeException;

	public static List<AbstractQuakeNode> getUpdate(final List<AbstractQuakeNode> older, final List<AbstractQuakeNode> newer) {
		final List<AbstractQuakeNode> list = Lists.newLinkedList(newer);
		list.removeAll(older);
		return list;
	}
}
