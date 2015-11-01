package com.kjstudy.frag;

import org.kymjs.kjframe.ui.BindView;

import android.widget.ListView;

import com.imbase.R;
import com.kjstudy.bars.BarDefault;

public class NearByListFrag extends BFrag {

	@BindView(id = R.id.lv_nearby)
	private ListView mLvNearBy;

	@Override
	protected int getLayoutId() {
		return R.layout.frag_layout_nearby_list;
	}

	@Override
	protected void initWidget() {
		super.initWidget();
	}
}
