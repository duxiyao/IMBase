package com.imbase;

import org.kymjs.kjframe.KJActivity;
import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.utils.ActUtil;

import android.view.View;
import android.widget.TextView;

public class LoginAct extends KJActivity {

	@BindView(id = R.id.tv_register, click = true)
	private TextView mTvRegister;

	@Override
	public void setRootView() {
		setContentView(R.layout.layout_login);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.tv_register:
			ActUtil.startAct(RegisterAct.class);
			overridePendingTransition(R.anim.sideslip_in_from_right,
					R.anim.sideslip_out_from_left);
			break;

		default:
			break;
		}
	}
}
