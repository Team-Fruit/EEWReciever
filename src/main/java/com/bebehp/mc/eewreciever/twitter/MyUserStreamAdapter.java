package com.bebehp.mc.eewreciever.twitter;

import com.bebehp.mc.eewreciever.EEWRecieverMod;

import twitter4j.Logger;
import twitter4j.Status;
import twitter4j.UserStreamAdapter;

public class MyUserStreamAdapter extends UserStreamAdapter
{
	private static final Logger logger = Logger.getLogger(MyUserStreamAdapter.class);

	@Override
	public void onStatus(Status status)
	{
		super.onStatus(status);
		// テスト
		EEWRecieverMod.sendServerChat(status.getText());
		logger.info("sushi");
	}
}
