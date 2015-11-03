package com.kjstudy.core.util;

import java.text.SimpleDateFormat;

public class DateUtil {
	static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static String getCommonDate(long millis){
		return format.format(millis);
	}
}
