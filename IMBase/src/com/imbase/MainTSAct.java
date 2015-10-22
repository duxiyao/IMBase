package com.imbase;

import java.util.ArrayList;

import org.kymjs.kjframe.KJActivity;
import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.ui.ViewInject;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

import com.kjstudy.frag.InterestFrag;
import com.kjstudy.frag.FriendFrag;
import com.kjstudy.frag.IdentityFrag;
import com.kjstudy.frag.MapFrag;
import com.kjstudy.plugin.MainFooterView;

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
	}

	@Override
	public void initWidget() {
		super.initWidget();
		MapFrag mapFrag = new MapFrag();
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
	}
}
