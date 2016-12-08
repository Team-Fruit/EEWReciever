package net.teamfruit.eewreciever2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

import net.teamfruit.eewreciever2.common.Reference;

public class Debug {

	public static void main(final String[] args) throws Exception {
		final File file = new File("E:/output.txt");
		final BufferedWriter bw = new BufferedWriter(new FileWriter(file));

		final URL url = new URL("http://www.data.jma.go.jp/svd/eqev/data/kyoshin/jma-shindo.html");
		final URLConnection con = url.openConnection();
		final BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));

		String line;
		while ((line = br.readLine())!=null) {
			Reference.logger.info(line);
			if (StringUtils.startsWith(line, "<tr><td>")) {
				line = StringUtils.remove(line, "<tr>");
				line = StringUtils.remove(line, "</tr>");
				line = StringUtils.remove(line, "<td>");
				final String[] array = Arrays.copyOf(line.split("</td>"), 9);
				if (StringUtils.isBlank(array[8])) {
					final StringBuilder sb = new StringBuilder("(\"");
					sb.append(array[0]).append('"').append(',');
					sb.append('"').append(array[1]).append('"').append(',');
					sb.append(ll(array[3], array[4])).append(',');
					sb.append(ll(array[5], array[6]));
					sb.append("),");
					bw.write(sb.toString());
					bw.newLine();
				}
			}
		}
		bw.close();
		Reference.logger.info("Done");
	}

	public static String ll(final String a, final String b) {
		BigDecimal decimal = new BigDecimal(a);
		final String[] array = StringUtils.split(b, ".");
		decimal = decimal.add(new BigDecimal(array[0]).divide(new BigDecimal(60), 3, BigDecimal.ROUND_HALF_UP));
		decimal = decimal.add(new BigDecimal(array[1]).multiply(new BigDecimal(10)).divide(new BigDecimal(3600), 3, BigDecimal.ROUND_HALF_UP));
		return String.valueOf(decimal.floatValue());
	}
}
