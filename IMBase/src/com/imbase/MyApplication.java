package com.imbase;


import android.app.Application;

public class MyApplication extends Application {
	
	public static MyApplication mInstance;
	
	public static MyApplication getInstance(){
		return mInstance;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		mInstance=this;
//		CrashHandler.getInstance().init(getApplicationContext());
	}
}
