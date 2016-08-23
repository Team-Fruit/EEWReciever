package com.bebehp.mc.eewreciever;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

public class ByteUtil {

	public static byte[] joinByte(final byte splitByte, final byte[]... arrays) {
		return joinByte(splitByte, Arrays.asList(arrays));
	}

	public static byte[] joinByte(final byte splitByte, final List<byte[]> array) {
		byte[] joinByte = null;
		final Iterator it = array.iterator();
		while (it.hasNext()) {
			final byte[] line = (byte[])it.next();
			joinByte = ArrayUtils.addAll(joinByte, line);
			if (it.hasNext())
				joinByte = ArrayUtils.addAll(joinByte, splitByte);
		}
		return joinByte;
	}

	public static List<byte[]> splitByte(final byte target, final byte[] array) {
		final List<byte[]> list = new ArrayList<byte[]>();
		final List<Integer> indexList = indexAll(target, array);
		int count = 0;
		final Iterator it = indexList.iterator();
		while (it.hasNext()) {
			final int to = (Integer)it.next();
			list.add(Arrays.copyOfRange(array, count, to));
			count = to+1;
			if (!it.hasNext())
				list.add(Arrays.copyOfRange(array, count, array.length));
		}
		return list;
	}

	public static List indexAll(final byte target, final byte[] array) {
		final List<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < array.length; i++) {
			if (array[i] == target)
				list.add(i);
		}
		return list;
	}

}
