package com.example.cabbot.utils;

public class Constants {
	
	public interface MessageRequest {
		public static final String ADDRESS = "address";
		public static final String MSG = "msg";
		public static final String SLACK_TIME = "slack";
		public static final String LON = "long";
		public static final String LAT = "lat";
	}
	
	public interface BookingDetails  {
//		public static final String BOOKING_DATE = "booking_date";
//		public static final String BOOKING_TIME = "booking_time";
//		public static final String BOOKING_PLACE = "booking_place";
//		public static final String BOOKING_TITLE = "booking_title";
		public static final String SECOND_TITLE = "second_title";
		public static final String BOOKING_DETAILS = "booking_details";
		public static final String SECOND_CAB = "second_cab";
	}
	
	public interface UserDetailResponse {
		public static final String USER_NAME = "username";
		public static final String PASSWORD = "password";
		public static final String USER_ID = "user_id";
		public static final String SLACK_TIME = "slack_time";
	}
	
	public interface BookingHistoryResponse {
		public static final String TITLE = "title";
		public static final String TYPE = "type";
		public static final String EVENT_TIME = "event_type";
		public static final String LATITUDE = "latitude";
		public static final String LONGITUDE = "longitude";
		public static final String PICKUP_POINT = "pickup_point";
		public static final String BOOKED_TIME = "booked_time";
	}

	public static final String RESULTS = "results";
	public static final String SUCCESS = "success";
	public static final String USER_ID = "user_id";

}
