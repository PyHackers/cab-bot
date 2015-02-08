package com.example.cabbot.utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.support.v4.app.TaskStackBuilder;

import com.example.cabbot.CabApplication;


public enum AppUtil {
	Instance;

	CabApplication application = CabApplication.instance;
	NotificationManager notificationManager = (NotificationManager) application.getSystemService(Context.NOTIFICATION_SERVICE);

	public void showNotification(PendingIntent pendingIntent, String descrption, String title, int smallId) {
		Builder builder = generateNotificationBuilder(pendingIntent, descrption, title, smallId);
		notificationManager.notify(100, builder.build());
	}
	
	private Builder generateNotificationBuilder(PendingIntent intent, String description, String title, int smallIcon) {
		Builder nBuilder= new NotificationCompat.Builder(application);
		nBuilder.setProgress(0, 0, false);
		nBuilder.setAutoCancel(true);
		nBuilder.setContentIntent(intent);
		nBuilder.setWhen(System.currentTimeMillis());
		nBuilder.setSmallIcon(smallIcon);
		nBuilder.setOngoing(true);
		nBuilder.setTicker(description);
		nBuilder.setContentTitle(title);
		nBuilder.setContentText(description);
		return nBuilder;
	}

	public static PendingIntent getNotificationTask(Context context,Intent intent, Class parentActivity) {
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
		if(parentActivity != null) {
			stackBuilder.addParentStack(parentActivity);
		}
		stackBuilder.addNextIntent(intent);
		return stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
	}

}
