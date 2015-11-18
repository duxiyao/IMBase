package com.kjstudy.plugin.gesture_pwd;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.imbase.R;
import com.kjstudy.core.util.DensityUtil;
import com.kjstudy.plugin.gesture_pwd.GestureDrawline.GestureCallBack;

/**
 * 手势密码容器类
 * 
 */
public class GestureContentView extends ViewGroup {

	private int baseNum = 6;

	private int mScreenWidth;
	/**
	 * 每个点区域的宽度
	 */
	private int blockWidth;
	/**
	 * 声明一个集合用来封装坐标集合
	 */
	private List<GesturePoint> list;
	private Context context;
	private boolean isVerify;
	private GestureDrawline gestureDrawline;

	private int mGestureNodeNormal = R.drawable.gesture_node_normal;
	private int mGestureNodePressed = R.drawable.gesture_node_pressed;
	private int mGestureNodeWrong = R.drawable.gesture_node_wrong;

	public int getGestureNodeNormal() {
		return mGestureNodeNormal;
	}

	public void setGestureNodeNormal(int gestureNodeNormal) {
		this.mGestureNodeNormal = gestureNodeNormal;
		changeStyle();
	}

	public int getGestureNodePressed() {
		return mGestureNodePressed;
	}

	public void setGestureNodePressed(int gestureNodePressed) {
		this.mGestureNodePressed = gestureNodePressed;
		changeStyle();
	}

	public int getGestureNodeWrong() {
		return mGestureNodeWrong;
	}

	public void setGestureNodeWrong(int gestureNodeWrong) {
		this.mGestureNodeWrong = gestureNodeWrong;
		changeStyle();
	}

	private void changeStyle() {
		for (GesturePoint gp : list) {
			gp.setGestureNodeNormal(mGestureNodeNormal);
			gp.setGestureNodePressed(mGestureNodePressed);
			gp.setGestureNodeWrong(mGestureNodeWrong);
		}
	}

	/**
	 * 包含9个ImageView的容器，初始化
	 * 
	 * @param context
	 * @param isVerify
	 *            是否为校验手势密码
	 * @param passWord
	 *            用户传入密码
	 * @param gestureCallBack
	 *            手势绘制完毕的回调
	 */
	public GestureContentView(Context context, boolean isVerify,
			String passWord, GestureCallBack gestureCallBack) {
		super(context);
		mScreenWidth = DensityUtil.getScreenWidth();
		blockWidth = mScreenWidth / 3;
		this.list = new ArrayList<GesturePoint>();
		this.context = context;
		this.isVerify = isVerify;
		// 添加9个图标
		addChild();
		// 初始化一个可以画线的view
		gestureDrawline = new GestureDrawline(context, list, isVerify,
				passWord, gestureCallBack);
		gestureDrawline.setDefaultLine(Color.rgb(31, 167, 240));
	}

	private void addChild() {
		for (int i = 0; i < 9; i++) {
			ImageView image = new ImageView(context);
			image.setBackgroundResource(mGestureNodeNormal);
			this.addView(image);
			invalidate();
			// 第几行
			int row = i / 3;
			// 第几列
			int col = i % 3;
			// 定义点的每个属性
			int leftX = col * blockWidth + blockWidth / baseNum;
			int topY = row * blockWidth + blockWidth / baseNum;
			int rightX = col * blockWidth + blockWidth - blockWidth / baseNum;
			int bottomY = row * blockWidth + blockWidth - blockWidth / baseNum;
			GesturePoint p = new GesturePoint(leftX, rightX, topY, bottomY,
					image, i + 1);
			p.setGestureNodeNormal(mGestureNodeNormal);
			p.setGestureNodePressed(mGestureNodePressed);
			p.setGestureNodeWrong(mGestureNodeWrong);
			this.list.add(p);
		}
	}

	public void setParentView(ViewGroup parent) {
		// 得到屏幕的宽度
		int width = mScreenWidth;
		LayoutParams layoutParams = new LayoutParams(width, width);
		this.setLayoutParams(layoutParams);
		gestureDrawline.setLayoutParams(layoutParams);
		parent.addView(gestureDrawline);
		parent.addView(this);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		for (int i = 0; i < getChildCount(); i++) {
			// 第几行
			int row = i / 3;
			// 第几列
			int col = i % 3;
			View v = getChildAt(i);
			v.layout(col * blockWidth + blockWidth / baseNum, row * blockWidth
					+ blockWidth / baseNum, col * blockWidth + blockWidth
					- blockWidth / baseNum, row * blockWidth + blockWidth
					- blockWidth / baseNum);
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		// 遍历设置每个子view的大小
		for (int i = 0; i < getChildCount(); i++) {
			View v = getChildAt(i);
			v.measure(widthMeasureSpec, heightMeasureSpec);
		}
	}

	/**
	 * 保留路径delayTime时间长
	 * 
	 * @param delayTime
	 */
	public void clearDrawlineState(long delayTime) {
		gestureDrawline.clearDrawlineState(delayTime);
	}

}
