package com.kjstudy.core.util;

import com.alibaba.fastjson.JSON;

public class JsonUtil {
	public static <T> T json2Obj(String json, Class cls) {
		return (T) JSON.parseObject(json, cls);
	}

	public static String obj2Json(Object obj) {
		return JSON.toJSONString(obj);
	}
}
