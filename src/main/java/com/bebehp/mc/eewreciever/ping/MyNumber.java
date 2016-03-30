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
	public MyNumber(String number) {
		this.number = newDecimal(number);
	}

	public MyNumber(Number number) {
		this(number.toString());
	}

	public BigDecimal getNumber() {
		return number;
	}

	public BigDecimal getNumber(Number defaultnum) {
		return (number != null) ? number : newDecimal(defaultnum.toString());
	}

	@Override
	public String toString() {
		return toString("");
	}

	public String toString(String defaultstr) {
		if (number != null) {
			return number.toString();
		} else {
			return defaultstr;
		}
	}

	public String format(String format) {
		return String.format(format, number);
	}

	public String format(String format, String defaultstr) {
		if (number != null) {
			try {
				return String.format(format, number);
			} catch (IllegalFormatConversionException e) {
			}
		}
		return defaultstr;
	}

	public static BigDecimal newDecimal(String str) {
		if (str == null)
			return null;
		try {
			return new BigDecimal(str);
		} catch (NumberFormatException e) {
			return null;
		}
	}
}
