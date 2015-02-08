package com.example.cabbot;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.example.cabbot.interfaces.AsyncTaskCallback;
import com.example.cabbot.interfaces.LoginTask;
import com.example.cabbot.utils.Constants.UserDetailResponse;

public class LoginActivity extends ActionBarActivity implements AsyncTaskCallback {
	
	@InjectView(R.id.username) TextView usernameTextView;
	@InjectView(R.id.password) TextView passwordTextView;
	@InjectView(R.id.login) View loginView;
	private ProgressDialog pg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		ButterKnife.inject(this);
		
//		Intent intent = new Intent(this, BookCabActivity.class);
//		intent.putExtra(BookingDetails.BOOKING_DATE, "date");
//		intent.putExtra(BookingDetails.BOOKING_TIME, 138723047385353l);
//		intent.putExtra(BookingDetails.BOOKING_PLACE, "psdfsdfsdfafalace");
//		startActivity(intent);
	}
	
	@OnClick(R.id.login) 
	public void login() {
		String username = usernameTextView.getText().toString();
		String password = passwordTextView.getText().toString();
		if(username.equals("") || password.equals("")) {
			Toast.makeText(this, "Username or password should not be null", Toast.LENGTH_SHORT).show();
			return;
		}
		
		LoginTask loginTask = new LoginTask(this);
		Bundle b = new Bundle();
		b.putString(UserDetailResponse.USER_NAME, username);
		b.putString(UserDetailResponse.PASSWORD, password);
		loginTask.execute(b);
	}

	@Override
	public void onPreExecute() {
		pg = ProgressDialog.show(this, "Login", "Please wait...");
	}

	@Override
	public void onPostExecute(Object... result) {
		pg.cancel();
		
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		finish();
	}

	@Override
	public void onError(Object... result) {
		pg.cancel();
		Toast.makeText(this, "Error in login", Toast.LENGTH_SHORT).show();
	}
}
