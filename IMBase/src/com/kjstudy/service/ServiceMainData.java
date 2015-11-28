package com.kjstudy.service;

import org.kymjs.kjframe.KJService;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.ui.ViewInject;

import android.content.Intent;
import android.os.IBinder;

import com.kjstudy.bean.ETSUserInfo;
import com.kjstudy.bean.EntityT;
import com.kjstudy.bean.data.TSStudentInfo;
import com.kjstudy.bean.data.TSTeacherInfo;
import com.kjstudy.bean.data.TSUserInfo;
import com.kjstudy.core.net.Req;
import com.kjstudy.core.thread.ThreadManager;
import com.kjstudy.core.util.BroadCastUtil;
import com.kjstudy.core.util.DBUtil;
import com.kjstudy.core.util.Global;
import com.kjstudy.core.util.IntentNameUtil;
import com.kjstudy.core.util.JsonUtil;

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
                    Req.getBasicInfo(m.getId(), new HttpCallBack() {
                        @Override
                        public void onSuccess(String t) {
                            ETSUserInfo user = JsonUtil.json2Obj(t,
                                    ETSUserInfo.class);
                            if (user != null && user.getCode() == 0
                                    && user.getData() != null) {
                                TSUserInfo u = user.getData();
                                if (!DBUtil.getDB().isExists(TSUserInfo.class,
                                        "id=" + u.getId())) {
                                    DBUtil.save(u);
                                } else {
                                    DBUtil.update(u, "id=" + u.getId());
                                }
                                if(u.getId()!=-1)
                                    Global.setCURUSER(String.valueOf(u.getId()));
                            } else {
                                if (user != null)
                                    ViewInject.toast(user.getMsg());
                            }
                            BroadCastUtil
                                    .sendBroadCast(IntentNameUtil.ON_ALTER_PERSONAL_INFO_SUCCESS);
                        }
                    });

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
                                    DBUtil.update(stu, "ubId=" + stu.getUbId());
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
                                    DBUtil.update(tea, "ubId=" + tea.getUbId());

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
