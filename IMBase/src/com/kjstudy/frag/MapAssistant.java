package com.kjstudy.frag;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.kjstudy.frag.maputil.MapLocation;
import com.kjstudy.frag.maputil.MapLocation.LocationListener;
import com.kjstudy.frag.maputil.MapOverlay;

public class MapAssistant {

	public static void startLocation(LocationListener lis) {
		MapLocation.getInstance().setLLis(lis).startLocation();
	}

	public static void addOverlay(BaiduMap baiduMap, BDLocation location) {
		MapOverlay mapOverlay = new MapOverlay(baiduMap);
		mapOverlay.locationMe(location);
		mapOverlay.randomAroundMe(location);
	}
}
