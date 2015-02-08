package com.example.cabbot.models;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.example.cabbot.database.DBContract.UserAccount;
import com.example.cabbot.database.DBContract.UserColumns;
import com.example.cabbot.utils.Constants.UserDetailResponse;

public class UserAccountModel implements ModelInterface {

	private static final int DEFAULT_SLACK_VALUE = 10;
	private String username;
	private String userId;
	private int slackTime;

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public int getSlackTime() {
		if(slackTime < DEFAULT_SLACK_VALUE) {
			return DEFAULT_SLACK_VALUE;
		}
		return slackTime;
	}
	
	public void setSlackTime(int slackTime) {
		this.slackTime = slackTime;
	}
	
	public static UserAccountModel fromCursor(Cursor cursor) {
		UserAccountModel userInfo = null;
		if(cursor != null) {
			userInfo = new UserAccountModel();
			userInfo.setUsername(cursor.getString(cursor.getColumnIndex(UserColumns.USER_NAME)));
			userInfo.setUserId(cursor.getString(cursor.getColumnIndex(UserColumns.USER_ID)));
			userInfo.setSlackTime(cursor.getInt(cursor.getColumnIndex(UserColumns.SLACK_TIME)));
		}
		return userInfo;
	}
	
	@Override
	public ContentValues toContentValues() {
		ContentValues contentValues = new ContentValues();
		contentValues.put(UserColumns.USER_ID, getUserId());
		contentValues.put(UserColumns.USER_NAME, getUsername());
		contentValues.put(UserColumns.SLACK_TIME, getSlackTime());
		return contentValues;
	}
	
	public static UserAccountModel fromJSON(JSONObject jsonObject) throws JSONException {
		UserAccountModel userInfo = null;
		if(jsonObject != null) {
			userInfo = new UserAccountModel();
			userInfo.setUsername(jsonObject.getString(UserDetailResponse.USER_NAME));
			userInfo.setUserId(jsonObject.getString(UserDetailResponse.USER_ID));
			userInfo.setSlackTime(jsonObject.optInt(UserDetailResponse.SLACK_TIME));
		}
		return userInfo;
	}
	
	@Override
	public JSONObject toJSON() {
		return null;
	}
	
	@Override
	public Uri getUri() {
		return UserAccount.CONTENT_URI;
	}

}
