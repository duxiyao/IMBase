package com.kjstudy.core.io;

import java.io.File;

import android.os.Environment;

public class FileAccessor {

	public static final String APP_NAME = "sinldo";
	public static final String APPS_ROOT_DIR = getExternalStorePath() + "/"
			+ APP_NAME;
	public static final String CRASH_PATH = APPS_ROOT_DIR + "/Log";
	public static final String DB_FILENAME_BEFORE_LOGIN = "login_state.db";
	public static final String DBNAME = "test.db";
	public static final String DBPATH = "sdcard";
	public static final String QRCODE_PATH = "/qrcode";

	/**
	 * 是否有外存卡
	 * 
	 * @return
	 */
	public static boolean isExistExternalStore() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * /sdcard
	 * 
	 * @return
	 */
	public static String getExternalStorePath() {
		if (isExistExternalStore()) {
			return Environment.getExternalStorageDirectory().getAbsolutePath();
		}
		return null;
	}

	public static File creatFile(String filename) {
		String rootPath = FileAccessor.CRASH_PATH;
		File dir = new File(rootPath);
		try {
			if (!dir.exists()) {
				dir.mkdirs();
			}
			File f = new File(rootPath + File.separator + filename);
			if (!f.exists()) {
				f.createNewFile();
			}
			return f;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 检查文件是否存在
	 * 
	 * @return
	 */
	public static boolean checkDir(String cacheDir) {
		File f = new File(cacheDir);
		if (!f.exists()) {
			return f.mkdirs();
		}
		return true;
	}
}
