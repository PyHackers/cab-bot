package com.example.cabbot.asycntasks;

import com.example.cabbot.interfaces.AsyncTaskCallback;

import android.os.AsyncTask;

public abstract class AbstractTask<X, Y, Z> extends AsyncTask<X, Y, Z> {
	
	private AsyncTaskCallback callback;
	
	public AbstractTask(AsyncTaskCallback callback) {
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
	protected void onPostExecute(Z result) {
		super.onPostExecute(result);
		if(callback != null) {
			callback.onPostExecute(result);
		}
	}

}
