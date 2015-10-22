package com.kjstudy.plugin;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.imbase.R;

/**
 * @author duxiyao
 * @date 2015年9月1日
 * @description 首页下边的 ，【 首页 - 消息】
 */
@SuppressLint("NewApi")
public class MainFooterView extends LinearLayout implements OnClickListener {
	/**
	 * @author duxiyao
	 * @date 2015年9月1日
	 * @description 页面点击切换回调
	 */
	public interface OnItemClickListener {
		/**
		 * @date 2015年9月1日
		 * @author duxiyao
		 * @description 触发footer点击事件
		 * @param 索引
		 */
		void onFootItemClick(int clickItemIndex);
	}

	/**
	 * @date 2015年9月1日
	 * @author duxiyao
	 * @description footer渐变动画效果对应的控件
	 */
	private TextView mTvHome, mTvHomeUnclick, mTvMsg, mTvMsgUnclick;
	/**
	 * @date 2015年9月1日
	 * @author duxiyao
	 * @description 回调
	 */
	private OnItemClickListener mOnItemClickListener;

	/**
	 * @date 2015年9月1日
	 * @author duxiyao
	 * @description 构造函数
	 * @param context
	 */
	public MainFooterView(Context context) {
		super(context);
	}

	/**
	 * @date 2015年9月1日
	 * @author duxiyao
	 * @description 构造函数
	 * @param context
	 * @param attrs
	 */
	public MainFooterView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	/**
	 * @date 2015年9月1日
	 * @author duxiyao
	 * @description 初始化界面控件及事件
	 */
	private void initView() {
		if (isInEditMode())
			return;
		View v = LayoutInflater.from(getContext()).inflate(
				R.layout.layout_main_footer, this);
	}

	/**
	 * @date 2015年9月1日
	 * @author duxiyao
	 * @description 当点击事件触发时调用该函数
	 * @param gone1
	 * @param gone2
	 * @param show1
	 * @param show2
	 */
	private synchronized void onTvClick(View gone1, View gone2, View show1,
			View show2) {
		changeWithAnim(gone1, gone2, show1, show2);
	}

	/**
	 * @date 2015年9月1日
	 * @author duxiyao
	 * @description 切换footer选项，不带动画
	 * @param gone1
	 * @param gone2
	 * @param show1
	 * @param show2
	 */
	private void change(View gone1, View gone2, View show1, View show2) {
		gone1.setVisibility(View.GONE);
		gone2.setVisibility(View.GONE);
		show1.setVisibility(View.VISIBLE);
		show2.setVisibility(View.VISIBLE);
	}

	/**
	 * @date 2015年9月1日
	 * @author duxiyao
	 * @description 切换footer选项，带动画
	 * @param gone1
	 * @param gone2
	 * @param show1
	 * @param show2
	 */
	private synchronized void changeWithAnim(final View gone1,
			final View gone2, final View show1, final View show2) {
		ObjectAnimator anim = ObjectAnimator.ofFloat(gone1, "alpha", 1f, 0f);
		ObjectAnimator anim1 = ObjectAnimator.ofFloat(gone2, "alpha", 1f, 0f);
		ObjectAnimator anim2 = ObjectAnimator.ofFloat(show1, "alpha", 0f, 1f);
		ObjectAnimator anim3 = ObjectAnimator.ofFloat(show2, "alpha", 0f, 1f);
		AnimatorSet as = new AnimatorSet();
		as.playTogether(anim, anim1, anim2, anim3);
		as.setDuration(250);
		as.addListener(new AnimatorListener() {

			@Override
			public void onAnimationStart(Animator animation) {
				show1.setVisibility(View.VISIBLE);
				show2.setVisibility(View.VISIBLE);
			}

			@Override
			public void onAnimationRepeat(Animator animation) {
			}

			@Override
			public void onAnimationEnd(Animator animation) {
				gone1.setVisibility(View.GONE);
				gone2.setVisibility(View.GONE);
				show2.setEnabled(true);
			}

			@Override
			public void onAnimationCancel(Animator animation) {
			}
		});
		as.start();
	}

	@Override
	public void onClick(View v) {
		v.setEnabled(false);
		if (v instanceof TextView) {
			int itemIndex = -1;
			switch (v.getId()) {
			// case R.id.tv_hospital_main_ui_home_unclick:
			// onTvClick(mTvMsg, mTvHomeUnclick, mTvHome, mTvMsgUnclick);
			// itemIndex = 0;
			// break;
			// case R.id.tv_hospital_main_ui_msg_unclick:
			// onTvClick(mTvMsgUnclick, mTvHome, mTvMsg, mTvHomeUnclick);
			// itemIndex = 1;
			// break;
			default:
				break;
			}
			if (mOnItemClickListener != null && itemIndex != -1)
				mOnItemClickListener.onFootItemClick(itemIndex);
		}
	}

	/**
	 * @date 2015年9月1日
	 * @author duxiyao
	 * @description 选择了首页
	 */
	public void selectHomePage() {
		onTvClick(mTvMsg, mTvHomeUnclick, mTvHome, mTvMsgUnclick);
	}

	/**
	 * @date 2015年9月1日
	 * @author duxiyao
	 * @description 选择了消息页
	 */
	public void selectMsgPage() {
		onTvClick(mTvMsgUnclick, mTvHome, mTvHomeUnclick, mTvMsg);
	}

	/**
	 * @date 2015年9月1日
	 * @author duxiyao
	 * @description 设置footer选项点击事件回调
	 * @param onItemClickListener
	 */
	public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
		this.mOnItemClickListener = onItemClickListener;
	}
}
