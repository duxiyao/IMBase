package com.kjstudy.frag;

import org.kymjs.kjframe.ui.BindView;

import android.content.Context;
import android.telephony.TelephonyManager;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.imbase.R;
import com.kjstudy.frag.maputil.MapLocation.LocationListener;

public class MapFrag extends BFrag {

	@BindView(id=R.id.bmapView)
	private MapView mMapView;
	private BaiduMap mBaiduMap;

	@Override
	protected int getLayoutId() {
		return R.layout.frag_layout_map;
	}

	@Override
	protected void initWidget() {
		mBaiduMap = mMapView.getMap();
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
