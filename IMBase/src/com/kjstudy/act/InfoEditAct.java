package com.kjstudy.act;

import org.kymjs.kjframe.KJActivity;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.ui.ViewInject;
import org.kymjs.kjframe.utils.BroadCastUtil;
import org.kymjs.kjframe.utils.StringUtils;

import android.view.View;
import android.widget.EditText;

import com.imbase.R;
import com.kjstudy.bars.BarDefault2;
import com.kjstudy.bean.Entity;
import com.kjstudy.core.net.Req;
import com.kjstudy.core.util.IntentNameUtil;
import com.kjstudy.core.util.JsonUtil;

public class InfoEditAct extends KJActivity {

	public static final String KEY = "InfoEditAct.key";
	public static final String HINTVALUE = "InfoEditAct.hintvalue";

	@BindView(id = R.id.et_content)
	private EditText mEtContent;
	private String mKey;

	@Override
	public void setRootView() {
		setContentView(R.layout.layout_personal_info_edit);
	}

	@Override
	public void initWidget() {
		super.initWidget();
		BarDefault2 bar = new BarDefault2();
		bar.setOnClickLis(this);
		mKey = getIntent().getStringExtra(KEY);
		mEtContent.setText(getIntent().getStringExtra(HINTVALUE));
		setCustomBar(bar.getBarView());
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.tv_confirm:
			if (StringUtils.isEmail(mKey)) {
				ViewInject.toast("key error");
				return;
			}
			String value = mEtContent.getText().toString();
			if (StringUtils.isEmail(value)) {
				ViewInject.toast("没写东西....");
				return;
			}
			Req.updateUserInfo(mKey, value, new HttpCallBack() {
				@Override
				public void onSuccess(String t) {
					super.onSuccess(t);
					try {
						Entity en = JsonUtil.json2Obj(t, Entity.class);
						if (0 == en.getCode())
							BroadCastUtil
									.sendBroadCast(IntentNameUtil.ON_ALTER_PERSONAL_INFO_SUCCESS);
						else
							ViewInject.toast("修改失败！");
					} catch (Exception e) {
						ViewInject.toast("修改失败！");
					}
				}
			});
			finish();
			break;

		default:
			break;
		}
	}
}
