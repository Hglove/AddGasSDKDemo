package com.squid.sdk.addgas.utils;

import android.util.Log;

import com.squid.sdk.addgas.BuildConfig;


public class TLog {
	public static final String LOG_TAG = "Squid";
	public static boolean DEBUG = BuildConfig.DEBUG;

	public TLog() {
	}

	public static final void d(String tile,String log) {
		if (DEBUG)
			Log.d(LOG_TAG, tile+":"+log);
	}

	public static final void e(String tile,String log) {
		if (DEBUG)
			Log.e(LOG_TAG, tile+":" + log);
	}

	public static final void i(String tile,String log) {
		if (DEBUG)
			Log.i(LOG_TAG, tile+":"+log);
	}

	public static final void v(String tile,String log) {
		if (DEBUG)
			Log.v(LOG_TAG, tile+":"+log);
	}

	public static final void w(String tile,String log) {
		if (DEBUG)
			Log.w(LOG_TAG, tile+":"+log);
	}
}
