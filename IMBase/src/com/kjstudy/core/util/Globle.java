package com.kjstudy.core.util;

import org.kymjs.kjframe.utils.PreferenceHelper;

import android.text.TextUtils;

import com.imbase.MyApplication;
import com.imbase.RegisterAct;
import com.kjstudy.bean.data.TSUserInfo;
import com.kjstudy.communication.CCPConfig;
import com.kjstudy.communication.SDKHelper;
import com.kjstudy.core.io.FileUtil;

public class Globle {
	private static TSUserInfo CURUSER;
	final static String KEY = "id"; // phone or qqOpenId or wxOpenId
	/**
	 * @date 2015年11月3日
	 * @author duxiyao
	 * @description phone , qqOpenId , wxOpenId
	 */
	final static String LOGIN_TYPE = "login_type";

	public static TSUserInfo getCURUSER() {
		return CURUSER;
	}

	/**
	 * @date 2015年11月3日
	 * @author duxiyao
	 * @description 上次登录的用户
	 */
	public static void lastLoginUser() {
		String id = PreferenceHelper.readString(MyApplication.getInstance()
				.getApplicationContext(), FileUtil.FN_CURUSERPHONE, KEY);
		if (TextUtils.isEmpty(id))
			return;
		String loginType = PreferenceHelper.readString(MyApplication
				.getInstance().getApplicationContext(),
				FileUtil.FN_CURUSERPHONE, LOGIN_TYPE);
		if (DBUtil.getDB().isExists(TSUserInfo.class,
				loginType + "='" + id + "'")) {
			setCURUSER(
					DBUtil.findOne(TSUserInfo.class, loginType + "='" + id
							+ "'"), loginType);
		}
	}

	public static void setCURUSER(TSUserInfo u) {
		setCURUSER(u, "phone");
	}

	public static void setCURUSER(TSUserInfo u, String loginType) {
		if (!GUtil.isEmpty(loginType)) {
			if ("phone".equals(loginType)) {
				PreferenceHelper.write(MyApplication.getInstance()
						.getApplicationContext(), FileUtil.FN_CURUSERPHONE,
						LOGIN_TYPE, loginType);
				PreferenceHelper.write(MyApplication.getInstance()
						.getApplicationContext(), FileUtil.FN_CURUSERPHONE,
						KEY, u.getPhone());
			} else if ("qqOpenId".equals(loginType)) {
				PreferenceHelper.write(MyApplication.getInstance()
						.getApplicationContext(), FileUtil.FN_CURUSERPHONE,
						LOGIN_TYPE, loginType);
				PreferenceHelper.write(MyApplication.getInstance()
						.getApplicationContext(), FileUtil.FN_CURUSERPHONE,
						KEY, u.getQqOpenId());
			} else if ("wxOpenId".equals(loginType)) {
				PreferenceHelper.write(MyApplication.getInstance()
						.getApplicationContext(), FileUtil.FN_CURUSERPHONE,
						LOGIN_TYPE, loginType);
				PreferenceHelper.write(MyApplication.getInstance()
						.getApplicationContext(), FileUtil.FN_CURUSERPHONE,
						KEY, u.getQqOpenId());
			}

		}
		CURUSER = u;
		PreferenceHelper.write(MyApplication.getInstance()
				.getApplicationContext(), FileUtil.FN_CURUSERPHONE, KEY, u
				.getPhone());
		CCPConfig.VoIP_ID = u.getVoipAccount();
		CCPConfig.VoIP_PWD = u.getVoipPwd();
		CCPConfig.Sub_Account = u.getSubAccountSid();
		CCPConfig.Sub_Token = u.getSubToken();
		// if (TextUtils.isEmpty(CCPConfig.VoIP_ID)
		// || TextUtils.isEmpty(CCPConfig.VoIP_PWD)
		// || TextUtils.isEmpty(CCPConfig.Sub_Account)
		// || TextUtils.isEmpty(CCPConfig.Sub_Token))
		// return;
		SDKHelper.getInstance().init();
	}

}
