package com.kjstudy.service;

import org.kymjs.kjframe.KJService;

import com.kjstudy.core.thread.ThreadManager;
import com.kjstudy.core.util.IntentNameUtil;

import android.content.Intent;
import android.os.IBinder;

public class ServiceMainData extends KJService{

    void r(Runnable r){
        ThreadManager.getInstance().exeRunnable(r);
    }
    
    @Override
    public void onCreate() {
        super.onCreate();
        setFilters(IntentNameUtil.SERVICE_ACTION_ON_REQ_STU_TEA_DATA);
    }
    
    @Override
    protected void dealBroadcase(Intent intent) {
        super.dealBroadcase(intent);
        String action=intent.getAction();
        if(IntentNameUtil.SERVICE_ACTION_ON_REQ_STU_TEA_DATA.equals(action)){
            r(new Runnable() {
                
                @Override
                public void run() {
//                    Req.
                }
            });
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
