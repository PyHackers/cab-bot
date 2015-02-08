package com.example.cabbot.adapters;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.example.cabbot.R;
import com.example.cabbot.database.DBContract.BookingHistory;
import com.example.cabbot.models.BookingHistoryModel;

public class BookingHistoryAdapter extends CursorAdapter {

	private LayoutInflater inflator;

	public BookingHistoryAdapter(Context context, Cursor c, boolean autoRequery) {
		super(context, c, autoRequery);
		inflator = LayoutInflater.from(context);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		ViewHolder viewHolder = (ViewHolder) view.getTag();
		
		BookingHistoryModel model = BookingHistoryModel.fromCursor(cursor);
		viewHolder.titleText.setText(model.getTitle());
		viewHolder.pickupPlaceText.setText(model.getPickUpPoint());
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy hh:mm a");
		viewHolder.pickupTimeText.setText(sdf.format(new Date(model.getBookedTime())));
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		View view = inflator.inflate(R.layout.booking_item, parent, false);
		ViewHolder viewHolder = new ViewHolder(view);
		view.setTag(viewHolder);
		return view;
	}
	
	static class ViewHolder {
		@InjectView(R.id.booking_image) ImageView bookingImage;
		@InjectView(R.id.title_text) TextView titleText;
		@InjectView(R.id.event_type_image) ImageView eventTypeImage;
		@InjectView(R.id.pick_up_place_text) TextView pickupPlaceText;
		@InjectView(R.id.pick_up_time_text) TextView pickupTimeText;
		
		public ViewHolder(View view) {
			ButterKnife.inject(this, view);
		}
	}
}
