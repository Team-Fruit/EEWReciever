package com.bebehp.mc.eewreciever.ping;

import java.math.BigDecimal;
import java.util.IllegalFormatConversionException;

/**
 * 漏えい、ダメ、ゼッタイ
 *
 * @author 被告：bebe0601氏をマイナンバー情報漏えいの疑いで逮捕する。
 */
public class MyNumber {
	private final BigDecimal number;

	/**
	 * 値のない(ヌルヌルな)マイナンバーです(こんなマイナンバーあってたまるかｗｗ)
	 */
	public MyNumber() {
		this.number = null;
	}

	/**
	 * 値が入ったマイナンバー(正常ｗｗ)
	 *
	 * @param number
	 */
	public MyNumber(final String number) {
		this.number = newDecimal(number);
	}

	public MyNumber(final Number number) {
		this(number.toString());
	}

	public BigDecimal getNumber() {
		return this.number;
	}

	public BigDecimal getNumber(final Number defaultnum) {
		return (this.number != null) ? this.number : newDecimal(defaultnum.toString());
	}

	@Override
	public String toString() {
		return toString("");
	}

	public String toString(final String defaultstr) {
		if (this.number != null) {
			return this.number.toString();
		} else {
			return defaultstr;
		}
	}

	public String format(final String format) {
		return String.format(format, this.number);
	}

	public String format(final String format, final String defaultstr) {
		if (this.number != null) {
			try {
				return String.format(format, this.number);
			} catch (final IllegalFormatConversionException e) {
			}
		}
		return defaultstr;
	}

	public static BigDecimal newDecimal(final String str) {
		if (str == null)
			return null;
		try {
			return new BigDecimal(str);
		} catch (final NumberFormatException e) {
			return null;
		}
	}
}
