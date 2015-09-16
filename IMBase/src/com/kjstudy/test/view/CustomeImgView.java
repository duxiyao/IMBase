package com.kjstudy.test.view;

import org.kymjs.kjframe.utils.KJLoger;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.imbase.R;

public class CustomeImgView extends View {
	private Bitmap[] mBitmaps = new Bitmap[10];
	private OnClickBitmapListener clickBitmapListener;
	private int mCurClickIndex = -1;

	public CustomeImgView(Context context) {
		super(context);
		// mBitmaps[0] = BitmapFactory.decodeResource(getResources(),
		// R.drawable.tt);
		// mBitmaps[1] = BitmapFactory
		// .decodeResource(getResources(), R.drawable.t);
		init();
	}

	public CustomeImgView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// mBitmaps[1] = BitmapFactory
		// .decodeResource(getResources(), R.drawable.t);
		// mBitmaps[0] = BitmapFactory.decodeResource(getResources(),
		// R.drawable.tt);
		init();
	}

	private void init() {
		mBitmaps[0] = BitmapFactory.decodeResource(getResources(),
				R.drawable.man_front);
		mBitmaps[1] = BitmapFactory.decodeResource(getResources(),
				R.drawable.head);
		mBitmaps[2] = BitmapFactory.decodeResource(getResources(),
				R.drawable.neck);
		mBitmaps[3] = BitmapFactory.decodeResource(getResources(),
				R.drawable.chest);
		mBitmaps[4] = BitmapFactory.decodeResource(getResources(),
				R.drawable.arm);
		mBitmaps[5] = BitmapFactory.decodeResource(getResources(),
				R.drawable.hand);
		mBitmaps[6] = BitmapFactory.decodeResource(getResources(),
				R.drawable.abdomen);
		mBitmaps[7] = BitmapFactory.decodeResource(getResources(),
				R.drawable.genitals);
		mBitmaps[8] = BitmapFactory.decodeResource(getResources(),
				R.drawable.leg);
		mBitmaps[9] = BitmapFactory.decodeResource(getResources(),
				R.drawable.foot);
	}

	public CustomeImgView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	/**
	 * 设置图片
	 * 
	 * @date 2013-10-12
	 */
	public final void setBitmaps(Bitmap[] bitmap) {
		this.mBitmaps = bitmap;
		invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		if (mBitmaps != null) {
			for (int i = 0; i < mBitmaps.length; i++) {
				canvas.drawBitmap(mBitmaps[i], 0, 0, null);
			}
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int me = event.getAction();
		KJLoger.debug("action---" + me + "");

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
					Toast.makeText(getContext(), "" + i, Toast.LENGTH_SHORT)
							.show();
					// clickBitmapListener.ClickBitmap(i);
					break;
				}
			}
		}
	}

	private void onClick(boolean du) {
		if(mCurClickIndex==-1)
			return;
		if (du) {
			mBitmaps[mCurClickIndex] = BitmapFactory.decodeResource(
					getResources(), R.drawable.leg_click);
		} else {
			mBitmaps[mCurClickIndex] = BitmapFactory.decodeResource(
					getResources(), R.drawable.leg);
			mCurClickIndex = -1;
		}
		invalidate();
	}

	public final void setOnClickBitmapListener(OnClickBitmapListener listener) {
		this.clickBitmapListener = listener;
	};

	public static interface OnClickBitmapListener {
		/**
		 * @param index
		 *            -1表示超出范围
		 * @date 2013-10-12
		 */
		void ClickBitmap(int index);
	}
}
