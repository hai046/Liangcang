package com.liangcang.base;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;

import cn.sharesdk.framework.AbstractWeibo;

import com.alibaba.fastjson.JSON;
import com.igexin.slavesdk.MessageManager;
import com.igexin.slavesdk.MessageManagerObserver;
import com.liangcang.managers.ColorManager;
import com.liangcang.managers.DataManager;
import com.liangcang.mode.MessageNum;
import com.liangcang.mode.User;
import com.liangcang.stores.Settings;
import com.liangcang.util.MyLog;
import com.liangcang.webUtil.WebDataManager;

public class MyApplication extends Application {

	@Override
	public Resources getResources() {

		return super.getResources();
	}

	@Override
	public void onCreate() {
		super.onCreate();
		DataManager.getInstance(this);
		initUser();
		MessageManager.getInstance().initialize(getApplicationContext());
		MessageManager.getInstance().setObserver(new MessageManagerObserver() {
			@Override
			public void onData(byte[] arg0) {
				MyLog.e("hhh", "setObserver=" + new String(arg0));
			}
		});
		AbstractWeibo.initSDK(this, "3e9472cd844");
		ColorManager.getInsance().init(this);
	}

	public boolean isLogined() {
		return user != null;
	}

	private static User user = null;

	public static User getUser() {
		return user;
	}

	public static String storeAuthority = "authority";

	

	public void setUser(User t) {
		MyApplication.user = t;
		try {
			String store = URLEncoder.encode(JSON.toJSONString(t), "utf-8");
			WebDataManager.getInsance().initUser(t);
			MyLog.d("setUser", store);
			Settings.getInstance(this).putString(storeAuthority, store);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

	}

	// private String getTestData() {
	//
	// return
	// "{\"platform\":{\"douban\":0,\"qq\":0,\"sina\":0},\"sig\":\"0445f101b22c25145deb6f2f1c1b94b4\",\"template_id\":1,\"user_id\":\"6933\",\"user_image\":\"http://www.iliangcang.com/images/default/default30.png\",\"user_name\":\"hai046\"}";
	// }

	private void initUser() {

		String userStr = /* getTestData(); *///
		Settings.getInstance(this).getString(storeAuthority, null);
		if (!TextUtils.isEmpty(userStr)) {
			try {
				String store = URLDecoder.decode(userStr, "utf-8");
				MyApplication.user = JSON.parseObject(store, User.class);
				WebDataManager.getInsance().initUser(user);
				MyLog.e("setUser", "user=" + store);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public static void clearUser(Context mContext) {
		user = null;
		WebDataManager.getInsance().initUser(null);
		Settings.getInstance(mContext).putString(storeAuthority, null);
		;// .getString(storeAuthority, null);
		Settings.getInstance(mContext).clear();
	}

	public static void setMessageNum(MessageNum messageNum) {
		// TODO Auto-generated method stub

	}

	public static boolean isLogin() {

		return user != null;
	}

}
