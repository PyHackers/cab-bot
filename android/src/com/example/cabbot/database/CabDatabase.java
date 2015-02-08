package com.example.cabbot.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.cabbot.database.DBContract.Address;
import com.example.cabbot.database.DBContract.BookingHistory;
import com.example.cabbot.database.DBContract.UserAccount;

public class CabDatabase extends SQLiteOpenHelper {
	
	private static String DB_NAME = "Cabbot.db";
	private static int VERSION = 1;

	public CabDatabase(Context context) {
		super(context, DB_NAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table " + DBContract.USERACCOUNT_PATH + "(" 
				+ UserAccount._ID + " integer primary key autoincrement, "
				+ UserAccount.USER_NAME + " text not null, "
				+ UserAccount.USER_ID + " text not null, "
				+ UserAccount.SLACK_TIME + " int not null default 100, "
				+ "unique (" + UserAccount.USER_ID + ") on conflict replace)");
		
		db.execSQL("create table " + DBContract.BOOKING_HISTORY_PATH + "("
				+ BookingHistory._ID + " integer primary key autoincrement, "
				+ BookingHistory.TITLE + " text not null, "
				+ BookingHistory.TYPE + " text, "
				+ BookingHistory.EVENT_TIME + " long not null, "
				+ BookingHistory.LATITUDE + " float not null, "
				+ BookingHistory.LONGITUDE + " float not null, "
				+ BookingHistory.PICK_UP_POINT + " text not null, "
				+ BookingHistory.BOOKED_TIME + " long not null)");
		
		db.execSQL("create table " + DBContract.ADDRESS_PATH + "("
				+ Address._ID + " integer primary key autoincrement, "
				+ Address.TITLE + " text, "
				+ Address.ADDRESS + " text not null, "
				+ Address.LATITUDE + " float not null, "
				+ Address.LONGITUDE + " float not null, "
				+ Address.FAVORITE + " int not null default 0, "
				+ Address.TIMES_BOOKED + " int not null default 0)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

}
