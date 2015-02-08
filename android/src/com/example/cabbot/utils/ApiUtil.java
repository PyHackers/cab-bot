package com.example.cabbot.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;

import org.json.JSONObject;

import android.util.Log;

import com.example.cabbot.CabApplication;
import com.example.cabbot.R;

public enum ApiUtil {
	Instance;
	
	private String baseUrl;
	
	private ApiUtil() {
		baseUrl = CabApplication.instance.getString(R.string.base_url);
	}
	
	public String getBaseUrl() {
		return baseUrl;
	}
	
	public String getResponse(String baseUrl, String appendUrl, String method, JSONObject json, Object... params) throws ResponseFailureException, MalformedURLException {
		StringBuilder sb = new StringBuilder(baseUrl);
		sb.append(appendUrl);
		String url = String.format(sb.toString(), params);
		return getResponse(new URL(url), method, json);
	}

	public String getResponse(URL url, String method, JSONObject json) throws ResponseFailureException {
		Log.e(getClass().toString(), "Url: " + url);

		try {
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod(method);
			urlConnection.setConnectTimeout(30000);
			urlConnection.setReadTimeout(30000);

			if(json != null) {
				urlConnection.setDoOutput(true);
				urlConnection.setDoInput(true);
			}

			urlConnection.connect();

			int responseCode = urlConnection.getResponseCode();
			if(responseCode == HttpURLConnection.HTTP_OK) {
				return getResponseString(urlConnection.getInputStream());
			}

			throw new ResponseFailureException(
					CabApplication.instance
					.getString(R.string.network_error));
		} catch (ConnectException cte) {
			throw new ResponseFailureException(
					CabApplication.instance.getString(R.string.request_timeout));
		} catch (SocketTimeoutException ste) {
			throw new ResponseFailureException(
					CabApplication.instance.getString(R.string.request_timeout));
		} catch (IOException ioe) {
			throw new ResponseFailureException(
					CabApplication.instance
					.getString(R.string.network_error));
		}
	}

	private String getResponseString(InputStream iStream) throws IOException {
		if (iStream == null) {
			return null;
		}

		BufferedReader reader = null;
		StringBuilder builder = new StringBuilder();

		try {
			reader = new BufferedReader(new InputStreamReader(iStream), 2 * 1024);
			String line = null;
			while ((line = reader.readLine()) != null) {
				builder.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			iStream.close();
			reader.close();
		}
		return builder.toString();
	}


}
