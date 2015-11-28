package com.kjstudy.act;

import java.util.ArrayList;
import java.util.List;

import org.kymjs.kjframe.KJActivity;
import org.kymjs.kjframe.ui.BindView;

import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.Poi;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.imbase.R;
import com.kjstudy.ext.AdapterBase;
import com.kjstudy.frag.MapAssistant;
import com.kjstudy.maputil.MapLocation.LocationListener;

/**
 * @ClassName: LocMapAct
 * @Description: 定位界面
 * @author duxiyao
 * @date 2015年11月28日 下午2:39:40 百度地图 参考 类 @see MapAct
 */
public class LocMapAct extends KJActivity {

    @BindView(id = R.id.bmapView)
    private MapView mMapView;

    @BindView(id = R.id.lv_pois)
    private ListView mLvPois;

    private BaiduMap mBaiduMap;

    private AdapterPoi mAdapter;

    private BDLocation mBDLocation;

    @Override
    public void setRootView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_map_location);
    }

    @Override
    public void initWidget() {
        super.initWidget();

        mBaiduMap = mMapView.getMap();
        // 普通地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        mAdapter = new AdapterPoi();
        mLvPois.setAdapter(mAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        MapStatusUpdate u = MapStatusUpdateFactory.zoomTo(18);
        mBaiduMap.animateMapStatus(u);
        MapAssistant.startLocation(new LocationListener() {

            @Override
            public void onReceiveLocation(BDLocation location) {
                if (BDLocation.TypeGpsLocation == location.getLocType()
                        || BDLocation.TypeNetWorkLocation == location
                                .getLocType()
                        || BDLocation.TypeOffLineLocation == location
                                .getLocType()) {
                    mBDLocation = location;
                    // 定位成功
                    // 添加标注物
                    MapAssistant.addOverlay(mBaiduMap, location);
                    List<Poi> list = location.getPoiList();// POI数据
                    List<String> tmp = new ArrayList<String>();
                    if (list != null) {
                        for (Poi p : list) {
                            if (p != null && !TextUtils.isEmpty(p.getName())) {
                                tmp.add(p.getName());
                            }
                        }
                    }
                    mAdapter.setDatas(tmp);
                }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    public static final class AdapterPoi extends
            AdapterBase<String, AdapterPoi.Holder> {

        public static final class Holder {
            @BindView(id = R.id.tv_data)
            TextView tv;
        }

        @Override
        protected int getItemLayout() {
            return R.layout.item_poi;
        }

        @Override
        protected void process(int position, Holder h) {
            final String d = mDatas.get(position);
            h.tv.setText(d);
            h.tv.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    submitLocInfo(d);
                }
            });
        }

        private void submitLocInfo(String poiInfo) {
            try {
                // mBDLocation
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
}
