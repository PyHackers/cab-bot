package com.example.cabbot.interfaces;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.example.cabbot.CabApplication;
import com.example.cabbot.R;
import com.example.cabbot.models.UserAccountModel;
import com.example.cabbot.utils.ApiUtil;
import com.example.cabbot.utils.Constants;
import com.example.cabbot.utils.DBUtil;
import com.example.cabbot.utils.Constants.UserDetailResponse;

public class LoginTask extends AsyncTask<Bundle, Void, String> {

	private AsyncTaskCallback callback;

	public LoginTask(AsyncTaskCallback callback) {
		this.callback = callback;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		if(callback != null) {
			callback.onPreExecute();
		}
	}

	@Override
	protected String doInBackground(Bundle... params) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		Bundle b = params[0];

		CabApplication application = CabApplication.instance;

		try {
			String response = ApiUtil.Instance.getResponse(application.getString(R.string.base_url), application.getString(R.string.login_url),
					"GET", null, b.getString(UserDetailResponse.USER_NAME), b.getString(UserDetailResponse.PASSWORD));
			
			JSONObject mock = new JSONObject();
			try {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("success", true);
				jsonObject.put("username", "boopathy");
				jsonObject.put("user_id", "sdlkjfkdsjfdsf");
				
				mock.put("results", jsonObject);
			} catch (JSONException e1) {
				e1.printStackTrace();
			}

			JSONObject obj = new JSONObject(mock.toString()).getJSONObject(Constants.RESULTS);
			boolean success = obj.getBoolean(Constants.SUCCESS);

			if(success) {
				UserAccountModel userInfo = UserAccountModel.fromJSON(obj);
				DBUtil.Instance.insertIntoTable(userInfo);
				return "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);

		if(result == null) {
			if(callback != null) {
				callback.onError();
			}
			return;
		}

		if(callback != null) {
			callback.onPostExecute();
		}
	}

}
