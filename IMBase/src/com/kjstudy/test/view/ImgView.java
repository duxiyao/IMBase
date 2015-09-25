package com.kjstudy.test.view;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

@SuppressLint("NewApi")
public class ImgView extends View {

	private List<String> mUrls;
	private List<Bitmap> mImgs;
	private int mImgCount;
	private int mWidgh, mHeight;
	private float mGap = 5;
	private final int mRowCount = 3;

	public ImgView(Context context) {
		super(context);
		init();
	}

	public ImgView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public ImgView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		this.addOnLayoutChangeListener(new OnLayoutChangeListener() {

			@Override
			public void onLayoutChange(View v, int left, int top, int right,
					int bottom, int oldLeft, int oldTop, int oldRight,
					int oldBottom) {
				mWidgh = right - left;
				mHeight = bottom - top;
			}
		});
	}

	public void setImgs(List<String> urls) {
		if (urls == null)
			return;
		mUrls = urls;
		mImgCount = mUrls.size();
		mImgs = new ArrayList<Bitmap>();
		for (String url : mUrls) {
			downimg(url);
		}
	}

	private void downimg(final String url) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// download imgs
				Bitmap bmp = null;

				// after img downloaded
				if (bmp != null)
					mImgs.add(bmp);
				postInvalidate();
			}
		}).start();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		drawImgs(canvas);
	}

	private synchronized void drawImgs(Canvas canvas) {

		int count = mImgs.size();
		for (int i = 0; i < count; i++) {

		}
	}

//	private float getRowOffset(int pos, int curCount) {
//		float w=0;
//		if(curCount>=3)
//			w=(mWidgh-4*mGap)/3f;
//		else
//			w=(mWidgh-mGap*(curCount+1))/curCount;
//		int row = pos / mRowCount;
//		(row+1)*mGap+(row*)
//		if(row==0)
//			return mGap;
//		else{
//			return row*mGap;
//		}
//	}

//	private float getColOffset(int pos, int curCount) {
//		float h=0;
//		if(curCount>=3)
//			h=(mWidgh-4*mGap)/3f;
//		else
//			h=(mWidgh-mGap*(curCount+1))/curCount;
//		int col = pos % mRowCount;
//
//	}
	
//	http://202.106.149.197:8083/icallImgs/20150907111906808.jpg
//		http://202.106.149.197:8083/icallImgs/20150428102022735.jpg
//		http://202.106.149.197:8083/icallImgs/20150506061834330.jpg
//		http://202.106.149.197:8083/icallImgs/20150206071226060.jpg

}
