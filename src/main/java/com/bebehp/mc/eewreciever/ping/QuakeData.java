package com.bebehp.mc.eewreciever.ping;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.bebehp.mc.eewreciever.EEWRecieverMod;

public class QuakeData {

	public final LinkedList<QuakeNode> data;

	public QuakeData(LinkedList<QuakeNode> data)
	{
		this.data = data;
	}

	public static List<QuakeNode> getUpdate(QuakeData older, QuakeData newer)
	{
		LinkedList<QuakeNode> o = older.data;
		LinkedList<QuakeNode> n = newer.data;
		LinkedList<QuakeNode> update = new LinkedList<QuakeNode>();
		Collections.copy(update, n);
		update.removeAll(o);
		return update;
	}
	
	public static void main(String[] args) throws QuakeException
	{
		IAPIPath checker = new APIPathP2PQUAKE();
		QuakeData buf = checker.getQuake();
		
		EEWRecieverMod.logger.info(buf);
//		SimpleDateFormat format = new SimpleDateFormat("M/d");
//		EEWRecieverMod.logger.info(format.format(new Date()));
	}
}