package com.liangcang.util;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.Uri;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import com.alibaba.fastjson.JSON;
import com.liangcang.GoodCommentActivity;
import com.liangcang.ItemDetailActivity;
import com.liangcang.LoginActivity;
import com.liangcang.RecommendActivity;
import com.liangcang.RegisterActivity;
import com.liangcang.activits.UserActivity;
import com.liangcang.base.ModeCallBack;
import com.liangcang.base.MyApplication;
import com.liangcang.managers.DataCallBack;
import com.liangcang.managers.DataManager;
import com.liangcang.menus.MenuActivity;
import com.liangcang.mode.Good;
import com.liangcang.mode.MessageNum;

public class Util {

	public static String saveTempFile(String content, Context mContext) {
		if (content == null || content.length() < 5) {
			try {
				mContext.getFileStreamPath("temp_web").delete();
			} catch (Exception e) {
				e.printStackTrace();
			}
			// MyLog.e( "util", "content=" + content );
			return null;

		}
		try {
			FileOutputStream output = mContext.openFileOutput("temp_web",
					Context.MODE_PRIVATE);
			BufferedOutputStream bos = new BufferedOutputStream(output);
			ByteArrayInputStream is = new ByteArrayInputStream(
					content.getBytes());
			byte buffer[] = new byte[1024];
			int flag = -1;
			while ((flag = is.read(buffer)) != -1) {
				bos.write(buffer, 0, flag);
			}
			bos.flush();
			bos.close();
			is.close();
			output.close();
			// output.write( content.getBytes( ) );
			content = mContext.getFileStreamPath("temp_web").getPath();
			// MyLog.d( "Util", "path=" + content );
			return content;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static String getMD5Str(String str) {
		// MessageDigest digest;
		// try {
		// digest = java.security.MessageDigest.getInstance("MD5");
		// digest.update(str.getBytes());
		// byte messageDigest[] = digest.digest();
		// // Create Hex String
		// StringBuffer hexString = new StringBuffer();
		// for (int i = 0; i < messageDigest.length; i++)
		// hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
		// return hexString.toString();
		// } catch (NoSuchAlgorithmException e) {
		//
		// e.printStackTrace();
		// }
		// return null;
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(str.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		byte[] byteArray = messageDigest.digest();
		StringBuffer md5StrBuff = new StringBuffer();

		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(
						Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}
		return md5StrBuff.toString();
	}

	public static boolean isConnectedNetWork(Context mContext) {
		ConnectivityManager connectivityManager = (ConnectivityManager) mContext
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mobileInfo = connectivityManager
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		NetworkInfo wifiInfo = connectivityManager
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI)/* .getState( ) */;
		if (mobileInfo != null
				&& (mobileInfo.getState() == State.CONNECTED || mobileInfo
						.getState() == State.CONNECTING)) {
			return true;
		}
		if (wifiInfo != null
				&& (wifiInfo.getState() == State.CONNECTED || wifiInfo
						.getState() == State.CONNECTING)) {
			return true;
		}
		return false;
	}

	public static void gotoItemDetail(Context mContext, Good good) {
		Intent intent = new Intent();
		ItemDetailActivity.mGood = good;
		intent.setClass(mContext, ItemDetailActivity.class);
		mContext.startActivity(intent);

	}

	public static void gotoLogin(Context mContext) {
		Intent intent = new Intent();
		intent.setClass(mContext, LoginActivity.class);
		mContext.startActivity(intent);

	}

	public static void gotoRegister(Context mContext) {
		Intent intent = new Intent();
		intent.setClass(mContext, RegisterActivity.class);
		mContext.startActivity(intent);

	}

	public static void gotoComment(Context mContext, String string) {
		Intent intent = new Intent();
		intent.setClass(mContext, RecommendActivity.class);
		mContext.startActivity(intent);

	}

	public static int getDisplayWindth(Context mContext) {
		DisplayMetrics mDisplayMetrics = mContext.getResources()
				.getDisplayMetrics();
		return mDisplayMetrics.widthPixels;
	}

	public static void gotoBuy(Context mContext, String buyUrl) {
		// Intent intent = new Intent();
		// intent.setClass(mContext, BuyWebActivity.class);
		// intent.putExtra(BuyWebActivity.PATH, buyUrl);
		// mContext.startActivity(intent);y
		if (TextUtils.isEmpty(buyUrl)) {

			MyToast.showMsgLong(mContext, "没有购买地址");
			return;
		}
		Uri uri = Uri.parse(buyUrl);
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		mContext.startActivity(intent);

	}

	public static boolean checkEmail(String mail) {
		String regex = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(mail);
		return m.find();
	}

	public static void gotoMain(Context mContext) {
		Intent intent = new Intent();
		intent.setClass(mContext, MenuActivity.class);
		mContext.startActivity(intent);
	}

	public static void gotoGoodComment(Context mContext, Good good) {
		Intent intent = new Intent();
		intent.putExtra(GoodCommentActivity.GOODS_ID, good.getGoods_id());
		intent.putExtra(GoodCommentActivity.GOODS_IMAGE, good.getGoods_image());
		intent.putExtra(GoodCommentActivity.COMMENT_COUNT, good.getMsg_count());
		intent.setClass(mContext, GoodCommentActivity.class);
		mContext.startActivity(intent);

	}

	public static void gotoUser(Context mContext, String user_id,
			String user_image, String user_name) {
		Intent intent = new Intent(mContext, UserActivity.class);
		// intent.setAction(Intent.ACTION_VIEW);intent.setFlags(Intent);
		intent.putExtra(UserActivity.USERID, user_id);
		// intent.setClass(mContext, UserActivity.class);
		// intent.setClassName(mContext, "com.liangcang.menus.UserActivity");
		mContext.startActivity(intent);
		MyLog.e("gotoUser", "user_id=" + user_id + "  user_name=" + user_name);

	}

	public static void doLiked(final Context mContext, boolean toLike,
			String goods_id) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("type", toLike ? "1" : "0");
		params.put("goods_id", goods_id);
		DataManager.getInstance(mContext).doPost("like/action", params,
				new DataCallBack<String>() {

					@Override
					public void success(String t) {
						MyLog.d("doLiked", "String t=" + t);
					}

					@Override
					public void failure(String msg) {
						MyToast.showMsgLong(mContext, msg);

					}
				});
	}

	public static void getMessageNum(Context mContext,
			final ModeCallBack<MessageNum> callBack) {
		DataManager.getInstance(mContext).getData("notificationcount", null,
				true, new DataCallBack<String>() {

					@Override
					public void success(String t) {
						if (TextUtils.isEmpty(t) == false) {
							MessageNum messageNum = JSON.parseObject(t,
									MessageNum.class);
							MyApplication.setMessageNum(messageNum);
							if (callBack != null) {
								callBack.onCallBack(messageNum);
							}
						}
					}

					@Override
					public void failure(String msg) {

					}
				});

	}

}
