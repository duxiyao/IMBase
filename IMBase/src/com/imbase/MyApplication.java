package com.imbase;

import org.kymjs.kjframe.utils.ServiceUtil;

import com.baidu.mapapi.SDKInitializer;
import com.kjstudy.core.CrashHandler;
import com.kjstudy.core.util.Global;
import com.kjstudy.service.ServiceMainData;

import android.app.Application;
import android.content.Intent;

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
        startService(new Intent(this, ServiceMainData.class));
//        CrashHandler.getInstance().init(getApplicationContext());
        Global.lastLoginUser();
    }
}
