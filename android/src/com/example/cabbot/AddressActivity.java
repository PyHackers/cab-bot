package com.example.cabbot;

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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.example.cabbot.adapters.AddressAdapter;
import com.example.cabbot.database.DBContract.Address;
import com.example.cabbot.models.AddressModel;

public class AddressActivity extends ActionBarActivity implements LoaderCallbacks<Cursor>, OnItemClickListener {
	
	public static final String RESULT_WANTED = "result_wanted";
	
	@InjectView(R.id.booking_history_list) ListView listView;
	CursorAdapter cursorAdapter;

	private int mode;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		ButterKnife.inject(this);
		
		cursorAdapter = new AddressAdapter(this, null, true);
		listView.setAdapter(cursorAdapter);
		listView.addFooterView(new View(this));
		
		listView.setOnItemClickListener(this);
		
		mode = getIntent().getIntExtra(RESULT_WANTED, 0);

		getSupportLoaderManager().initLoader(1, null, this);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.address, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case R.id.action_new_address:
			Intent intent = new Intent(this, DisplayMessageActivity.class);
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		CursorLoader loader = new CursorLoader(this, Address.CONTENT_URI, null, null, null, null);
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

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
		if(mode == 1) {
			Intent data = new Intent();
			Cursor cursor = (Cursor) adapterView.getItemAtPosition(position);
			AddressModel model = AddressModel.fromCursor(cursor);
			data.putExtra("data", model);
			setResult(RESULT_OK, data);
			finish();
		}
	}
}
