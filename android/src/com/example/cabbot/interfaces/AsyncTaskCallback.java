package com.example.cabbot.interfaces;

public interface AsyncTaskCallback {
	
	public void onPreExecute();
	public void onPostExecute(Object... result);
	public void onError(Object... result);

}
