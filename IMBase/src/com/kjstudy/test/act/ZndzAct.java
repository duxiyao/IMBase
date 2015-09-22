package com.kjstudy.test.act;

import java.util.List;

import org.kymjs.kjframe.KJActivity;
import org.kymjs.kjframe.ui.AnnotateUtil;
import org.kymjs.kjframe.ui.BindView;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.view.View.OnLayoutChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.imbase.R;
import com.kjstudy.test.view.CustomeImgView;
import com.kjstudy.test.view.MapInfo;
import com.kjstudy.test.view.ZndzDataUtil;

@SuppressLint("NewApi")
public class ZndzAct extends KJActivity {

	@BindView(id = R.id.iv)
	private CustomeImgView mIv;
	@BindView(id = R.id.ll)
	private LinearLayout mLl;
	@BindView(id = R.id.iv_sex_female, click = true)
	private ImageView mIvSexFemale;
	@BindView(id = R.id.iv_sex_male, click = true)
	private ImageView mIvSexMale;
	@BindView(id = R.id.tv_ctl, click = true)
	private TextView mTvCtl;
	private ZndzAssistant mZndzAssistant;

	@Override
	public void setRootView() {
		 setContentView(R.layout.layout_zndz);
	}

	@SuppressLint("NewApi")
	@Override
	public void initWidget() {
		mZndzAssistant = new ZndzAssistant();
//		mLl.addOnLayoutChangeListener(new OnLayoutChangeListener() {
//
//			@Override
//			public void onLayoutChange(View v, int left, int top, int right,
//					int bottom, int oldLeft, int oldTop, int oldRight,
//					int oldBottom) {
//				float w = mLl.getWidth();
//				float h = mLl.getHeight();
//				float cw = mIv.getWidth();
//				float ch = mIv.getHeight();
//
//				mZndzAssistant.clickWoMan();
//				System.out.println(w / cw);
//				float sx = w / cw;
//				if (sx > 1.7)
//					sx = 1.7f;
//				ObjectAnimator anim = ObjectAnimator.ofFloat(mIv, "scaleX", sx);
//				anim.setDuration(1000);
//				anim.start();
//
//				anim = ObjectAnimator.ofFloat(mIv, "scaleY", h / ch);
//				anim.setDuration(1000);
//				anim.start();
//			}
//		});
	}

	@Override
	public void widgetClick(View v) {
		switch (v.getId()) {
		case R.id.iv_sex_female:
			mZndzAssistant.clickWoMan();
			break;
		case R.id.iv_sex_male:
			mZndzAssistant.clickMan();
			System.out.println("click man "+System.currentTimeMillis());
			break;
		case R.id.tv_ctl:
			mZndzAssistant.clickCtl();
			break;
		default:
			break;
		}
	}

	class ZndzAssistant {
		private List<MapInfo> mCurFrontDatas, mCurBackDatas;
		/**
		 * @date 2015年9月21日
		 * @author duxiyao
		 * @description true front ; false back
		 */
		private boolean mFrontOrBack = true;

		public void clickMan() {
			setIvSexBg(false);
			mCurFrontDatas = ZndzDataUtil.getManFront();
			mCurBackDatas = ZndzDataUtil.getManBack();
			if (mFrontOrBack)
				mIv.setBackground(getResources().getDrawable(R.drawable.man_front));
//				mIv.setBackgroundResource(R.drawable.man_front);
			else
				mIv.setBackgroundResource(R.drawable.man_back);
			setCur();
		}

		public void clickWoMan() {
			setIvSexBg(true);
			mCurFrontDatas = ZndzDataUtil.getWoManFront();
			mCurBackDatas = ZndzDataUtil.getWoManBack();
			if (mFrontOrBack)
				mIv.setBackgroundResource(R.drawable.woman_front);
			else
				mIv.setBackgroundResource(R.drawable.woman_back);
			setCur();
		}

		public void clickCtl() {
			if (mFrontOrBack) {
				setTvCtl("正面");
			} else {
				setTvCtl("反面");
			}
			mFrontOrBack = !mFrontOrBack;
			setCur();
		}

		private void setCur() {
			if (mFrontOrBack && mCurFrontDatas != null) {
				mIv.setDatas(mCurFrontDatas);
			} else {
				if (mCurBackDatas != null) {
					mIv.setDatas(mCurBackDatas);
				}
			}
		}

		/**
		 * @date 2015年9月21日
		 * @author duxiyao
		 * @description 设置正面反面
		 * @param txt
		 */
		private void setTvCtl(final String txt) {
			rotate(mTvCtl,new Runnable() {

				@Override
				public void run() {
					mTvCtl.setText(txt);
				}
			});
		}

		private void rotate(final View v,final Runnable run) {

			ObjectAnimator anim = ObjectAnimator.ofFloat(v, "rotationY",
					0.0f, 90.0f);
			anim.setDuration(500);
			anim.addListener(new AnimatorListener() {

				@Override
				public void onAnimationStart(Animator animation) {
				}

				@Override
				public void onAnimationRepeat(Animator animation) {
				}

				@Override
				public void onAnimationEnd(Animator animation) {
					if (run != null)
						run.run();

					ObjectAnimator anim = ObjectAnimator.ofFloat(v,
							"rotationY", 270.0f, 360.0f);
					anim.setDuration(500);
					anim.start();
				}

				@Override
				public void onAnimationCancel(Animator animation) {
				}
			});
			anim.start();
		}

		/**
		 * @date 2015年9月21日
		 * @author duxiyao
		 * @description 设置性别按钮
		 * @param true 女 ; false 男
		 */
		private void setIvSexBg(boolean f) {
			if (f) {
				mIvSexFemale.setBackgroundResource(R.drawable.zndz_female);
				mIvSexMale.setBackgroundResource(R.drawable.zndz_male_grey);
			} else {
				mIvSexMale.setBackgroundResource(R.drawable.zndz_male);
				mIvSexFemale.setBackgroundResource(R.drawable.zndz_female_grey);
			}
		}
	}
}
