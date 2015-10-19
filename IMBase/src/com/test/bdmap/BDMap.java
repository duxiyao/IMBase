package com.test.bdmap;

import org.kymjs.kjframe.KJActivity;
import org.kymjs.kjframe.ui.BindView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.imbase.R;

public class BDMap extends KJActivity {

	@BindView(id = R.id.bmapView)
	private MapView mMapView;
	private BaiduMap mBaiduMap;

	@Override
	public void setRootView() {
		setContentView(R.layout.layout_bdmap_test);
	}

	@Override
	public void initWidget() {
		super.initWidget();

		mBaiduMap = mMapView.getMap();
		// 普通地图
		mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
//		mBaiduMap.setTrafficEnabled(true);
		//开启交通图   
		mBaiduMap.setBaiduHeatMapEnabled(true);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mMapView.onDestroy();
	}

	@Override
	protected void onResume() {
		super.onResume();
		mMapView.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		mMapView.onPause();
	}
}
