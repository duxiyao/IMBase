package com.imbase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import org.kymjs.kjframe.utils.FileUtils;

import android.app.Application;
import android.content.Intent;

import com.baidu.mapapi.SDKInitializer;
import com.guard.Watcher;
import com.kjstudy.core.thread.ThreadManager;
import com.kjstudy.core.util.Global;
import com.kjstudy.service.ServiceMainData;

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

        // String executable = "libhelper.so";
        // String aliasfile = "helper";
        // String r = NativeRuntime.getInstance().RunExecutable(
        // getPackageName(),
        // executable,
        // aliasfile,
        // getPackageName()
        // + "/com.qigame.lock.helper.HostMonitor");
        // System.out.println(r);
        // try {
        // NativeRuntime
        // .getInstance()
        // .startService(
        // getPackageName()
        // + "/com.kjstudy.service.ServiceMainData");
        // } catch(Exception e) {
        // e.printStackTrace();
        // }

        startService(new Intent(this, ServiceMainData.class));
         Watcher w = new Watcher(this);
         w.createAppMonitor(String.valueOf(android.os.Process.myUid()));
        // CrashHandler.getInstance().init(getApplicationContext());
        Global.lastLoginUser();
        ThreadManager.getInstance().exeRunnable(new Runnable() {

            @Override
            public void run() {
                try {
                    FileUtils.copyFromAssets(MyApplication.this.getAssets(),
                            "ANProcessGuard", "/data/data/com.imbase/ANProcessGuard");
//                    Watcher w = new Watcher(MyApplication.this);
//                    w.startGuardProcess();
//                     do_exec("ps | grep com.imbase >> /sdcard/a.txt");
//                     do_exec("/sdcard/ANProcessGuard");
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private String do_exec(String cmd) {
        String s = "/n";
        try {
            Process p = Runtime.getRuntime().exec(cmd);
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    p.getInputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                s += line + "/n";
            }
            System.out.println(s);
        } catch(IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return cmd;
    }
}
