package com.kjstudy.test.view;

import android.view.View.OnClickListener;

public class MapInfo {
	private String name;
	private int resIdDef;
	private int resIdPress;
	private OnClickListener clickListener;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getResIdDef() {
		return resIdDef;
	}

	public void setResIdDef(int resIdDef) {
		this.resIdDef = resIdDef;
	}

	public int getResIdPress() {
		return resIdPress;
	}

	public void setResIdPress(int resIdPress) {
		this.resIdPress = resIdPress;
	}

	public OnClickListener getClickListener() {
		return clickListener;
	}

	public void setClickListener(OnClickListener clickListener) {
		this.clickListener = clickListener;
	}
}
