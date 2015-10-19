package com.imbase;

import com.baidu.mapapi.SDKInitializer;

import android.app.Application;

public class MyApplication extends Application {

	public static MyApplication mInstance;

	public static MyApplication getInstance() {
		return mInstance;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;
		SDKInitializer.initialize(this);
		// CrashHandler.getInstance().init(getApplicationContext());
	}
}
