package com.kjstudy.core.util;

import android.text.TextUtils;

public class GUtil {

	public static boolean isEmpty(String s) {
		return TextUtils.isEmpty(s) || TextUtils.isEmpty(s.trim());

	}
}
