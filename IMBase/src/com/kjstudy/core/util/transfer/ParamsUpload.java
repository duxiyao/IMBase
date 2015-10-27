package com.kjstudy.core.util.transfer;

import java.io.File;
import java.util.List;

import org.apache.http.NameValuePair;

/**
 * @author duxiyao
 * 
 * 文件上传下载类参数，根据接口文档中接口12、13编码。
 */
public class ParamsUpload {
	public List<NameValuePair> params; 
	/**
	 * 文件上传地址。
	 */
	public String url;
	/**
	 * 要上传的附件。
	 */
	public File file;
	/**
	 * 缩略图。
	 */
	public byte[] fileImg;
}
