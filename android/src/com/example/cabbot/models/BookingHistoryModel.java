package com.example.cabbot.models;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.cabbot.database.DBContract.BookingHistory;
import com.example.cabbot.utils.Constants.BookingHistoryResponse;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

public class BookingHistoryModel implements ModelInterface, Serializable {
	
	private String title;
	private String type;
	private long eventTime;
	private double latitude;
	private double longitude;
	private String pickUpPoint;
	private long bookedTime;
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public long getEventTime() {
		return eventTime;
	}

	public void setEventTime(long eventTime) {
		this.eventTime = eventTime;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getPickUpPoint() {
		return pickUpPoint;
	}

	public void setPickUpPoint(String pickUpPoint) {
		this.pickUpPoint = pickUpPoint;
	}

	public long getBookedTime() {
		return bookedTime;
	}

	public void setBookedTime(long bookedTime) {
		this.bookedTime = bookedTime;
	}

	@Override
	public ContentValues toContentValues() {
		ContentValues contentValues = new ContentValues();
		contentValues.put(BookingHistory.TITLE, getTitle());
		contentValues.put(BookingHistory.TYPE, getType());
		contentValues.put(BookingHistory.EVENT_TIME, getEventTime());
		contentValues.put(BookingHistory.LATITUDE, getLatitude());
		contentValues.put(BookingHistory.LONGITUDE, getLongitude());
		contentValues.put(BookingHistory.PICK_UP_POINT, getPickUpPoint());
		contentValues.put(BookingHistory.BOOKED_TIME, getBookedTime());
		return contentValues;
	}

	@Override
	public JSONObject toJSON() throws JSONException {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(BookingHistoryResponse.TITLE, getTitle());
		jsonObject.put(BookingHistoryResponse.TYPE, getType());
		jsonObject.put(BookingHistoryResponse.EVENT_TIME, getEventTime());
		jsonObject.put(BookingHistoryResponse.LATITUDE, getLatitude());
		jsonObject.put(BookingHistoryResponse.LONGITUDE, getLongitude());
		jsonObject.put(BookingHistoryResponse.PICKUP_POINT, getPickUpPoint());
		jsonObject.put(BookingHistoryResponse.BOOKED_TIME, getBookedTime());
		return jsonObject;
	}

	@Override
	public Uri getUri() {
		return BookingHistory.CONTENT_URI;
	}

	public static BookingHistoryModel fromJSON(JSONObject mockingResponse) throws JSONException {
		BookingHistoryModel bookingHistoryModel = null;
		if(mockingResponse != null) {
			bookingHistoryModel = new BookingHistoryModel();
			bookingHistoryModel.setTitle(mockingResponse.optString(BookingHistoryResponse.TITLE));
			bookingHistoryModel.setType(mockingResponse.optString(BookingHistoryResponse.TYPE));
			bookingHistoryModel.setEventTime(mockingResponse.optLong(BookingHistoryResponse.EVENT_TIME));
			bookingHistoryModel.setLatitude((float) mockingResponse.optDouble(BookingHistoryResponse.LATITUDE));
			bookingHistoryModel.setLongitude((float) mockingResponse.optDouble(BookingHistoryResponse.LONGITUDE));
			bookingHistoryModel.setPickUpPoint(mockingResponse.optString(BookingHistoryResponse.PICKUP_POINT));
			bookingHistoryModel.setBookedTime(mockingResponse.optLong(BookingHistoryResponse.BOOKED_TIME));
		}
		return bookingHistoryModel;
	}

	public static BookingHistoryModel fromCursor(Cursor cursor) {
		BookingHistoryModel bookingHistoryModel = null;
		if(cursor != null) {
			bookingHistoryModel = new BookingHistoryModel();
			bookingHistoryModel.setTitle(cursor.getString(cursor.getColumnIndex(BookingHistory.TITLE)));
			bookingHistoryModel.setType(cursor.getString(cursor.getColumnIndex(BookingHistory.TYPE)));
			bookingHistoryModel.setEventTime(cursor.getLong(cursor.getColumnIndex(BookingHistory.EVENT_TIME)));
			bookingHistoryModel.setLatitude((float) cursor.getDouble(cursor.getColumnIndex(BookingHistory.LATITUDE)));
			bookingHistoryModel.setLongitude((float) cursor.getDouble(cursor.getColumnIndex(BookingHistory.LONGITUDE)));
			bookingHistoryModel.setPickUpPoint(cursor.getString(cursor.getColumnIndex(BookingHistory.PICK_UP_POINT)));
			bookingHistoryModel.setBookedTime(cursor.getLong(cursor.getColumnIndex(BookingHistory.BOOKED_TIME)));
		}
		return bookingHistoryModel;
	}
	
	public void changeAddress(AddressModel addressModel) {
		this.latitude = addressModel.getLatitude();
		this.longitude = addressModel.getLongitude();
		this.pickUpPoint = addressModel.getAddress();
	}

}
