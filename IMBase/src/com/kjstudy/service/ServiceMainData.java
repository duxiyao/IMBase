package com.kjstudy.service;

import org.kymjs.kjframe.KJService;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.ui.ViewInject;
import org.kymjs.kjframe.utils.BroadCastUtil;

import com.kjstudy.bean.EntityT;
import com.kjstudy.bean.data.TSStudentInfo;
import com.kjstudy.bean.data.TSTeacherInfo;
import com.kjstudy.bean.data.TSUserInfo;
import com.kjstudy.core.net.Req;
import com.kjstudy.core.thread.ThreadManager;
import com.kjstudy.core.util.DBUtil;
import com.kjstudy.core.util.Global;
import com.kjstudy.core.util.IntentNameUtil;
import com.kjstudy.core.util.JsonUtil;

import android.content.Intent;
import android.os.IBinder;

public class ServiceMainData extends KJService {

    void r(Runnable r) {
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
        String action = intent.getAction();
        if (IntentNameUtil.SERVICE_ACTION_ON_REQ_STU_TEA_DATA.equals(action)) {
            r(new Runnable() {

                @Override
                public void run() {
                    TSUserInfo m = Global.getCURUSER();
                    if (m == null)
                        return;
                    Req.getStudentInfo(m.getId(), new HttpCallBack() {
                        @Override
                        public void onSuccess(String t) {
                            EntityT<TSStudentInfo> et = JsonUtil.json2ET(t,
                                    TSStudentInfo.class);
                            if (et.getCode() == 0) {
                                TSStudentInfo stu = et.getData();
                                if (!DBUtil.isExists(TSStudentInfo.class,
                                        "ubId=" + stu.getUbId()))
                                    DBUtil.save(stu);
                                else
                                    DBUtil.update(stu);
                        BroadCastUtil
                                .sendBroadCast(IntentNameUtil.ON_ALTER_PERSONAL_INFO_SUCCESS);
                            } else {
                                ViewInject.toast(et.getMsg());
                            }
                        }
                    });

                    Req.getTeacherInfo(m.getId(), new HttpCallBack() {
                        @Override
                        public void onSuccess(String t) {
                            EntityT<TSTeacherInfo> et = JsonUtil.json2ET(t,
                                    TSTeacherInfo.class);
                            if (et.getCode() == 0) {
                                TSTeacherInfo tea = et.getData();

                                if (!DBUtil.isExists(TSTeacherInfo.class,
                                        "ubId=" + tea.getUbId()))
                                    DBUtil.save(tea);
                                else
                                    DBUtil.update(tea);

                            } else {
                                ViewInject.toast(et.getMsg());
                            }
                        }
                    });
                }
            });
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
