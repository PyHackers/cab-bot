package com.example.cabbot;

import java.io.IOException;
import java.util.List;

import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cabbot.models.AddressModel;
import com.example.cabbot.utils.DBUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class DisplayMessageActivity extends FragmentActivity {
	public final static String LOC_CORDS = "com.example.root.sample1.sub_location";
	GoogleMap mmap;
	MarkerOptions markerOptions;
	LatLng latlng;
//	TextView text;
	private static final float DEFAULTZOOM = 15;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

//		text = (TextView) findViewById(R.id.sub_location);

		setContentView(R.layout.activity_display_message);

		if (initmap()) {
			Toast.makeText(this, "Ready to map", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(this, "No map!!", Toast.LENGTH_SHORT).show();
		}

		// Getting reference to btn_find of the layout activity_main
		Button btn_find = (Button) findViewById(R.id.btn_find);

		Button btn_submit = (Button) findViewById(R.id.btn_submit);

		// Defining button click event listener for the find button
		View.OnClickListener findClickListener = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// Getting reference to EditText to get the user input location
				EditText etLocation = (EditText) findViewById(R.id.et_location);

				// Getting user input location
				String location = etLocation.getText().toString();

				if (location != null && !location.equals("")) {
					new GeocoderTask().execute(location);
				}
			}


		};

		btn_find.setOnClickListener(findClickListener);

		btn_submit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				if(latlng != null) {
					String address = ((TextView) findViewById(R.id.et_location)).getText().toString();
					AddressModel model = new AddressModel();
					model.setAddress(address);
					model.setTitle(address);
					model.setLatitude(latlng.latitude);
					model.setLongitude(latlng.longitude);
					DBUtil.Instance.insertIntoTable(model);
					
					finish();
				}
			}
		});

	}

	//    public void sendMessage(View view){
	//        Intent intent = new Intent(this, ConfirmAddressActivity.class);
	//        TextView editText = (TextView) findViewById(R.id.sub_location);
	//        String locCords = editText.getText().toString();
	//        intent.putExtra(LOC_CORDS, locCords);
	//        startActivity(intent);
	//    }

	@Override
	public boolean onCreateOptionsMenu (Menu menu){
		// Inflate the menu; this adds items to the action bar if it is present.
		//		getMenuInflater().inflate(R.menu.menu_display_message, menu);
		return true;
	}


	@Override
	public boolean onOptionsItemSelected (MenuItem item){
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		//		if (id == R.id.action_settings) {
		//			return true;
		//		}

		return super.onOptionsItemSelected(item);
	}

	private boolean initmap() {
		if (mmap == null) {
			MapFragment mapfrag = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
			mmap = mapfrag.getMap();
		}
		return (mmap != null);
	}

	private class GeocoderTask extends AsyncTask<String, Void, List<Address>>{

		@Override
		protected List<Address> doInBackground(String... locationName) {
			// Creating an instance of Geocoder class
			Geocoder geocoder = new Geocoder(getBaseContext());
			List<Address> addresses = null;

			try {
				// Getting a maximum of 3 Address that matches the input text
				addresses = geocoder.getFromLocationName(locationName[0], 3);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return addresses;
		}

		@Override
		protected void onPostExecute(List<Address> addresses) {
//			text = (TextView) findViewById(R.id.sub_location);

			if(addresses==null || addresses.size()==0){
				Toast.makeText(getBaseContext(), "No Location found", Toast.LENGTH_SHORT).show();
			}

			// Clears all the existing markers on the map
			mmap.clear();

			// Adding Markers on Google Map for each matching address
			for(int i=0;i<addresses.size();i++){

				Address address = (Address) addresses.get(i);

				// Creating an instance of GeoPoint, to display in Google Map
				latlng = new LatLng(address.getLatitude(), address.getLongitude());

				String addressText = String.format("%s, %s",
						address.getMaxAddressLineIndex() > 0 ? address.getAddressLine(0) : "",
								address.getCountryName());

				markerOptions = new MarkerOptions();
				markerOptions.position(latlng);
				markerOptions.title(addressText);



				mmap.addMarker(markerOptions);


				// Locate the first location
				if(i==0) {
					mmap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng,DEFAULTZOOM));

				}

//				text.setText(markerOptions.getPosition().toString());

			}
		}
	}

}

