package com.kjstudy.plugin;

import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.MeasureSpec;

/**
 * @author duxiyao
 * @date 2015年11月2日
 * @description 均匀排列的控件
 */
public class AverageView extends ViewGroup {

	private int mWidth, mHeight, mChildWidthMeasureSpec,
			mChildHeightMeasureSpec;
	private List<View> mVs;
	private int mGap = 10;
	private boolean isSquare = false;
	private int mRowCount = 3;

	public AverageView(Context context) {
		super(context);
	}

	public AverageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public AverageView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	/**
	 * @date 2015年11月2日
	 * @author duxiyao
	 * @description 要显示的view
	 * @param vs
	 * @param rowCount
	 *            每行的个数
	 * @param isSquare
	 *            子view的宽高是否相同
	 */
	public void setViews(List<View> vs, int rowCount, boolean isSquare) {
		removeAllViews();
		this.isSquare = isSquare;
		this.mRowCount = rowCount;
		this.mVs = vs;
		if (mVs != null) {
			for (View v : mVs)
				addView(v);
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		measureChildWidthHeight(widthMeasureSpec, heightMeasureSpec);
		if (mVs != null) {
			int len = mVs.size();
			for (int i = 0; i < len; i++) {
				View v = getChildAt(i);
				v.measure(mChildWidthMeasureSpec, mChildHeightMeasureSpec);
			}
		}

		setMeasuredDimension(mWidth, mHeight);
	}

	private void measureChildWidthHeight(int widthMeasureSpec,
			int heightMeasureSpec) {
		if (mVs != null) {
			int len = mVs.size();
			if (len > 0) {
				View v = getChildAt(0);
				v.measure(widthMeasureSpec, heightMeasureSpec);
				int w = v.getMeasuredWidth();
				int h = v.getMeasuredHeight();
				mWidth = w;
				mChildWidthMeasureSpec = MeasureSpec.makeMeasureSpec(
						(mWidth - (mRowCount + 1) * mGap) / mRowCount, 1);
				if (isSquare)
					mChildHeightMeasureSpec = mChildWidthMeasureSpec;
				else
					mChildHeightMeasureSpec = MeasureSpec.makeMeasureSpec(h, 1);
				v.measure(mChildWidthMeasureSpec, mChildHeightMeasureSpec);
				int row = len / mRowCount;
				if (len % mRowCount != 0)
					row += 1;
				mHeight = (MeasureSpec.getSize(mChildHeightMeasureSpec)) * row
						+ (row + 1) * mGap;
			}
		}

	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int childCount = getChildCount();
		for (int i = 0; i < childCount; i++) {
			View v = getChildAt(i);

			int measureHeight = mChildHeightMeasureSpec;// v.getMeasuredHeight();
			int measuredWidth = mChildWidthMeasureSpec;// v.getMeasuredWidth();
			int curRow = i / mRowCount + 1;
			int curCol = i % mRowCount + 1;

			int x = curCol * mGap + measuredWidth * (curCol - 1);
			int y = mGap * curRow + (curRow - 1) * measureHeight;
			v.layout(x, y, x + measuredWidth, y + measureHeight);
		}
	}
}
