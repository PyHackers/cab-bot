package com.example.cabbot;

import org.json.JSONObject;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.example.cabbot.adapters.BookingHistoryAdapter;
import com.example.cabbot.database.DBContract.BookingHistory;
import com.example.cabbot.models.BookingHistoryModel;
import com.example.cabbot.services.IncomingMessages;
import com.example.cabbot.utils.Constants.BookingDetails;
import com.example.cabbot.utils.DBUtil;

public class MainActivity extends ActionBarActivity implements LoaderCallbacks<Cursor> {

	@InjectView(R.id.booking_history_list) ListView listView;
	CursorAdapter cursorAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		boolean userLoggedIn = DBUtil.Instance.isUserLoggedIn();
		if(!userLoggedIn) {
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
			finish();
		}
		setContentView(R.layout.activity_main);
		
		setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		getSupportActionBar().setHomeButtonEnabled(true);
		
		ButterKnife.inject(this);

		cursorAdapter = new BookingHistoryAdapter(this, null, true);
		listView.setAdapter(cursorAdapter);
		listView.addFooterView(new View(this));

		getSupportLoaderManager().initLoader(1, null, this);
	
		BookingHistoryModel model = new BookingHistoryModel();
		model.setTitle("sdfsdf");
		model.setType("Movie");;
		model.setBookedTime(1441089933l * 1000);
		model.setLatitude(12.9490936d);
		model.setLongitude(77.6443056d);
		model.setEventTime(1441089933l * 1000);
		model.setPickUpPoint("Porur");
		
		BookingHistoryModel secondModel = new BookingHistoryModel();
		secondModel.setTitle("sdfsdf");
		secondModel.setType("Movie");;
		secondModel.setBookedTime(1441089933l * 1000);
		secondModel.setLatitude(12.9490936d);
		secondModel.setLongitude(77.6443056d);
		secondModel.setEventTime(1441089933l * 1000);
		secondModel.setPickUpPoint("Porur");
//		JSONObject = 
		Intent intent = new Intent(this, BookCabActivity.class);
		intent.putExtra(BookingDetails.BOOKING_DETAILS, model);
		intent.putExtra(BookingDetails.SECOND_CAB, secondModel);
//		intent.putExtra(BookingDetails.BOOKING_TIME, 138723047385353l);
//		intent.putExtra(BookingDetails.BOOKING_PLACE, "psdfsdfsdfafalace");
		startActivity(intent);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_account) {
			startPlainIntent(AccountActivity.class);
			return true;
		} else if(id == R.id.action_address) {
			startPlainIntent(AddressActivity.class);
			return true;
		} else if(id == R.id.action_logout) {
			CabApplication.instance.clearAll();
			startPlainIntent(LoginActivity.class);
			finish();
		}
		return super.onOptionsItemSelected(item);
	}

	private void startPlainIntent(Class class1) {
		Intent intent = new Intent(this, class1);
		startActivity(intent);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		CursorLoader loader = new CursorLoader(this, BookingHistory.CONTENT_URI, null, null, null, null);
		return loader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
			cursorAdapter.swapCursor(cursor);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		cursorAdapter.changeCursor(null);
	}
}
