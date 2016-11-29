package net.teamfruit.eewreciever2.common;

import java.util.Date;

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
}
