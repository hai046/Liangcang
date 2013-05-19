package com.liangcang.util;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

public class MyToast {
	public static void showMsgShort(Context mContext, String msg) {
		if (TextUtils.isEmpty(msg))
			return;
		Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
	}

	public static void showMsgShort(Context mContext, int msg) {

		Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
	}

	public static void showMsgLong(Context mContext, String msg) {
		if (TextUtils.isEmpty(msg))
			return;
		Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
	}

	public static void showMsgLong(Context mContext, int msg) {
		if (msg < 0)
			return;
		Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
	}

}
