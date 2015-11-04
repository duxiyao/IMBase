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

		int h = (int) (DensityUtil.getScreenHeight() * 0.4); // 高度设置为屏幕的0.6
		int w = (int) (DensityUtil.getScreenWidth() * 0.8); // 宽度设置为屏幕的0.65

		Activity act = KJActivityStack.create().topActivity();

		Dialog d = new Dialog(act, R.style.ccpalertdialog);
		d.requestWindowFeature(Window.FEATURE_NO_TITLE);
		d.setContentView(v);
		Window win = d.getWindow();
		WindowManager.LayoutParams lp = win.getAttributes();
		win.setGravity(Gravity.CENTER);
		lp.width = w;
		lp.height = h;
		// lp.alpha = 0.7f;
		win.setAttributes(lp);
		d.setCanceledOnTouchOutside(false);
		return d;
	}
}
