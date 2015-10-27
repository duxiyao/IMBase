package com.kjstudy.core.util.transfer;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author duxiyao
 * 
 *         下载文件类。
 */
public class Download extends AbstractTransfered {
	private String downUrl, dirPath;

	public Download(String downUrl, String dirPath) {
		this.downUrl = downUrl;
		this.dirPath = dirPath;
	}

	private String getFileName() {
		if (null == downUrl)
			return null;
		String[] tmp = downUrl.split("/");
		if (tmp.length <= 0)
			return null;
		String name = tmp[tmp.length - 1];
		if (name.startsWith("imFileDownload?fileName=")) {
			name = name.replace("imFileDownload?fileName=", "");
		}
		return name;
	}

	public void download(ProgressListener listener) {
		try {
			String fileName = getFileName();
			if (null == dirPath || null == fileName)
				throw new Exception("lllegal dirPath or fileName");

			File f = new File(dirPath + fileName);
			// if (f.exists()) {
			// if (null != listener)
			// listener.transferred(100L);
			// // throw new Exception("file exists");
			// }
			f.setLastModified(System.currentTimeMillis());
			int downloadSize = 0;
			URL u = new URL(downUrl);
			URLConnection conn = u.openConnection();
			conn.connect();

			// Map<String,List<String>> tmpmap = conn.getHeaderFields();
			// String fileName = conn.getHeaderField(6);
			// fileName =
			// URLDecoder.decode(fileName.substring(fileName.indexOf("filename=")+9),"UTF-8");
			// System.out.println("文件名为："+fileName);

			InputStream is = conn.getInputStream();
			int fileSize = conn.getContentLength();
			if (fileSize < 1 || is == null) {
				// sendMessage(DOWNLOAD_ERROR);
				throw new Exception("lllegal");
			} else {
				saveFromInputStream(is, f);
				is.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (null != listener)
				listener.onResponse(false,"", e);
		}
	}

	@Override
	public void exe(ProgressListener listener) {
		download(listener);
	}

	private boolean saveFromInputStream(InputStream content, File f) {
		try {
			FileOutputStream out = new FileOutputStream(f);

			byte[] b = new byte[131072];
			int len = 0;
			while ((len = content.read(b)) != -1) {
				out.write(b, 0, len);
			}
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
