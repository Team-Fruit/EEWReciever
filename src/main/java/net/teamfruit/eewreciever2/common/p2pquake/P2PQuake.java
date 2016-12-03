package net.teamfruit.eewreciever2.common.p2pquake;

import java.util.List;
import java.util.Queue;

import com.google.common.collect.Lists;
import com.google.gson.Gson;

import net.teamfruit.eewreciever2.common.IQuake;
import net.teamfruit.eewreciever2.common.IQuakeNode;
import net.teamfruit.eewreciever2.common.QuakeException;

public class P2PQuake implements IQuake, Runnable {
	public static Gson gson = new Gson();
	public static final String API_PATH = "http://api.p2pquake.com/v1/human-readable?limit=3";

	private static List<IQuakeNode> empty = Lists.newArrayList();

	@Override
	public void run() {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public Queue<IQuakeNode> getQuakeUpdate() throws QuakeException {

		return null;
	}

}
