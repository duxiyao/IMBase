package com.kjstudy.test.act;

import java.util.ArrayList;
import java.util.List;

import org.kymjs.kjframe.KJActivity;
import org.kymjs.kjframe.ui.BindView;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.view.View;
import android.view.View.OnLayoutChangeListener;
import android.widget.LinearLayout;

import com.imbase.R;
import com.kjstudy.test.view.CustomeImgView;
import com.kjstudy.test.view.MapInfo;

public class ZndzAct extends KJActivity {

	@BindView(id = R.id.iv)
	private CustomeImgView mIv;
	@BindView(id = R.id.ll)
	private LinearLayout mLl;

	@Override
	public void setRootView() {
		setContentView(R.layout.layout_zndz);
	}

	@SuppressLint("NewApi")
	@Override
	public void initWidget() {
		// mIv.setAdjustViewBounds(true);
		List<MapInfo> datas = new ArrayList<MapInfo>();
		MapInfo mi = new MapInfo();
		mi.setName("bg");
		mi.setResIdDef(R.drawable.man_front);
		// mi.setResIdPress(resIdPress);
		datas.add(mi);

		mi = new MapInfo();
		mi.setName("head");
		mi.setResIdDef(R.drawable.head);
		// mi.setResIdPress(resIdPress);
		datas.add(mi);

		mi = new MapInfo();
		mi.setName("neck");
		mi.setResIdDef(R.drawable.neck);
		// mi.setResIdPress(resIdPress);
		datas.add(mi);

		mi = new MapInfo();
		mi.setName("chest");
		mi.setResIdDef(R.drawable.chest);
		// mi.setResIdPress(resIdPress);
		datas.add(mi);

		mi = new MapInfo();
		mi.setName("arm");
		mi.setResIdDef(R.drawable.arm);
		mi.setResIdPress(R.drawable.arm_click);
		datas.add(mi);

		mi = new MapInfo();
		mi.setName("hand");
		mi.setResIdDef(R.drawable.hand);
		// mi.setResIdPress(resIdPress);
		datas.add(mi);

		mi = new MapInfo();
		mi.setName("abdomen");
		mi.setResIdDef(R.drawable.abdomen);
		// mi.setResIdPress(resIdPress);
		datas.add(mi);

		mi = new MapInfo();
		mi.setName("genitals");
		mi.setResIdDef(R.drawable.genitals);
		// mi.setResIdPress(resIdPress);
		datas.add(mi);

		mi = new MapInfo();
		mi.setName("leg");
		mi.setResIdDef(R.drawable.leg);
		mi.setResIdPress(R.drawable.leg_click);
		datas.add(mi);

		mi = new MapInfo();
		mi.setName("foot");
		mi.setResIdDef(R.drawable.foot);
		// mi.setResIdPress(resIdPress);
		datas.add(mi);
		mIv.setDatas(datas);
		mLl.addOnLayoutChangeListener(new OnLayoutChangeListener() {

			@Override
			public void onLayoutChange(View v, int left, int top, int right,
					int bottom, int oldLeft, int oldTop, int oldRight,
					int oldBottom) {
				float w = mLl.getWidth();
				float h = mLl.getHeight();
				float cw = mIv.getWidth();
				float ch = mIv.getHeight();

				ObjectAnimator anim = ObjectAnimator.ofFloat(mIv, "scaleX", w/cw);
				anim.setDuration(1000);
				anim.start();

				anim = ObjectAnimator.ofFloat(mIv, "scaleY", h/ch);
				anim.setDuration(1000);
				anim.start();
			}
		});
		// ObjectAnimator anim = ObjectAnimator.ofFloat(mIv, "scaleX", 2f);
		// anim.setDuration(1000);
		// anim.start();
		// PhotoViewAttacher attacher = new PhotoViewAttacher(mIv);
		// attacher.setOnPhotoTapListener(new OnPhotoTapListener() {
		// @Override
		// public void onPhotoTap(View view, float x, float y) {
		// finish();
		// }
		// });
	}
}
