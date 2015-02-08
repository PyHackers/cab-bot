package com.example.cabbot.listeners;

import com.example.cabbot.CabApplication;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MyLocationManager implements LocationListener {
	
	public interface Callback {
		public void locationFound(LocationListener locationListerner, Location location);
	}
	
	private Callback callback;
	
	public MyLocationManager(Callback callback) {
		this.callback = callback;
	}

	@Override
	public void onLocationChanged(Location location) {
		if (location != null) {
			if(callback != null) {
				callback.locationFound(this, location);
			}
			Log.d("LOCATION CHANGED", location.getLatitude() + "");
			Log.d("LOCATION CHANGED", location.getLongitude() + "");
			Toast.makeText(CabApplication.instance,
					location.getLatitude() + "" + location.getLongitude(),
					Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public void onProviderDisabled(String arg0) {
	}

	@Override
	public void onProviderEnabled(String arg0) {
	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
	}

}
