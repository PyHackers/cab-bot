package com.example.cabbot.adapters;

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
import com.example.cabbot.adapters.BookingHistoryAdapter.ViewHolder;
import com.example.cabbot.database.DBContract.Address;
import com.example.cabbot.models.AddressModel;

public class AddressAdapter extends CursorAdapter {

	private LayoutInflater inflator;

	public AddressAdapter(Context context, Cursor c, boolean autoRequery) {
		super(context, c, autoRequery);
		inflator = LayoutInflater.from(context);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		ViewHolder vh = (ViewHolder) view.getTag();
		
		AddressModel model = AddressModel.fromCursor(cursor);
		vh.titleText.setText(model.getTitle());
		vh.addressText.setText(model.getAddress());
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		View view = inflator.inflate(R.layout.address_item, parent, false);
		ViewHolder vh = new ViewHolder(view);
		view.setTag(vh);
		return view;
	}
	
	static class ViewHolder {
		@InjectView(R.id.favorite_image) ImageView favImage;
		@InjectView(R.id.title_text) TextView titleText;
		@InjectView(R.id.address_text) TextView addressText;
		
		public ViewHolder(View view) {
			ButterKnife.inject(this, view);
		}
	}

}
