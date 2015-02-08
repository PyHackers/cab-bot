package com.example.cabbot.database;

import android.net.Uri;
import android.provider.BaseColumns;

public final class DBContract {
	
	public static final String CONTENT_AUTHORITY = "com.example.cabbot";	// No I18N
	private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
	
	public static final String USERACCOUNT_PATH = "user_account";
	public static final int USERACCOUNT_CODE = 100;
	
	public static final String BOOKING_HISTORY_PATH = "booking_history";
	public static final int BOOKING_HISTORY_CODE = 200;
	
	public static final String ADDRESS_PATH = "address";
	public static final int ADDRESS_CODE = 300;

	public interface UserColumns {
		public final static String USER_ID =  "user_id";
		public final static String USER_NAME =  "user_name";
		public final static String SLACK_TIME = "slack_time";
	}
	
	public static class UserAccount implements UserColumns, BaseColumns {
		public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, USERACCOUNT_PATH);
		public static final String CONTENT_TYPE =
				"vnd.android.cursor.dir/vnd.cabbot.account";	// No I18N
		public static final String CONTENT_ITEM_TYPE =
				"vnd.android.cursor.item/vnd.cabbot.account";	// No I18N
		public static Uri buildAccountUri(String id) {
			return CONTENT_URI.buildUpon().appendPath(id).build();
		}
	}
	
	public interface BookingHistoryColumn {
		public final static String TITLE = "title";
		public final static String TYPE = "type";
		public final static String EVENT_TIME = "event_time";
		public final static String LATITUDE = "latitude";
		public final static String LONGITUDE = "longitude";
		public final static String PICK_UP_POINT = "pick_up_point";
		public final static String BOOKED_TIME = "booked_time";
	}
	
	public static class BookingHistory implements BookingHistoryColumn, BaseColumns {
		public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, BOOKING_HISTORY_PATH);
		public static final String CONTENT_TYPE =
				"vnd.android.cursor.dir/vnd.cabbot.account";	// No I18N
		public static final String CONTENT_ITEM_TYPE =
				"vnd.android.cursor.item/vnd.cabbot.account";	// No I18N
		public static Uri buildBookingHistoryUri(String id) {
			return CONTENT_URI.buildUpon().appendPath(id).build();
		}
	}

	public interface AddressColumns {
		public final static String TITLE = "title";
		public final static String ADDRESS = "address";
		public final static String LATITUDE = "latitude";
		public final static String LONGITUDE = "longitude";
		public final static String FAVORITE = "favorite";
		public final static String TIMES_BOOKED = "times_booked";
	}
	
	public static class Address implements AddressColumns, BaseColumns {
		public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, ADDRESS_PATH);
		public static final String CONTENT_TYPE =
				"vnd.android.cursor.dir/vnd.cabbot.account";	// No I18N
		public static final String CONTENT_ITEM_TYPE =
				"vnd.android.cursor.item/vnd.cabbot.account";	// No I18N
		public static Uri buildAddressUri(String id) {
			return CONTENT_URI.buildUpon().appendPath(id).build();
		}
	}
}
