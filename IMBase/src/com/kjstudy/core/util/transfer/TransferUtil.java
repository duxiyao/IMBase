package com.kjstudy.core.util.transfer;

import java.io.File;

import android.text.TextUtils;

/**
 * @author duxiyao
 * 
 *         文件上传下载工具类。
 */
public class TransferUtil {

	/**
	 * 上传附件。
	 * 
	 * @param params
	 * @param listener
	 */
	public static void upload(ParamsUpload params,
			ProgressListener listener) {
		File f = params.file;
		if(f==null){
			if (null != listener)
				listener.onResponse(false,"", new Exception("lllegal"));
			return;
		}
		if (!f.exists() || !f.canRead()) {
			if (null != listener)
				listener.onResponse(false,"", new Exception("lllegal"));
			return;
		}
		// AbstractTransfered upload = new Upload(params);
		AbstractTransfered upload = new UploadGet(params);
		exe(upload, listener);
	}

	/**
	 * 下载附件
	 * 
	 * @param downUrl
	 * @param dirPath
	 * @param listener
	 */
	public static void download(String downUrl, String dirPath,
			ProgressListener listener) {

		if(TextUtils.isEmpty(dirPath)){
			if (null != listener)
				listener.onResponse(false,"", new Exception("lllegal"));
			return;
		}
			
		File f = new File(dirPath);
		if (!f.exists())
			f.mkdirs();
		if (!f.canWrite())
			throw new FileCannotWriteException();

		AbstractTransfered download = new Download(downUrl, dirPath);
		exe(download, listener);
	}

	private static void exe(final AbstractTransfered transfered,
			final ProgressListener listener) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				transfered.exe(listener);
			}
		}).start();
	}

	// Bitmap转换成byte[]
	// public byte[] Bitmap2Bytes(Bitmap bm) {
	// ByteArrayOutputStream baos = new ByteArrayOutputStream();
	// bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
	// return baos.toByteArray();
	// }
}
