package com.kjstudy.frag;

import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.ui.KJActivityStack;
import org.kymjs.kjframe.utils.ActUtil;
import org.kymjs.kjframe.utils.ViewUtils;

import android.app.ActionBar;
import android.view.View;
import android.view.View.OnClickListener;

import com.imbase.R;
import com.kjstudy.act.UploadImageActivity;
import com.kjstudy.bars.BarDefault;
import com.kjstudy.core.util.ViewUtil;
import com.kjstudy.plugin.CircleImageView;

public class StudentFrag extends BFrag implements OnClickListener {

	@BindView(id = R.id.iv_head, click = true)
	private CircleImageView mCIVHead;

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

		default:
			break;
		}
	}
}
