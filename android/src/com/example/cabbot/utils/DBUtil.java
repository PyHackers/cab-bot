package com.example.cabbot.utils;

import android.database.Cursor;
import android.net.Uri;

import com.example.cabbot.CabApplication;
import com.example.cabbot.database.DBContract.Address;
import com.example.cabbot.database.DBContract.BookingHistory;
import com.example.cabbot.database.DBContract.UserAccount;
import com.example.cabbot.models.ModelInterface;
import com.example.cabbot.models.UserAccountModel;

public enum DBUtil {
	Instance;

	CabApplication application = CabApplication.instance;

	public Uri insertIntoTable(ModelInterface modelInterface) {
		Uri uri = application.getContentResolver().insert(modelInterface.getUri(), modelInterface.toContentValues());
		application.getContentResolver().notifyChange(UserAccount.CONTENT_URI, null);
		application.getContentResolver().notifyChange(BookingHistory.CONTENT_URI, null);
		application.getContentResolver().notifyChange(Address.CONTENT_URI, null);
		return uri;
	}

	public boolean isUserLoggedIn() {
		Cursor cursor = application.getContentResolver().query(UserAccount.CONTENT_URI, null, null, null, null);
		if(cursor != null) {
			cursor.moveToFirst();
			if(!cursor.isAfterLast()) {
				application.setUserAccountModel(UserAccountModel.fromCursor(cursor));
				return true;
			}
		}
		return false;
	}

	public void updateTable(ModelInterface modelInterface, String where, String[] selectionArgs) {
		application.getContentResolver().update(modelInterface.getUri(), modelInterface.toContentValues(), where, selectionArgs);
	}
}
