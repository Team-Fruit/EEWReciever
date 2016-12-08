package net.teamfruit.eewreciever2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;

import net.teamfruit.eewreciever2.common.Reference;
import net.teamfruit.eewreciever2.common.util.Downloader;

public class Debug {

	public static void main(final String[] args) throws Exception {
		try {
			final File file = new File("E:/output.txt");
			final BufferedWriter bw = new BufferedWriter(new FileWriter(file));

			final HttpGet get = new HttpGet("http://www.data.jma.go.jp/svd/eqev/data/kyoshin/jma-shindo.html");
			final HttpResponse res = Downloader.downloader.getClient().execute(get);
			final HttpEntity entity = res.getEntity();
			final BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));

			int i = 0;
			String temp = null;
			String line;
			while ((line = br.readLine())!=null) {
				if (StringUtils.startsWith(line, "<tr><td>")) {
					line = StringUtils.remove(line, "<tr>");
					line = StringUtils.remove(line, "</tr>");
					line = StringUtils.remove(line, "<td>");
					final String[] array = Arrays.copyOf(line.split("</td>"), 9);
					if (StringUtils.isBlank(array[8])) {
						if (!array[0].equals(temp)) {
							if (temp!=null)
								bw.newLine();
							bw.write(array[0]);
							bw.newLine();
							temp = array[0];
						}
						final String lat = ll(array[3], array[4]);
						final String lon = ll(array[5], array[6]);

						final HttpGet get2 = new HttpGet(String.format("http://www.j-shis.bosai.go.jp/map/api/sstrct/V3/meshinfo.geojson?position=%s,%s&epsg=4612", lon, lat));
						final HttpResponse res2 = Downloader.downloader.getClient().execute(get2);
						final HttpEntity entity2 = res2.getEntity();
						final BufferedReader br2 = new BufferedReader(new InputStreamReader(entity2.getContent(), "UTF-8"));
						final String line2 = br2.readLine();
						final String avs = StringUtils.substringBefore(StringUtils.substringAfter(line2, "AVS\":\""), "\",\"ARV");
						final String arv = StringUtils.substringBefore(StringUtils.substringAfter(line2, "ARV\":\""), "\",\"JCODE");
						br2.close();

						final StringBuilder sb = new StringBuilder("(\"");
						sb.append(array[1]).append('"').append(',');
						sb.append(lat).append(',');
						sb.append(lon).append(',');
						sb.append(avs).append(',');
						sb.append(arv).append("),");
						Reference.logger.info(sb.toString());

						bw.write(sb.toString());
						bw.newLine();

						bw.flush();

						i++;
						Reference.logger.info("Progress {}", i);
						Thread.sleep(1000*5);
					}
				}
			}
			br.close();
			bw.close();
			Reference.logger.info("Done");
		} catch (final Exception e) {
			Reference.logger.error(e.getMessage(), e);
		}
	}

	public static String ll(final String a, final String b) {
		BigDecimal decimal = new BigDecimal(a);
		final String[] array = StringUtils.split(b, ".");
		decimal = decimal.add(new BigDecimal(array[0]).divide(new BigDecimal(60), 4, BigDecimal.ROUND_HALF_UP));
		decimal = decimal.add(new BigDecimal(array[1]).multiply(new BigDecimal(10)).divide(new BigDecimal(3600), 4, BigDecimal.ROUND_HALF_UP));
		return String.valueOf(decimal.floatValue());
	}
}
