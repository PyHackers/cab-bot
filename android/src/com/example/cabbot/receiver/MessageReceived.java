package com.example.cabbot.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.cabbot.services.IncomingMessages;

public class MessageReceived extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		intent.setClass(context, IncomingMessages.class);
		context.startService(intent);
	}

}
