package com.kjstudy.core.util;

import java.io.IOException;
import java.io.StringReader;

import org.json.JSONObject;

import com.alibaba.fastjson.JSON;

import android.util.JsonReader;

public class JsonUtil {
	public static <T> T json2Obj(String json, Class cls) {
		if (GUtil.isEmpty(json))
			return null;
		return (T) JSON.parseObject(json, cls);
	}

	public static String obj2Json(Object obj) {
		return JSON.toJSONString(obj);
	}

	public static String getString(JSONObject jo, String key) {
		try {
			return jo.getString(key);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static String getString(String json, String key) {
		JsonReader jr = new JsonReader(new StringReader(json));
		try {
			jr.beginObject();
			while (jr.hasNext()) {
				String k = jr.nextName();
				String v = jr.nextString();
				if (key.equals(k)) {
					return v;
				}
			}
			jr.endObject();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
}
