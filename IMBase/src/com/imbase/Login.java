package com.imbase;

import java.util.Map;

import org.kymjs.kjframe.KJActivity;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.ui.ViewInject;
import org.kymjs.kjframe.utils.KJLoger;
import org.kymjs.kjframe.utils.StringUtils;

import android.app.ProgressDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kjstudy.bean.EUserInfo;
import com.kjstudy.bean.data.UserInfo;
import com.kjstudy.communication.CCPConfig;
import com.kjstudy.communication.SDKCoreHelper;
import com.kjstudy.communication.SDKHelper;
import com.kjstudy.core.net.Req;
import com.kjstudy.core.util.DBUtil;
import com.kjstudy.core.util.GUtil;
import com.kjstudy.core.util.JsonUtil;

public class Login extends KJActivity {

	@BindView(id = R.id.et_name)
	private EditText etName;

	@BindView(id = R.id.et_pwd)
	private EditText etPwd;

	@BindView(id = R.id.tv_login, click = true)
	private TextView tvLogin;

	@BindView(id = R.id.page_login)
	private LinearLayout llLogin;

	private ProgressDialog mProgressDialog;

	@Override
	public void setRootView() {
		setContentView(R.layout.login);
	}

	@Override
	public void widgetClick(View v) {
		super.widgetClick(v);
		switch (v.getId()) {
		case R.id.tv_login:
			ViewInject.toast(String.valueOf("on click"));
			login();
			// skipActivity(this, Qrcode.class);
			break;
		default:
			break;
		}
	}

	private void login() {
		String name = etName.getText().toString();
		String pwd = etPwd.getText().toString();
		if (GUtil.isEmpty(name)) {
			ViewInject.toast("用户名不能为空!");
			return;
		}
		if (GUtil.isEmpty(pwd)) {
			ViewInject.toast("密码不能为空!");
			return;
		}
		KJLoger.debug("name=" + name + "  pwd=" + pwd);
		if (mProgressDialog == null)
			mProgressDialog = ViewInject.getprogress(this,
					StringUtils.getResString(R.string.request_from_net), false);
		Req.login(name, pwd, new HttpCallBack() {
			@Override
			public void onSuccess(Map<String, String> headers, byte[] t) {
				String ret = new String(t);
				ViewInject.toast(String.valueOf(ret));
				EUserInfo user = JsonUtil.json2Obj(ret, EUserInfo.class);
				if (user.getCode() == 0 && user.getData() != null) {
					UserInfo u = user.getData();
					if (!DBUtil.getDB().isExists(UserInfo.class,
							"email='" + u.getEmail() + "'")) {
						DBUtil.save(u);
					}
					CCPConfig.VoIP_ID = u.getVoipAccount();
					CCPConfig.VoIP_PWD = u.getVoipPwd();
					CCPConfig.Sub_Account = u.getSubAccountSid();
					CCPConfig.Sub_Token = u.getSubToken();
					SDKHelper.getInstance().init();
					// SDKCoreHelper.init(Login.this);
				} else {
					ViewInject.toast(user.getMsg());
					dismissDialog();
				}
			}

			@Override
			public void onLoading(long count, long current) {
				showDialog();
			}

			@Override
			public void onFinish() {
			}
		});
	}

	private void showDialog() {
		if(mProgressDialog!=null&&!mProgressDialog.isShowing())
			mProgressDialog.show();
	}

	private void dismissDialog() {
		if(mProgressDialog!=null&&mProgressDialog.isShowing())
			mProgressDialog.dismiss();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		dismissDialog();
	}
}
