package com.example.cabbot.models;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.example.cabbot.database.DBContract.Address;

public class AddressModel implements ModelInterface, Serializable {
	
	private String title;
	private String address;
	private double latitude;
	private double longitude;
	private int favorite;
	private int bookedTimes;
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public int isFavorite() {
		return favorite;
	}

	public void setFavorite(int favorite) {
		this.favorite = favorite;
	}

	public int getBookedTimes() {
		return bookedTimes;
	}

	public void setBookedTimes(int bookedTimes) {
		this.bookedTimes = bookedTimes;
	}

	@Override
	public ContentValues toContentValues() {
		ContentValues contentValues = new ContentValues();
		contentValues.put(Address.TITLE, getTitle());
		contentValues.put(Address.ADDRESS, getAddress());
		contentValues.put(Address.LATITUDE, getLatitude());
		contentValues.put(Address.LONGITUDE, getLongitude());
		contentValues.put(Address.FAVORITE, isFavorite());
		contentValues.put(Address.TIMES_BOOKED, getBookedTimes());
		return contentValues;
	}
	
	public static AddressModel fromCursor(Cursor cursor) {
		AddressModel model = null;
		if(cursor != null) {
			model = new AddressModel();
			model.setTitle(cursor.getString(cursor.getColumnIndex(Address.TITLE)));
			model.setAddress(cursor.getString(cursor.getColumnIndex(Address.ADDRESS)));
			model.setLatitude(cursor.getDouble(cursor.getColumnIndex(Address.LATITUDE)));
			model.setLongitude(cursor.getDouble(cursor.getColumnIndex(Address.LONGITUDE)));
			model.setFavorite(cursor.getInt(cursor.getColumnIndex(Address.FAVORITE)));
			model.setBookedTimes(cursor.getInt(cursor.getColumnIndex(Address.TIMES_BOOKED)));
		}
		return model;
	}

	@Override
	public JSONObject toJSON() throws JSONException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri getUri() {
		return Address.CONTENT_URI;
	}

}
