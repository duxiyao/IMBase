package org.kymjs.kjframe.utils;

import org.kymjs.kjframe.ui.KJActivityStack;

import android.content.Intent;

public class BroadCastUtil {
	public static void sendBroadCast(String action) {
		KJActivityStack.create().topActivity()
				.sendBroadcast(new Intent(action));
	}

}
