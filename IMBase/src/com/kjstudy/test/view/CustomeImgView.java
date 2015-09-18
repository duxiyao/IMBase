package com.kjstudy.test.view;

import java.util.HashMap;
import java.util.List;

import org.kymjs.kjframe.utils.DensityUtils;
import org.kymjs.kjframe.utils.KJLoger;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.imbase.R;
import com.kjstudy.core.util.DensityUtil;

public class CustomeImgView extends ImageView {
	private Bitmap[] mBitmaps;
	private int mCurClickIndex = -1;
	private List<MapInfo> mDatas;
	private MapInfo mCurMapInfo;
	private HashMap<Integer, MapInfo> mH;

	public CustomeImgView(Context context) {
		super(context);
	}

	public CustomeImgView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CustomeImgView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void setDatas(List<MapInfo> datas) {
		if (datas == null || datas.size() == 0)
			return;
		if (mH == null)
			mH = new HashMap<Integer, MapInfo>();
		mH.clear();
		mDatas = datas;
		int len = mDatas.size();
		for (int i = 0; i < len; i++)
			mH.put(i, mDatas.get(i));
		mBitmaps = new Bitmap[len];
		init();
	}

	private void init() {
		if (mDatas == null)
			return;
		int len = mDatas.size();
		for (int i = 0; i < len; i++) {
			MapInfo mi = mH.get(i);
			if (mi == null)
				throw new RuntimeException("mapinfo could not be null");
			mBitmaps[i] = BitmapFactory.decodeResource(getResources(),
					mi.getResIdDef());
		}
		invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (mBitmaps != null) {
			int left = DensityUtil.getScreenWidth(getContext()) / 2
					- mBitmaps[0].getWidth() / 2;
			left=0;
			for (int i = 0; i < mBitmaps.length; i++) {
				canvas.drawBitmap(mBitmaps[i], left, 0, null);
			}
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			which(event.getX(), event.getY());
			onClick(true);
		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			onClick(false);
		}
		return true;
	}

	public void which(float x, float y) {
		if (mBitmaps != null) {
			for (int i = mBitmaps.length - 1; i >= 1; i--) {
				// 判断坐标点不超过图片得宽高
				if ((int) x > mBitmaps[0].getWidth()
						|| (int) y > mBitmaps[0].getHeight()) {
					// clickBitmapListener.ClickBitmap(-1);
					break;
				}
				Bitmap mBitmap = mBitmaps[i];
				// 判断坐标点是否是在图片得透明区域
				if (mBitmap.getPixel((int) x, (int) y) != 0) {
					mCurClickIndex = i;
					mCurMapInfo = mH.get(i);
					Toast.makeText(getContext(), mCurMapInfo.getName(),
							Toast.LENGTH_SHORT).show();
					break;
				}
			}
		}
	}

	private void onClick(boolean du) {
		if (mCurClickIndex == -1 || mCurMapInfo == null)
			return;
		MapInfo mi = mH.get(mCurClickIndex);
		if (mi == null)
			throw new RuntimeException("mapinfo could not be null");
		OnClickListener lis = mi.getClickListener();
		if (du) {
			if (mi.getResIdPress() != -1) {
				mBitmaps[mCurClickIndex].recycle();
				mBitmaps[mCurClickIndex] = BitmapFactory.decodeResource(
						getResources(), mi.getResIdPress());
			}
			if (lis != null)
				lis.onClick(this);
		} else {
			if (mi.getResIdDef() != -1) {
				mBitmaps[mCurClickIndex].recycle();
				mBitmaps[mCurClickIndex] = BitmapFactory.decodeResource(
						getResources(), mi.getResIdDef());
			}
			mCurClickIndex = -1;
			mCurMapInfo = null;
		}
		invalidate();
	}
}