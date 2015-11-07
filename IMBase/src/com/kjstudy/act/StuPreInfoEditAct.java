package com.kjstudy.act;

import org.kymjs.kjframe.KJActivity;
import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.utils.ActUtil;
import org.kymjs.kjframe.utils.StringUtils;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.imbase.R;
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
			key = "name";
			strHint = "改名字！";
			break;
		case R.id.rl_sex:
			key = "sex";
			strHint = "";
			break;
		case R.id.rl_age:
			key = "age";
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
			break;
		case R.id.rl_subject:
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
}
