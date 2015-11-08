package com.kjstudy.act;

import org.kymjs.kjframe.KJActivity;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.ui.ViewInject;
import org.kymjs.kjframe.utils.ActUtil;
import org.kymjs.kjframe.utils.BroadCastUtil;
import org.kymjs.kjframe.utils.StringUtils;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.imbase.R;
import com.kjstudy.bean.Entity;
import com.kjstudy.bean.data.TSUserInfo;
import com.kjstudy.core.net.Req;
import com.kjstudy.core.util.Global;
import com.kjstudy.core.util.IntentNameUtil;
import com.kjstudy.core.util.JsonUtil;
import com.kjstudy.dialog.DialogAssistant;
import com.kjstudy.plugin.CircleImageView;

public class StuPreInfoEditAct extends KJActivity {

	@BindView(id = R.id.iv_head)
	private CircleImageView mIvHead;
	@BindView(id = R.id.tv_name)
	private TextView mTvName;
	@BindView(id = R.id.tv_sex)
	private TextView mTvSex;
	@BindView(id = R.id.tv_age)
	private TextView mTvAge;
	@BindView(id = R.id.tv_resident)
	private TextView mTvResident;
	@BindView(id = R.id.tv_grade)
	private TextView mTvGrade;
	@BindView(id = R.id.tv_subject)
	private TextView mTvSubject;
	@BindView(id = R.id.tv_personal_signature)
	private TextView mTvPersonalSig;
	@BindView(id = R.id.ll_content)
	private LinearLayout mLlContent;

	@Override
	public void setRootView() {
		setContentView(R.layout.layout_personal_info);
	}

	@Override
	public void initWidget() {
		super.initWidget();
		int len = mLlContent.getChildCount();
		for (int i = 0; i < len; i++) {
			View v = mLlContent.getChildAt(i);
			if (v instanceof RelativeLayout) {
				v.setOnClickListener(this);
			}
		}
		TSUserInfo m = Global.getCURUSER();
		if (m != null) {
			mTvName.setText(m.getName());
			if (m.getSex() != -1)
				mTvSex.setText(m.getSex() == 0 ? "男" : "女");
			if (m.getAge() != -1)
				mTvAge.setText(String.valueOf(m.getAge()));
		}
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		String key = "";
		String strHint = "";
		switch (v.getId()) {
		case R.id.rl_head:
			ActUtil.startAct(UploadImageActivity.class);
			break;
		case R.id.rl_name:
			key = "a.name";
			strHint = "改名字！";
			break;
		case R.id.rl_sex:
			// key = "a.sex";
			// strHint = "";
			choiceSex();
			break;
		case R.id.rl_age:
			key = "a.age";
			strHint = "";
			break;
		default:
			break;
		}
		if (!StringUtils.isEmpty(key)) {
			Bundle b = new Bundle();
			b.putString(InfoEditAct.KEY, key);
			b.putString(InfoEditAct.HINTVALUE, strHint);
			b.putInt(InfoEditAct.INTTYPE, 0);
			ActUtil.startAct(InfoEditAct.class, b);
			return;
		}

		switch (v.getId()) {
		case R.id.rl_resident:
			break;
		case R.id.rl_grade:
			key = "b.grade";
			strHint = "";
			break;
		case R.id.rl_subject:
			key = "b.subject";
			strHint = "";
			break;
		case R.id.rl_personal_signature:

			break;
		default:
			break;
		}
		if (!StringUtils.isEmpty(key)) {
			Bundle b = new Bundle();
			b.putString(InfoEditAct.KEY, key);
			b.putString(InfoEditAct.HINTVALUE, strHint);
			b.putInt(InfoEditAct.INTTYPE, 1);
			ActUtil.startAct(InfoEditAct.class, b);
			return;
		}
	}

	private void choiceSex() {
		View v = LayoutInflater.from(getApplicationContext()).inflate(
				R.layout.layout_dialog_choice_sex, null);

		final Dialog d = DialogAssistant.getCustomDialog(v);
		OnClickListener lis = new OnClickListener() {

			@Override
			public void onClick(View v) {

				switch (v.getId()) {
				case R.id.tv_man:
					reqSex("0");
					break;
				case R.id.tv_woman:
					reqSex("1");
					break;
				case R.id.tv_cancle:
					d.dismiss();
					break;

				default:
					break;
				}
			}
		};
		v.findViewById(R.id.tv_man).setOnClickListener(lis);
		v.findViewById(R.id.tv_woman).setOnClickListener(lis);
		d.show();
	}

	private void reqSex(String v) {
		Req.updateUserInfo(0, "a.sex", v, new HttpCallBack() {
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
	}
}
