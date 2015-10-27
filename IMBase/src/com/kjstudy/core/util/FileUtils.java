/* 
 * Copyright (C) 2007-2008 OpenIntents.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.kjstudy.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.zip.ZipFile;

import android.content.Context;
import android.net.Uri;
import android.provider.MediaStore.Audio;
import android.provider.MediaStore.Video;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.text.format.Formatter;
import android.util.Log;

public class FileUtils {
	
	/** TAG for log messages. */
	static final String TAG = "FileUtils";
	private static final int X_OK = 1;
	
	private static boolean libLoadSuccess;
	
	static {
		try {
			System.loadLibrary("access");
			libLoadSuccess = true;
		} catch(UnsatisfiedLinkError e) {
			libLoadSuccess = false;
			Log.d(TAG, "libaccess.so failed to load.");
		}
	}

    /**
     * use it to calculate file count in the directory recursively
     */
    private static int fileCount = 0;

	/**
	 * Whether the filename is a video file.
	 * 
	 * @param filename
	 * @return
	 *//*
	public static boolean isVideo(String filename) {
		String mimeType = getMimeType(filename);
		if (mimeType != null && mimeType.startsWith("video/")) {
			return true;
		} else {
			return false;
		}
	}*/

	/**
	 * Whether the URI is a local one.
	 * 
	 * @param uri
	 * @return
	 */
	public static boolean isLocal(String uri) {
		if (uri != null && !uri.startsWith("http://")) {
			return true;
		}
		return false;
	}
	
	/***
	 * 判断文件夹是否存在
	 * 
	 * @param path 文件路径
	 */
	public boolean isFileExists(String path) {
		File mFile = new File(path);
		if (!mFile.exists()) {
			return false;
		}
		return true;
	}
	
	/***
	 * 判断文件夹是否存在,不存在生成
	 * 
	 * @param path 文件路径
	 */
	public void createMkdirsFile(String path){
		File mFile = new File(path);
		if (!mFile.exists()) {
			mFile.mkdirs();
		}
	}

	/**
	 * 判断有名有同名的文件
	 * @param url 文件名
	 * @param path 文件路径
	 */
	public boolean isSameFile(String path,String url) {
		File mFile = new File(path + url);
		if(mFile.exists()){
			return true;
		}else{
			return false;
		}
	}
	

	/**
	 * Gets the extension of a file name, like ".png" or ".jpg".
	 * 
	 * @param uri
	 * @return Extension including the dot("."); "" if there is no extension;
	 *         null if uri was null.
	 */
	public static String getExtension(String uri) {
		if (uri == null) {
			return null;
		}

		int dot = uri.lastIndexOf(".");
		if (dot >= 0) {
			return uri.substring(dot);
		} else {
			// No extension.
			return "";
		}
	}

	/**
	 * Returns true if uri is a media uri.
	 * 
	 * @param uri
	 * @return
	 */
	public static boolean isMediaUri(String uri) {
		if (uri.startsWith(Audio.Media.INTERNAL_CONTENT_URI.toString())
				|| uri.startsWith(Audio.Media.EXTERNAL_CONTENT_URI.toString())
				|| uri.startsWith(Video.Media.INTERNAL_CONTENT_URI.toString())
				|| uri.startsWith(Video.Media.EXTERNAL_CONTENT_URI.toString())) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Convert File into Uri.
	 * @param file
	 * @return uri
	 */
	public static Uri getUri(File file) {
		if (file != null) {
			return Uri.fromFile(file);
		}
		return null;
	}
	
	/**
	 * Convert Uri into File.
	 * @param uri
	 * @return file
	 */
	public static File getFile(Uri uri) {
		if (uri != null) {
			String filepath = uri.getPath();
			if (filepath != null) {
				return new File(filepath);
			}
		}
		return null;
	}
	
	/**
	 * Returns the path only (without file name).
	 * @param file
	 * @return
	 */
	public static File getPathWithoutFilename(File file) {
		 if (file != null) {
			 if (file.isDirectory()) {
				 // no file to be split off. Return everything
				 return file;
			 } else {
				 String filename = file.getName();
				 String filepath = file.getAbsolutePath();
	  
				 // Construct path without file name.
				 String pathwithoutname = filepath.substring(0, filepath.length() - filename.length());
				 if (pathwithoutname.endsWith("/")) {
					 pathwithoutname = pathwithoutname.substring(0, pathwithoutname.length() - 1);
				 }
				 return new File(pathwithoutname);
			 }
		 }
		 return null;
	}

	/**
	 * Constructs a file from a path and file name.
	 * 
	 * @param curdir
	 * @param file
	 * @return
	 */
	public static File getFile(String curdir, String file) {
		String separator = "/";
		  if (curdir.endsWith("/")) {
			  separator = "";
		  }
		   File clickedFile = new File(curdir + separator
		                       + file);
		return clickedFile;
	}
	
	public static File getFile(File curdir, String file) {
		return getFile(curdir.getAbsolutePath(), file);
	}
	
	public static String formatSize(Context context, long sizeInBytes) {
		return Formatter.formatFileSize(context, sizeInBytes);
	}
	
	public static String formatDate(Context context, long dateTime) {
		return DateFormat.getDateFormat(context).format(new Date(dateTime));
	}

    public static int getFileCount(File file){
        fileCount = 0;
        calculateFileCount(file);
        return fileCount;
    }

    /**
     * @param f  - file which need be checked
     * @return if is archive - returns true othewise
     */
    public static boolean checkIfZipArchive(File f){
        try {
            new ZipFile(f);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    private static void calculateFileCount(File file){
        if (!file.isDirectory()){
            fileCount++;
            return;
        }
        if (file.list() == null){
            return;
        }
        for (String fileName: file.list()){
            File f = new File(file.getAbsolutePath()+File.separator+fileName);
            calculateFileCount(f);
        }
    }    
	
	/**
	 * Native helper method, returns whether the current process has execute privilages.
	 * @param a File
	 * @return returns TRUE if the current process has execute privilages.
	 */
	public static boolean canExecute(File mContextFile) {
		try {
			// File.canExecute() was introduced in API 9.  If it doesn't exist, then
			// this will throw an exception and the NDK version will be used.
			Method m = File.class.getMethod("canExecute", new Class[] {} );
			Boolean result=(Boolean)m.invoke(mContextFile);
			return result;
		} catch (Exception e) {
			if(libLoadSuccess){
				return access(mContextFile.getPath(), X_OK);
			} else {
				return false;
			}
		}
	}
	
	// Native interface to unistd.h's access(*char, int) method.
	public static native boolean access(String path, int mode);
	
	/**
	 * 复制文件
	 * 
	 * @param srcPath
	 *            源文件绝对路径
	 * @param dirPath
	 *            目标文件所在目录
	 * @return boolean
	 */
	public static boolean copyFile(String srcPath, String dirPath,
			String fileName) {
		boolean flag = false;

		File srcFile = new File(srcPath);
		if (!srcFile.exists()) { // 源文件不存在
			System.out.println("源文件不存在");
			return false;
		}
		// 获取待复制文件的文件名
		if (TextUtils.isEmpty(fileName)) {
			fileName = srcPath.substring(srcPath.lastIndexOf(File.separator));
		}
		String destPath = dirPath + fileName;
		if (destPath.equals(srcPath)) { // 源文件路径和目标文件路径重复
			System.out.println("源文件路径和目标文件路径重复!");
			return false;
		}
		File destFile = new File(destPath);
		if (destFile.exists() && destFile.isFile()) { // 该路径下已经有一个同名文件
			System.out.println("目标目录下已有同名文件!");
			return false;
		}

		File destFileDir = new File(dirPath);
		destFileDir.mkdirs();
		try {
			FileInputStream fis = new FileInputStream(srcPath);
			FileOutputStream fos = new FileOutputStream(destFile);
			byte[] buf = new byte[1024];
			int c;
			while ((c = fis.read(buf)) != -1) {
				fos.write(buf, 0, c);
			}
			fis.close();
			fos.close();

			flag = true;
		} catch (IOException e) {
			//
		}

		if (flag) {
			System.out.println("复制文件成功!");
		}

		return flag;
	}
	
	public static void move(String srcFilePath, String destFilePath) {
		String fileName="";
		String dirPath="";
		try{
			fileName=destFilePath.substring(destFilePath.lastIndexOf("/")+1, destFilePath.length());
			dirPath=destFilePath.substring(0, destFilePath.lastIndexOf("/")+1);
		}catch(Exception e){
			e.printStackTrace();
			return;
		}
		if (copyFile(srcFilePath, dirPath, fileName)) {
			File file = new File(srcFilePath);
			try {
				file.delete();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
