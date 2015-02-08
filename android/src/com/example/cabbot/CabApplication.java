package com.example.cabbot;

import android.app.Application;

import com.example.cabbot.database.DBContract;
import com.example.cabbot.models.UserAccountModel;


public class CabApplication extends Application {
	
	public static CabApplication instance;
	
	private UserAccountModel userAccountModel;
	
	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
	}

	public void setUserAccountModel(UserAccountModel userAccountModel) {
		this.userAccountModel = userAccountModel;
	}
	
	public UserAccountModel getUserAccountModel() {
		return userAccountModel;
	}

	public void clearAll() {
		userAccountModel = null;
		getContentResolver().delete(DBContract.UserAccount.CONTENT_URI, null, null);
		getContentResolver().delete(DBContract.BookingHistory.CONTENT_URI, null, null);
		getContentResolver().delete(DBContract.Address.CONTENT_URI, null, null);
	}

}
