package com.kjstudy.test.act;

import java.util.List;
 



import org.myframe.MActivity;
import org.myframe.ui.BindView;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.View.OnLayoutChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.imbase.R;
import com.kjstudy.core.thread.ThreadManager;
import com.kjstudy.test.view.CustomeImgView;
import com.kjstudy.test.view.MapInfo;
import com.kjstudy.test.view.ZndzDataUtil;

@SuppressLint("NewApi")
public class ZndzAct extends MActivity {

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
		mIv.addOnLayoutChangeListener(new OnLayoutChangeListener() {

			@Override
			public void onLayoutChange(View v, int left, int top, int right,
					int bottom, int oldLeft, int oldTop, int oldRight,
					int oldBottom) {
				int h = mIv.getMeasuredHeight();
				int w = mIv.getMeasuredWidth();
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(w,
						h);
				mIv.setLayoutParams(lp);
				mIv.setBackground(null);
			}
		});
		// mLl.addOnLayoutChangeListener(new OnLayoutChangeListener() {
		//
		// @Override
		// public void onLayoutChange(View v, int left, int top, int right,
		// int bottom, int oldLeft, int oldTop, int oldRight,
		// int oldBottom) {
		// float w = mLl.getWidth();
		// float h = mLl.getHeight();
		// float cw = mIv.getWidth();
		// float ch = mIv.getHeight();
		//
		// mZndzAssistant.clickWoMan();
		// System.out.println(w / cw);
		// float sx = w / cw;
		// if (sx > 1.7)
		// sx = 1.7f;
		// ObjectAnimator anim = ObjectAnimator.ofFloat(mIv, "scaleX", sx);
		// anim.setDuration(1000);
		// anim.start();
		//
		// anim = ObjectAnimator.ofFloat(mIv, "scaleY", h / ch);
		// anim.setDuration(1000);
		// anim.start();
		// }
		// });
	}

	@Override
	public void widgetClick(View v) {
		switch (v.getId()) {
		case R.id.iv_sex_female:
			mZndzAssistant.clickWoMan();
			break;
		case R.id.iv_sex_male:
			mZndzAssistant.clickMan();
			System.out.println("click man " + System.currentTimeMillis());
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
			System.out.println("before clickMan " + System.currentTimeMillis());
			ThreadManager.getInstance().exeRunnable(new Runnable() {

				@Override
				public void run() {
					mCurFrontDatas = ZndzDataUtil.getManFront();
					mCurBackDatas = ZndzDataUtil.getManBack();
				}
			});
			System.out.println("after clickMan " + System.currentTimeMillis());
			setCur();
		}

		public void clickWoMan() {
			setIvSexBg(true);
			ThreadManager.getInstance().exeRunnable(new Runnable() {

				@Override
				public void run() {
					mCurFrontDatas = ZndzDataUtil.getWoManFront();
					mCurBackDatas = ZndzDataUtil.getWoManBack();
				}
			});
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
			rotate(mIv, new Runnable() {

				@Override
				public void run() {
					ZndzAct.this.runOnUiThread(new Runnable() {

						@Override
						public void run() {
							ThreadManager.getInstance().exeRunnable(
									new Runnable() {

										@Override
										public void run() {
											if (mFrontOrBack
													&& mCurFrontDatas != null) {
												while (mCurFrontDatas == null)
													;
												mIv.setDatas(mCurFrontDatas);
											} else {
												if (mCurBackDatas != null) {
													while (mCurBackDatas == null)
														;
													mIv.setDatas(mCurBackDatas);
												}
											}

										}
									});
						}
					});
				}
			});
		}

		/**
		 * @date 2015年9月21日
		 * @author duxiyao
		 * @description 设置正面反面
		 * @param txt
		 */
		private void setTvCtl(final String txt) {
			rotate(mTvCtl, new Runnable() {

				@Override
				public void run() {
					mTvCtl.setText(txt);
				}
			});
		}

		private void rotate(final View v, final Runnable run) {
			if (run != null)
				run.run();
			ObjectAnimator anim = ObjectAnimator.ofFloat(v, "rotationY", 0.0f,
					90.0f);
			anim.setDuration(600);
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
					anim.setDuration(600);
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
