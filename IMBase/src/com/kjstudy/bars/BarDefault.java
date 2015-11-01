package com.kjstudy.bars;

import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.utils.bars.AbsBarUtil;

import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.imbase.R;

public class BarDefault extends AbsBarUtil {

	@BindView(id = R.id.iv_back, click = true)
	private ImageView mIvBack;
	@BindView(id = R.id.tv1, click = true)
	private TextView mTv1;
	@BindView(id = R.id.tv2, click = true)
	private TextView mTv2;

	@Override
	protected int getLayoutId() {
		return R.layout.actionbar_default;
	}

	public void setOnClickLis(OnClickListener lis) {
		mOnClickListener = lis;
	}

	public void setTxt(int which, String txt) {
		if (which == 1)
			mTv1.setText(txt);
		if (which == 2)
			mTv2.setText(txt);
	}
}
