package com.liangcang;

import java.util.HashMap;

import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import cn.sharesdk.framework.AbstractWeibo;
import cn.sharesdk.framework.WeiboActionListener;
import cn.sharesdk.sina.weibo.SinaWeibo;

import com.liangcang.base.BaseActivity;
import com.liangcang.base.MyApplication;
import com.liangcang.managers.DataCallBack;
import com.liangcang.managers.DataManager;
import com.liangcang.mode.User;
import com.liangcang.util.MyLog;
import com.liangcang.util.MyToast;
import com.liangcang.util.Util;

public class LoginActivity extends BaseActivity implements Callback,
		WeiboActionListener {

	private EditText etUserName, etPasswd;
	String TAG = "LoginActivity";
	private Handler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		handler = new Handler(this);
		setContentView(R.layout.login);
		findViewById(R.id.login).setOnClickListener(mOnClickListener);
		etUserName = (EditText) findViewById(R.id.userName);
		etPasswd = (EditText) findViewById(R.id.password);

		setRightImage(R.drawable.selector_back);
		hideRightBtn2();
		findViewById(R.id.loginApp_weibo).setOnClickListener(mOnClickListener);
		AbstractWeibo.initSDK(this, "3e9472cd844");
	}

	private OnClickListener mOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.loginApp_weibo:
				// b39b7a7db35f38276b8827c482df2aa0
				// loginToSinaServe(/*"b39b7a7db35f38276b8827c482df2aa0",
				// "1"*/);
				// loginBySinaWeibo();
				loginSinaWeibo();
				break;
			case R.id.login:
				login();
				break;

			default:
				break;
			}

		}
	};

	private DataCallBack<User> taobaoDataCallBack = new DataCallBack<User>() {

		public void success(User t) {
			MyApplication mMyApplication = (MyApplication) getApplication();
			mMyApplication.setUser(t);
			MyLog.e(TAG, "success:" + t.toString());
			Util.gotoMain(LoginActivity.this);
			MyToast.showMsgLong(LoginActivity.this, t.toString());
			finish();
		};

		@Override
		public void failure(String msg) {
			MyToast.showMsgLong(LoginActivity.this, msg);

			// Util.gotoMain(LoginActivity.this);
			// finish();

		}
	};

	public void login() {
		DataManager.getInstance(this).Login(etUserName.getText().toString(),
				etPasswd.getText().toString(), taobaoDataCallBack);

	}


	protected void loginSinaWeibo() {
		AbstractWeibo weibo = AbstractWeibo.getWeibo(this, SinaWeibo.NAME);
		weibo.setWeiboActionListener(this);
		weibo.authorize();
	}

	public void onComplete(AbstractWeibo weibo, int action,
			HashMap<String, Object> res) {
		Message msg = new Message();
		msg.arg1 = 1;
		msg.arg2 = action;
		msg.obj = weibo;
		handler.sendMessage(msg);
	}

	public void onCancel(AbstractWeibo weibo, int action) {
		Message msg = new Message();
		msg.arg1 = 3;
		msg.arg2 = action;
		msg.obj = weibo;
		handler.sendMessage(msg);
	}

	public void onError(AbstractWeibo weibo, int action, Throwable t) {
		t.printStackTrace();

		Message msg = new Message();
		msg.arg1 = 2;
		msg.arg2 = action;
		msg.obj = weibo;
		handler.sendMessage(msg);
	}
	@Override
	protected void onDestroy() {
		AbstractWeibo.stopSDK(this);
		super.onDestroy();
	}
	/** 通过Toast显示操作结果 */
	public boolean handleMessage(Message msg) {
		AbstractWeibo weibo = (AbstractWeibo) msg.obj;
		MyToast.showMsgShort(this, weibo.getDb().getToken());
		// String msg;// = MainActivity.actionToString(msg.arg2);
		switch (msg.arg1) {
		case 1: { // 成功
			// text = weibo.getName() + " get token: " +
			// weibo.getDb().getToken();
			
			// String token = weibo.getDb().getToken();
			loginToSinaServe(weibo);
		}
			break;
		case 2: { // 失败
			// msg="登录失败";
			MyToast.showMsgShort(this, "登录失败");
			// text = weibo.getName() + " caught error";
		}
			break;
		case 3: { // 取消
			// text = weibo.getName() + " authorization canceled";
			MyToast.showMsgShort(this, " authorization canceled");
		}
			break;
		}
		// MyToast.showMsgShort(this, msg);
		// Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
		return false;
	}

	public void loginToSinaServe(AbstractWeibo weibo) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("access_token", weibo.getDb().getToken());
		params.put("expire_time", weibo.getDb().getExpiresIn() + "");
		params.put("3rd_type", weibo.getId()+"");
		params.put("3rd_uid", weibo.getDb().getWeiboId()+"");
		
		DataManager.getInstance(this).doPost("user/openplatform", params,
				new DataCallBack<String>() {

					@Override
					public void success(String t) {
						MyLog.e("sina", "success=" + t);
						super.success(t);
					}

					@Override
					public void failure(String msg) {
						MyLog.e("sina", "failure=" + msg);
						MyToast.showMsgShort(LoginActivity.this, "failure="+msg);
					}
				});
	}

	@Override
	public String getNavigationLeftText() {
		// TODO Auto-generated method stub
		return getString(R.string.login);
	}

	@Override
	public boolean isShowRightClose() {
		return true;
	}

	@Override
	public void onClickRightButton() {
		finish();
	}

	@Override
	public void onClickLeftButton() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub

	}
	/**
	 * SsoHandler 仅当sdk支持sso时有效，
	 */
	// private SsoHandler mSsoHandler;

	// protected void loginBySinaWeibo() {
	// Weibo mWeibo = Weibo.getInstance(Constant.SINA_APP_KEY,
	// Constant.SINA_REDIRECT_URL, Constant.SINA_SCOPE);
	// mWeibo.anthorize(this, new AuthDialogListener());
	// mSsoHandler = new SsoHandler(this, mWeibo);
	// mSsoHandler.authorize(new AuthDialogListener(), null);

	// }

	/*
	 * private Oauth2AccessToken accessToken;
	 * 
	 * class AuthDialogListener implements WeiboAuthListener {
	 * 
	 * @Override public void onComplete(Bundle values) { String code =
	 * values.getString("code"); if (code != null) { //
	 * mText.setText("取得认证code: \r\n Code: " + code);
	 * Toast.makeText(LoginActivity.this, "认证code成功",
	 * Toast.LENGTH_SHORT).show(); loginToSinaServe(code,"1"); return; } String
	 * token = values.getString("access_token"); String expires_in =
	 * values.getString("expires_in"); accessToken = new
	 * Oauth2AccessToken(token, expires_in); if (accessToken.isSessionValid()) {
	 * // String date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss") //
	 * .format(new java.util.Date(MainActivity.accessToken //
	 * .getExpiresTime())); // mText.setText("认证成功: \r\n access_token: " + token
	 * + "\r\n" // + "expires_in: " + expires_in + "\r\n有效期：" + date);
	 * 
	 * // AccessTokenKeeper.keepAccessToken(LoginActivity.this, // accessToken);
	 * Toast.makeText(LoginActivity.this, "认证成功", Toast.LENGTH_SHORT) .show(); }
	 * }
	 * 
	 * @Override public void onError(WeiboDialogError e) {
	 * Toast.makeText(getApplicationContext(), "Auth error : " + e.getMessage(),
	 * Toast.LENGTH_LONG).show(); }
	 * 
	 * @Override public void onCancel() {
	 * Toast.makeText(getApplicationContext(), "Auth cancel",
	 * Toast.LENGTH_LONG).show(); }
	 * 
	 * @Override public void onWeiboException(WeiboException e) {
	 * Toast.makeText(getApplicationContext(), "Auth exception : " +
	 * e.getMessage(), Toast.LENGTH_LONG) .show(); }
	 * 
	 * }
	 * 
	 * @Override protected void onActivityResult(int requestCode, int
	 * resultCode, Intent data) { super.onActivityResult(requestCode,
	 * resultCode, data); // sso 授权回调 // if (mSsoHandler != null) { //
	 * mSsoHandler.authorizeCallBack(requestCode, resultCode, data); // } }
	 */
}
