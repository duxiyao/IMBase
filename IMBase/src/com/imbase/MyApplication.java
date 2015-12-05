package com.imbase;

import org.kymjs.kjframe.utils.ServiceUtil;

import com.baidu.mapapi.SDKInitializer;
import com.guard.Watcher;
import com.kjstudy.core.CrashHandler;
import com.kjstudy.core.util.Global;
import com.kjstudy.service.ServiceMainData;
import com.qigame.lock.helper.NativeRuntime;

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
        
//        String executable = "libhelper.so";
//        String aliasfile = "helper"; 
//        String r = NativeRuntime.getInstance().RunExecutable(
//                getPackageName(),
//                executable,
//                aliasfile,
//                getPackageName()
//                        + "/com.qigame.lock.helper.HostMonitor");
//        System.out.println(r);
//        try {
//            NativeRuntime
//                    .getInstance()
//                    .startService(
//                            getPackageName()
//                                    + "/com.kjstudy.service.ServiceMainData");
//        } catch(Exception e) {
//            e.printStackTrace();
//        }
        

        startService(new Intent(this, ServiceMainData.class));
        Watcher w = new Watcher(this);
        w.createAppMonitor(String.valueOf(android.os.Process.myUid()));
//        CrashHandler.getInstance().init(getApplicationContext());
        Global.lastLoginUser();
    }
}
