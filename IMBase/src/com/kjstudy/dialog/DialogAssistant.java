package com.kjstudy.dialog;

import org.kymjs.kjframe.ui.KJActivityStack;
import org.kymjs.kjframe.utils.DensityUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.ContextThemeWrapper;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.imbase.MyApplication;
import com.imbase.R;
import com.kjstudy.core.util.DensityUtil;

public class DialogAssistant {

	public static Dialog getProgressingDialog() {
		int h = (int) (DensityUtil.getScreenHeight() * 0.5); // 高度设置为屏幕的0.6
		int w = (int) (DensityUtil.getScreenWidth() * 0.8); // 宽度设置为屏幕的0.65

		Activity act = KJActivityStack.create().topActivity();
		View v = LayoutInflater.from(act).inflate(
				R.layout.custom_progress_dialog, null);

		Dialog d = new Dialog(act, R.style.CustomProgressDialog);
		d.setContentView(v);
		Window win = d.getWindow();
		WindowManager.LayoutParams lp = win.getAttributes();
		win.setGravity(Gravity.CENTER);

		ImageView loadingProfress = (ImageView) v
				.findViewById(R.id.loading_progress);
		Animation animation = AnimationUtils.loadAnimation(act,
				R.anim.loading_progress_animation);
		loadingProfress.setAnimation(animation);

		// lp.width = h;
		// lp.height = w;
		// lp.alpha = 0.7f;
		win.setAttributes(lp);
		d.setCanceledOnTouchOutside(false);
		return d;
	}

	public static Dialog getCustomDialog(View v) {

		int h = (int) (DensityUtil.getScreenHeight() * 0.5); // 高度设置为屏幕的0.6
		int w = (int) (DensityUtil.getScreenWidth() * 0.8); // 宽度设置为屏幕的0.65

		// Activity act = KJActivityStack.create().topActivity();
		//
		// Dialog d = new Dialog(act, R.style.ccpalertdialog);
		// d.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// d.setContentView(v);
		// Window win = d.getWindow();
		// WindowManager.LayoutParams lp = win.getAttributes();
		// win.setGravity(Gravity.CENTER);
		// lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
		// lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
		// // lp.alpha = 0.7f;
		// win.setAttributes(lp);

		Dialog d = getDialog(R.style.ccpalertdialog, v, w, h, Gravity.CENTER,
				-1, -1, false, 1f);
		d.setCanceledOnTouchOutside(false);
		d.setCancelable(true);
		return d;
	}

	public static Dialog getPwdDialog(View v) {

		int h = (int) (DensityUtil.getScreenHeight());
		int w = (int) (DensityUtil.getScreenWidth());

		Dialog d = getDialog(R.style.ccpalertdialog, v, w, h, Gravity.LEFT
				| Gravity.TOP, -1, -1, false, 0.8f);
		return d;
	}

	public static Dialog getDialog(int style, View v, int w, int h,
			int gravity, int x, int y, boolean hasBar, float alpha) {

		Activity act = KJActivityStack.create().topActivity();

		Dialog d = new Dialog(act, style);
		if (!hasBar)
			d.requestWindowFeature(Window.FEATURE_NO_TITLE);
		d.setContentView(v);
		Window win = d.getWindow();
		WindowManager.LayoutParams lp = win.getAttributes();
		win.setGravity(gravity);
		lp.width = w;
		lp.height = h;
		if (x != -1)
			lp.x = x;
		if (y != -1)
			lp.y = y;
		lp.alpha = alpha;
		win.setAttributes(lp);
		d.setCanceledOnTouchOutside(false);
		d.setCancelable(false);
		return d;
	}
}
