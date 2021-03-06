package com.zbar.lib.camera;

import com.google.zxing.FormatException;
import com.google.zxing.common.DecoderResult;
import com.google.zxing.datamatrix.DataMatrixReader;
import com.google.zxing.datamatrix.decoder.Decoder;
import com.google.zxing.maxicode.MaxiCodeReader;
import com.kjstudy.core.util.LogUtil;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.hardware.Camera;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * 描述: 相机预览回调
 */
final class PreviewCallback implements Camera.PreviewCallback {

	private static final String TAG = PreviewCallback.class.getSimpleName();

	private final CameraConfigurationManager configManager;
	/** 使用一次预览回调 */
	private final boolean useOneShotPreviewCallback;
	private Handler previewHandler;
	private int previewMessage;

	PreviewCallback(CameraConfigurationManager configManager,
			boolean useOneShotPreviewCallback) {
		this.configManager = configManager;
		this.useOneShotPreviewCallback = useOneShotPreviewCallback;
	}

	void setHandler(Handler previewHandler, int previewMessage) {
		this.previewHandler = previewHandler;
		this.previewMessage = previewMessage;
	}

	public void onPreviewFrame(byte[] data, Camera camera) { 
		Point cameraResolution = configManager.getCameraResolution();
		if (!useOneShotPreviewCallback) {
			camera.setPreviewCallback(null);
		}
		if (previewHandler != null) {
			Message message = previewHandler.obtainMessage(previewMessage,
					cameraResolution.x, cameraResolution.y, data);
			message.sendToTarget();
			previewHandler = null;
		} else {
			Log.d(TAG, "Got preview callback, but no handler for it");
		}
	}

}
