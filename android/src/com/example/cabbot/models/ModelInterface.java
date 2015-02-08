package com.example.cabbot.models;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.net.Uri;

public interface ModelInterface {
	
//	public T fromCursor(Cursor cursor);
	public ContentValues toContentValues();
//	public T fromJSON(JSONObject jsonObject) throws JSONException;
	public JSONObject toJSON() throws JSONException;
	
	public Uri getUri();

}
