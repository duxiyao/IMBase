package com.kjstudy.frag;

import org.kymjs.kjframe.ui.BindView;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.telephony.TelephonyManager;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapFragment;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.SupportMapFragment;
import com.imbase.R;
import com.kjstudy.frag.SupportMapFragment1.OnMapCreated;
import com.kjstudy.frag.maputil.MapLocation.LocationListener;

public class NearByFrag extends BFrag implements OnMapCreated {

	private SupportMapFragment mMap;
	private BaiduMap mBaiduMap;

	@Override
	protected int getLayoutId() {
		return R.layout.frag_layout_nearby;
	}

	@Override
	protected void initWidget() {
		super.initWidget();
		FragmentManager fm = getActivity().getSupportFragmentManager();
		mMap = SupportMapFragment1.newInstance(this);
		fm.beginTransaction().replace(R.id.map, mMap).commit();
	}

	@Override
	public void onMapCreated() {

		mBaiduMap = mMap.getBaiduMap();
		// 普通地图
		mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);

		MapAssistant.startLocation(new LocationListener() {

			@Override
			public void onReceiveLocation(BDLocation location) {
				MapAssistant.addOverlay(mBaiduMap, location);
			}
		});
	}
}
