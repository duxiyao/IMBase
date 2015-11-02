package com.kjstudy.frag;

import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.utils.ActUtil;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.imbase.LoginAct;
import com.imbase.R;
import com.kjstudy.act.UploadImageActivity;
import com.kjstudy.plugin.CircleImageView;

public class StudentFrag extends BFrag implements OnClickListener {

	@BindView(id = R.id.iv_head, click = true)
	private CircleImageView mCIVHead;
	@BindView(id = R.id.tv_name, click = true)
	private TextView mTvName;

	@Override
	protected int getLayoutId() {
		return R.layout.layout_student_me;
	}

	@Override
	protected void initWidget() {
		super.initWidget();

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_head:
			ActUtil.startAct(UploadImageActivity.class);
			break;
		case R.id.tv_name:
			ActUtil.startAct(LoginAct.class);
			getActivity().overridePendingTransition(
					R.anim.sideslip_in_from_right,
					R.anim.sideslip_out_from_left);
			break;
		default:
			break;
		}
	}
}
