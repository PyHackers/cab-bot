package com.example.cabbot.services;

import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.util.Log;

import com.example.cabbot.BookCabActivity;
import com.example.cabbot.CabApplication;
import com.example.cabbot.R;
import com.example.cabbot.listeners.MyLocationManager;
import com.example.cabbot.models.BookingHistoryModel;
import com.example.cabbot.models.UserAccountModel;
import com.example.cabbot.utils.ApiUtil;
import com.example.cabbot.utils.Constants;
import com.example.cabbot.utils.Constants.BookingDetails;

public class IncomingMessages extends IntentService {

	private static String name = "com.example.cabbot.services.IncomingMessages";
	private boolean locationFound = false;

	public IncomingMessages() {
		super(name);
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		final LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		LocationListener locationListener = new MyLocationManager(new MyLocationManager.Callback() {
			@Override
			public void locationFound(LocationListener lListener, Location location) {
				Log.e(getClass().toString(), "location found.. removing listener.." + location.getLatitude() + " : " + location.getLongitude());
				locationManager.removeUpdates(lListener);
				if(!locationFound) {
					sendingRequest(location.getLatitude(), location.getLongitude());
				}
				locationFound = true;
			}
		});
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		if(!locationFound) {
			locationFound = true;
			locationManager.removeUpdates(locationListener);
			sendingRequest(12.9490936d, 77.6443056d);
		}
	}

	protected void sendingRequest(double lat, double lon) {
		UserAccountModel userAccountModel = CabApplication.instance.getUserAccountModel();
		if(userAccountModel != null) {
			Log.e(getClass().toString(), "Getting the message..");
			Uri inboxURI = Uri.parse("content://sms/inbox");
			Cursor cursor = getContentResolver().query(inboxURI, null, 
					null, null, "date desc limit 1");
			Log.e(getClass().toString(), "Num of message: " + cursor.getCount());

			JSONObject parseMessage = new JSONObject();
			cursor.moveToFirst();
			while(!cursor.isAfterLast()) {
				try {
					String encodedSubject = cursor.getString(cursor.getColumnIndex("body"));
					encodedSubject = URLEncoder.encode(encodedSubject, "UTF-8"); // No I18N

					String response = ApiUtil.Instance.getResponse(getString(R.string.base_url), getString(R.string.parse), "GET", null, 
							lat, lon, userAccountModel.getSlackTime(), encodedSubject);

					Log.e(getClass().toString(), "sending the request..." + parseMessage);

					JSONObject responseObj = new JSONObject(response).getJSONObject(Constants.RESULTS);
					String secondBookTitle = responseObj.getString(BookingDetails.SECOND_TITLE);
					JSONArray msg = responseObj.getJSONArray("msg");
					int length = msg.length();

					JSONObject secondJSON = null;
					if(length == 2) {
						secondJSON = (JSONObject) msg.get(1);
					}

					JSONObject json = (JSONObject) msg.get(0);

					BookingHistoryModel model = BookingHistoryModel.fromJSON(json);
					BookingHistoryModel secondModel = BookingHistoryModel.fromJSON(secondJSON);
					Log.e(getClass().toString(), "after receiving the request...");

					Intent intents = new Intent(IncomingMessages.this, BookCabActivity.class);
					intents.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					intents.putExtra(BookingDetails.BOOKING_DETAILS, model);
					intents.putExtra(BookingDetails.SECOND_CAB, secondModel);
					intents.putExtra(BookingDetails.SECOND_TITLE, secondBookTitle);
					startActivity(intents);
				} catch (Exception e) {
					e.printStackTrace();
				}
				cursor.moveToNext();
			}
		}		
	}

}
