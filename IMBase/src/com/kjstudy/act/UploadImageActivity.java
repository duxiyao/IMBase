package com.kjstudy.act;

import org.kymjs.kjframe.KJActivity;

import com.imbase.R;
import com.kjstudy.dialog.DialogAssistant;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 头像上传界面
 * 
 * @author tianzc
 * 
 */
public class UploadImageActivity extends KJActivity {

	private final int FROMALBUM = 1, PHOTO = 2;

	@Override
	public void setRootView() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.consult_upload_headimg_actdia);
		showUploadHeadChoice();
	}

	private void close() {
		this.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				finish();
			}
		});
	}

	private void showUploadHeadChoice() {
		View v = LayoutInflater.from(this).inflate(
				R.layout.layout_dialog_select_headimg, null);
		Dialog d = DialogAssistant.getCustomDialog(v);
		d.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss(DialogInterface dialog) {
				close();
			}
		});
		v.findViewById(R.id.tv_album).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				/* 开启Pictures画面Type设定为image */
				intent.setType("image/*");
				/* 使用Intent.ACTION_GET_CONTENT这个Action */
				intent.setAction(Intent.ACTION_GET_CONTENT);
				/* 取得相片后返回本画面 */
				startActivityForResult(intent, FROMALBUM);
				// v.setEnabled(false);
			}
		});

		v.findViewById(R.id.tv_photo).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(intent, PHOTO);
				// v.setEnabled(false);
			}
		});

		v.findViewById(R.id.cancle).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				close();
			}
		});
		d.show();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// this.setVisible(false);
		switch (requestCode) {
		case FROMALBUM:
			chooseFromAlbum(resultCode, data);
			break;
		case PHOTO:
			photoGraph(resultCode, data);
			break;
		default:
			break;
		}
	}

	/** show进度条 */
	private final int WHAT_LOADING_SHOW = 0;
	/** 结束进度条 */
	private final int WHAT_LOADING_DISS = 1;
	/** 进度条 */
	public Dialog mLoadingDialog;

	@SuppressLint("HandlerLeak")
	private Handler post = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case WHAT_LOADING_SHOW:
				mLoadingDialog = DialogAssistant.getProgressingDialog();
				mLoadingDialog.show();
				break;
			case WHAT_LOADING_DISS:
				if (mLoadingDialog != null) {
					mLoadingDialog.dismiss();
				}
				break;
			default:
				break;
			}
		};
	};

	// private void afterUpload(String fileUrl, ConsultImageUtil iu) {
	// deleteMyPreHead();
	// Bitmap tmp = iu.getBitmap();
	// if (tmp != null){
	// IFileCache ic=CacheFactory.getICache(fileUrl, 2);
	// if(ic!=null&&!ic.isExists()){
	// ic.put(tmp);
	// }
	// }
	// SQLiteManager.getInstance().updateConsultUserInfo(fileUrl);
	// UserSipInfoStorage.getInstance().updatePhotoUrl(Global.getPhoneNum(),
	// fileUrl);
	// }
	//
	// private void deleteMyPreHead(){
	// String url = "";
	// ConsultInfo m = Global.consultUserInfo();
	// if (m != null) {
	// url = m.getPhoto();
	// }
	// if (TextUtils.isEmpty(url))
	// return;
	// IFileCache ic=CacheFactory.getICache(url, 2);
	// if(ic==null||!ic.isExists())
	// return;
	// ic.delete();
	// }
	//
	/**
	 * 用手机拍照。
	 * 
	 * @param resultCode
	 * @param data
	 */
	private void photoGraph(int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			String sdStatus = Environment.getExternalStorageState();
			if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
				Log.i("TestFile",
						"SD card is not avaiable/writeable right now.");
				return;
			}
			Bundle bundle = data.getExtras();
			Bitmap bitmap = (Bitmap) bundle.get("data");
			// try {
			// post.sendEmptyMessage(WHAT_LOADING_SHOW);
			// final ConsultImageUtil iu = new ConsultImageUtil(bitmap);
			// iu.uploadHeadPhoto(new SubmitImgable() {
			// @Override
			// public void onSuccess(String imgId, String fileUrl) {
			// NetUtil.showSuccess();
			// post.sendEmptyMessage(WHAT_LOADING_DISS);
			// afterUpload(fileUrl, iu);
			// close();
			// }
			//
			// @Override
			// public void onFail(String reson, String imgId) {
			// NetUtil.showMsgReqNetError();
			// post.sendEmptyMessage(WHAT_LOADING_DISS);
			// close();
			// }
			// });
			// } catch (Exception e) {
			// post.sendEmptyMessage(WHAT_LOADING_DISS);
			// Log.e("error", e.getMessage());
			// close();
			// }
		}
	}

	//
	/**
	 * 从相册选照片。
	 * 
	 * @param resultCode
	 * @param data
	 */
	private void chooseFromAlbum(int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			Uri uri = data.getData();
			Log.e("uri", uri.toString());
			ContentResolver cr = this.getContentResolver();
			// try {
			// post.sendEmptyMessage(WHAT_LOADING_SHOW);
			// final ConsultImageUtil iu = new ConsultImageUtil(uri, cr);
			// iu.uploadHeadPhoto(new SubmitImgable() {
			// @Override
			// public void onSuccess(String imgId, String fileUrl) {
			// NetUtil.showSuccess();
			// post.sendEmptyMessage(WHAT_LOADING_DISS);
			// afterUpload(fileUrl, iu);
			// close();
			// }
			//
			// @Override
			// public void onFail(String reson, String imgId) {
			// NetUtil.showMsgReqNetError();
			// post.sendEmptyMessage(WHAT_LOADING_DISS);
			// close();
			// }
			// });
			// } catch (Exception e) {
			// Log.e("Exception", e.getMessage(), e);
			// post.sendEmptyMessage(WHAT_LOADING_DISS);
			// close();
			// }
		}
	}

}
