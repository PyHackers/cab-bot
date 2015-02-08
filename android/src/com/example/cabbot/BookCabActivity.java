package com.example.cabbot;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.example.cabbot.models.AddressModel;
import com.example.cabbot.models.BookingHistoryModel;
import com.example.cabbot.utils.AppUtil;
import com.example.cabbot.utils.Constants.BookingDetails;
import com.example.cabbot.utils.Constants.BookingHistoryResponse;
import com.example.cabbot.utils.DBUtil;

public class BookCabActivity extends FragmentActivity {

	@InjectView(R.id.booking_date_text) TextView bookingDateText;
	@InjectView(R.id.booking_time_text) TextView bookingTimeText;
	@InjectView(R.id.booking_place_text) TextView bookingPlaceText;
	@InjectView(R.id.second_cab) CheckBox secondCabCheck;

	@InjectView(R.id.remind_me_later) View remindMeLaterView;
	@InjectView(R.id.cancel) View cancelView;
	@InjectView(R.id.book) View bookView;

	private BookingHistoryModel bookingModel;
	private BookingHistoryModel secondCab;
	private String secondCabTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_book_cab);
		ButterKnife.inject(this);

		Intent intent = getIntent();
		bookingModel = (BookingHistoryModel) intent.getSerializableExtra(BookingDetails.BOOKING_DETAILS);
		secondCab = (BookingHistoryModel) intent.getSerializableExtra(BookingDetails.SECOND_CAB);

		secondCabTitle = intent.getStringExtra(BookingDetails.SECOND_TITLE);
		init();

		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		int width = (int) (displaymetrics.widthPixels * 0.95);
		WindowManager.LayoutParams params = getWindow().getAttributes();
		params.width = width;
		params.height = WindowManager.LayoutParams.WRAP_CONTENT;
		getWindow().setAttributes(params);
	}

	private void init() {
		((TextView) findViewById(R.id.heading_name)).setText(bookingModel.getTitle());

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date(bookingModel.getBookedTime());
		bookingDateText.setText(sdf.format(date));

		sdf = new SimpleDateFormat("hh:mm:ss a");
		bookingTimeText.setText(sdf.format(date));
		bookingPlaceText.setText(bookingModel.getPickUpPoint());

		if(secondCab != null) {
			secondCabCheck.setEnabled(true);
		}
		
		if(secondCabTitle != null) {
			secondCabCheck.setText(secondCabTitle);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == RESULT_OK) {
			switch (requestCode) {
			case 100:
				AddressModel model = (AddressModel) data.getSerializableExtra("data");
				bookingModel.changeAddress(model);
				init();
				break;

			default:
				break;
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}	

	@OnClick(R.id.booking_place_text)
	public void changeAddress() {
		Intent intent = new Intent(this, AddressActivity.class);
		intent.putExtra(AddressActivity.RESULT_WANTED, 1);
		startActivityForResult(intent, 100);
	}

	@OnClick(R.id.cancel) 
	public void cancelBooking() {
		finish();
	}

	@OnClick(R.id.remind_me_later) 
	public void remindMeLater() {
		Intent intent = new Intent(getIntent());
		intent.setClass(this, BookCabActivity.class);
		AppUtil.Instance.showNotification(AppUtil.getNotificationTask(this, intent, null), 
				"Descrption", "Title", R.drawable.ic_launcher);
		finish();
	}

	@OnClick(R.id.book) 
	public void bookCab() {
		Toast.makeText(this, "Cab booked", Toast.LENGTH_SHORT).show();
		//		try {
		//			JSONObject mockingResponse = new JSONObject();
		//			mockingResponse.put(BookingHistoryResponse.TITLE, "sample");
		//			mockingResponse.put(BookingHistoryResponse.TYPE, "Movie");
		//			mockingResponse.put(BookingHistoryResponse.EVENT_TIME, 1423211640000l);
		//			mockingResponse.put(BookingHistoryResponse.LATITUDE, 345.3f);
		//			mockingResponse.put(BookingHistoryResponse.LONGITUDE, 234f);
		//			mockingResponse.put(BookingHistoryResponse.PICKUP_POINT, "Porur");
		//			mockingResponse.put(BookingHistoryResponse.BOOKED_TIME, 1423211640000l);
		//			
		//			Log.e(getClass().toString(), "mock response: " + mockingResponse);
		//			
		//			BookingHistoryModel bookingHistoryModel = BookingHistoryModel.fromJSON(mockingResponse);
		DBUtil.Instance.insertIntoTable(bookingModel);

		if(secondCabCheck.isEnabled() && secondCabCheck.isChecked()) {
			DBUtil.Instance.insertIntoTable(secondCab);	
		}
		//		} catch (JSONException e) {
		//			e.printStackTrace();
		//		}
		finish();
	}

}
