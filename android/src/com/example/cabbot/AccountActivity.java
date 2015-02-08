package com.example.cabbot;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.example.cabbot.database.DBContract.UserAccount;
import com.example.cabbot.models.UserAccountModel;
import com.example.cabbot.utils.DBUtil;

public class AccountActivity extends ActionBarActivity {

	@InjectView(R.id.user_name) TextView usernameText;
	@InjectView(R.id.slack_bar) SeekBar slackBar;
	private UserAccountModel userAccountModel;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account);
		
		ButterKnife.inject(this);
		userAccountModel = ((CabApplication) getApplication()).getUserAccountModel();
		setInput(userAccountModel);
		
		slackBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				userAccountModel.setSlackTime(seekBar.getProgress());
				DBUtil.Instance.updateTable(userAccountModel, UserAccount.USER_ID + " = ?", new String[] {userAccountModel.getUserId()});
			}
			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
			}
			@Override
			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
			}
		});
		
		setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
	}

	private void setInput(UserAccountModel userAccountModel) {
		usernameText.setText(userAccountModel.getUsername());
		slackBar.setProgress(userAccountModel.getSlackTime());
	}
}
