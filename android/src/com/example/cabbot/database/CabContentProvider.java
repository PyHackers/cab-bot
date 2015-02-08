package com.example.cabbot.database;

import com.example.cabbot.database.DBContract.Address;
import com.example.cabbot.database.DBContract.BookingHistory;
import com.example.cabbot.database.DBContract.UserAccount;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class CabContentProvider extends ContentProvider {

	private static UriMatcher uriMatcher = buildUriMatcher();

	private static android.content.UriMatcher buildUriMatcher() {
		UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(DBContract.CONTENT_AUTHORITY, DBContract.USERACCOUNT_PATH, DBContract.USERACCOUNT_CODE);
		uriMatcher.addURI(DBContract.CONTENT_AUTHORITY, DBContract.BOOKING_HISTORY_PATH, DBContract.BOOKING_HISTORY_CODE);
		uriMatcher.addURI(DBContract.CONTENT_AUTHORITY, DBContract.ADDRESS_PATH, DBContract.ADDRESS_CODE);
		return uriMatcher;
	}

	private CabDatabase mOpenHelper;

	@Override
	public int delete(Uri uri, String where, String[] selectionArgs) {
		final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		int count = db.delete(getTable(uri), where, selectionArgs);
		return count;
	}

	private String getTable(Uri uri) {
		final int match = uriMatcher.match(uri);
		switch (match) {
		case DBContract.USERACCOUNT_CODE:
			return DBContract.USERACCOUNT_PATH;
		case DBContract.BOOKING_HISTORY_CODE:
			return DBContract.BOOKING_HISTORY_PATH;
		case DBContract.ADDRESS_CODE:
			return DBContract.ADDRESS_PATH;
		default:
			throw new UnsupportedOperationException();
		}
	}

	@Override
	public String getType(Uri uri) {
		final int match = uriMatcher.match(uri);
		switch (match) {

		case DBContract.USERACCOUNT_CODE:
			return UserAccount.CONTENT_TYPE;

		case DBContract.BOOKING_HISTORY_CODE:
			return BookingHistory.CONTENT_TYPE;

		case DBContract.ADDRESS_CODE:
			return Address.CONTENT_TYPE;

		default:
			throw new UnsupportedOperationException("Unknown uri: " + uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues contentValues) {
		SQLiteDatabase writableDatabase = mOpenHelper.getWritableDatabase();

		Uri childUri = null;
		final int match = uriMatcher.match(uri);
		switch (match) {
		case DBContract.USERACCOUNT_CODE:
			long id = writableDatabase.insertOrThrow(DBContract.USERACCOUNT_PATH, null, contentValues);
			childUri = UserAccount.buildAccountUri(String.valueOf(id));
			break;

		case DBContract.BOOKING_HISTORY_CODE:
			id = writableDatabase.insertOrThrow(DBContract.BOOKING_HISTORY_PATH, null, contentValues);
			childUri = BookingHistory.buildBookingHistoryUri(String.valueOf(id));
			break;

		case DBContract.ADDRESS_CODE:
			id = writableDatabase.insertOrThrow(DBContract.ADDRESS_PATH, null, contentValues);
			childUri = Address.buildAddressUri(String.valueOf(id));
			break;

		default:
			break;
		}

		writableDatabase.close();

		return childUri;
	}

	@Override
	public boolean onCreate() {
		final Context context = getContext();
		mOpenHelper = new CabDatabase(context);
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
			String sortBy) {
		SQLiteDatabase readableDatabase = mOpenHelper.getReadableDatabase();

		Cursor cursor = null;
		int match = uriMatcher.match(uri);
		switch(match) {
		case DBContract.USERACCOUNT_CODE:
			cursor = readableDatabase.query(DBContract.USERACCOUNT_PATH, projection, selection, selectionArgs, null, null, sortBy);
			break;

		case DBContract.BOOKING_HISTORY_CODE:
			cursor = readableDatabase.query(DBContract.BOOKING_HISTORY_PATH, projection, selection, selectionArgs, null, null, sortBy);
			break;

		case DBContract.ADDRESS_CODE:
			cursor = readableDatabase.query(DBContract.ADDRESS_PATH, projection, selection, selectionArgs, null, null, sortBy);
			break;
		}
		if (cursor != null) {
			cursor.setNotificationUri(getContext().getContentResolver(), uri);
		}
		return cursor;
	}

	@Override
	public int update(Uri uri, ContentValues contentValue, String where, String[] selectionArgs) {
		SQLiteDatabase writableDatabase = mOpenHelper.getWritableDatabase();

		int update = 0;
		final int match = uriMatcher.match(uri);
		switch (match) {
		case DBContract.USERACCOUNT_CODE:
			update = writableDatabase.update(DBContract.USERACCOUNT_PATH, contentValue, where, selectionArgs);
			break;
		}
		writableDatabase.close();
		return update;
	}


}
