package com.kjstudy.frag;

import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.ui.BindView;

import android.text.TextUtils;
import android.widget.ListView;

import com.imbase.R;
import com.kjstudy.bean.EntityS;
import com.kjstudy.bean.EntityT;
import com.kjstudy.bean.data.EnLocalPOI;
import com.kjstudy.core.net.Req;
import com.kjstudy.core.net.interfacebean.IEnSearchPOI;
import com.kjstudy.core.util.GUtil;
import com.kjstudy.core.util.JsonUtil;

public class NearByListFrag extends BFrag {

    @BindView(id = R.id.lv_nearby)
    private ListView mLvNearBy;

    @Override
    protected int getLayoutId() {
        return R.layout.frag_layout_nearby_list;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        IEnSearchPOI en = new IEnSearchPOI();
        Req.searchPOI(en, new HttpCallBack() {
            @Override
            public void onSuccess(String t) {
                if (GUtil.isEmpty(t))
                    return;
                try {
                    EntityT<EnLocalPOI> ens = JsonUtil.json2ET(t, EnLocalPOI.class);
                    EnLocalPOI elp = ens.getData();   
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
