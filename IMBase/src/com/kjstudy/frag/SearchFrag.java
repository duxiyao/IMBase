package com.kjstudy.frag;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.SupportMapFragment;
import com.imbase.R;
import com.kjstudy.frag.SupportMapFragment1.OnMapCreated;
import com.kjstudy.frag.maputil.MapLocation.LocationListener;

public class SearchFrag extends BFrag {

	private NearByListFrag mNearByListFrag;
	private MapFrag mMapFrag;
	private Fragment mCurFrag;
	private FragmentManager mManager;
	private FragmentTransaction mTransaction;

	@Override
	protected int getLayoutId() {
		return R.layout.frag_layout_search;
	}

	@Override
	protected void initWidget() {
		super.initWidget();

		mNearByListFrag = new NearByListFrag();
		mMapFrag = new MapFrag();
		mCurFrag = mNearByListFrag;
		showFragment();
	}

	private void showFragment() {
		if (mManager == null)
			mManager = getChildFragmentManager();
		mTransaction = mManager.beginTransaction();
		if (mCurFrag != null) {
			mTransaction.replace(R.id.search_container, mCurFrag);
		}

		mTransaction.commitAllowingStateLoss();
	}

	public void showNearByFrag() {
		mCurFrag = mNearByListFrag;
		showFragment();
	}

	public void showMapFrag() {
		mCurFrag = mMapFrag;
		showFragment();
	}

}
