package com.imbase;

import java.util.ArrayList;

import org.kymjs.kjframe.KJActivity;
import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.ui.ViewInject;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Html;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kjstudy.dialog.DialogAssistant;
import com.kjstudy.frag.FriendFrag;
import com.kjstudy.frag.IdentityFrag;
import com.kjstudy.frag.InterestFrag;
import com.kjstudy.frag.SearchFrag;
import com.kjstudy.plugin.MainFooterView;
import com.kjstudy.plugin.MainFooterView.OnItemClickListener;
import com.kjstudy.plugin.gesture_pwd.GestureContentView;
import com.kjstudy.plugin.gesture_pwd.GestureDrawline.GestureCallBack;
import com.umeng.update.UmengUpdateAgent;

public class MainTSAct extends KJActivity {

	/**
	 * @author duxiyao
	 * @description 主界面几个fragment的adapter
	 */
	class MainFragmentAdapter extends FragmentPagerAdapter {

		/**
		 * @author duxiyao
		 * @description 主界面要用的fragments
		 */
		private ArrayList<Fragment> mFrags;

		/**
		 * @author duxiyao
		 * @description 构造函数
		 * @param fm
		 * @param frags
		 */
		public MainFragmentAdapter(FragmentManager fm, ArrayList<Fragment> frags) {
			super(fm);
			this.mFrags = frags;
		}

		@Override
		public Fragment getItem(int pos) {
			return mFrags.get(pos);
		}

		@Override
		public int getCount() {
			return mFrags.size();
		}

	}

	@BindView(id = R.id.vp)
	private ViewPager mVp;
	@BindView(id = R.id.custom_footer)
	private MainFooterView mFoot;

	private MainFragmentAdapter mFragAdapter;

	@Override
	public void setRootView() {
		setContentView(R.layout.layout_main);
		UmengUpdateAgent.update(this);
	}

	GestureContentView mGestureContentView;

	void testpwd() {
		View pwdV = getLayoutInflater().inflate(R.layout.layout_test_pwd, null);
		final TextView mTextTip = (TextView) pwdV.findViewById(R.id.tv_tips);
		FrameLayout fl = (FrameLayout) pwdV
				.findViewById(R.id.fl_gesture_container);
		DialogAssistant.getPwdDialog(pwdV).show();

		mGestureContentView = new GestureContentView(this, true, "1596",
				new GestureCallBack() {

					@Override
					public void onGestureCodeInput(String inputCode) {

					}

					@Override
					public void checkedSuccess() {
						mGestureContentView.clearDrawlineState(0L);
						ViewInject.toast("密码正确");
					}

					@Override
					public void checkedFail() {
						mGestureContentView.clearDrawlineState(1300L);
						mTextTip.setVisibility(View.VISIBLE);
						mTextTip.setText(Html
								.fromHtml("<font color='#c70c1e'>密码错误，还有</font>"
										+ "<font color='#ffffff'>"
										+ "..."
										+ "</font>"
										+ "<font color='#c70c1e'>次机会</font>"));
						// 左右移动动画
						Animation shakeAnimation = AnimationUtils
								.loadAnimation(MainTSAct.this, R.anim.shake);
						mTextTip.startAnimation(shakeAnimation);
					}
				});
		// 设置手势解锁显示到哪个布局里面
		mGestureContentView.setParentView(fl);
		mGestureContentView.setGestureNodeNormal(R.drawable.gesture_node_normal1);
		mGestureContentView.setGestureNodePressed(R.drawable.gesture_node_pressed1);
	}

	@Override
	public void initWidget() {
		super.initWidget();
		testpwd();
		SearchFrag mapFrag = new SearchFrag();
		InterestFrag comFrag = new InterestFrag();
		FriendFrag frdFrag = new FriendFrag();
		IdentityFrag idyFrag = new IdentityFrag();
		ArrayList<Fragment> frags = new ArrayList<Fragment>();
		frags.add(mapFrag);
		frags.add(comFrag);
		frags.add(frdFrag);
		frags.add(idyFrag);
		mFragAdapter = new MainFragmentAdapter(getSupportFragmentManager(),
				frags);
		mVp.setAdapter(mFragAdapter);

		mVp.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				ViewInject.toast(String.valueOf(arg0));
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
		mFoot.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onFootItemClick(int clickItemIndex) {
				if (clickItemIndex >= 0 && clickItemIndex <= 3)
					mVp.setCurrentItem(clickItemIndex);
			}
		});

		// mFoot.addOnLayoutChangeListener(new OnLayoutChangeListener() {
		//
		// @Override
		// public void onLayoutChange(View v, int left, int top, int right,
		// int bottom, int oldLeft, int oldTop, int oldRight,
		// int oldBottom) {
		// if (oldBottom == 0) {
		// int barHeight = getActionBar().getHeight();
		// LayoutParams lp = mVp.getLayoutParams();
		// int tmp = getWindow().getDecorView().getHeight();
		// int tmp1=DensityUtils.getStatusBarHeight();
		// lp.height = /* DensityUtil.getScreenHeight() */getWindow()
		// .getDecorView().getHeight()
		// - DensityUtils.getStatusBarHeight()
		// - bottom
		// - DensityUtils.getStatusBarHeight(getWindow());
		// mVp.setLayoutParams(lp);
		// }
		// }
		// });
	}
}
