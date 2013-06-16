package com.liangcang;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;

import com.liangcang.base.MyApplication;
import com.liangcang.base.PhotoBaseActivity;
import com.liangcang.managers.DataCallBack;
import com.liangcang.managers.DataManager;
import com.liangcang.menus.MenuActivity;
import com.liangcang.mode.User;
import com.liangcang.util.MyLog;
import com.liangcang.util.MyToast;
import com.liangcang.util.Util;

public class RegisterActivity extends PhotoBaseActivity {

	private EditText userName, passwordOne, passwordTwo, liangcangName;
	private ImageView userImage;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_layout);
		userName = (EditText) findViewById(R.id.userName);
		passwordOne = (EditText) findViewById(R.id.passwordOne);
		passwordTwo = (EditText) findViewById(R.id.passwordTwo);
		liangcangName = (EditText) findViewById(R.id.liangcangName);
		userImage=(ImageView) findViewById(R.id.userImage);
		findViewById(R.id.btnRegister).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				regist();
			}
		});
		userImage.setOnClickListener(mClickListener);
	}
	private OnClickListener mClickListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.userImage:
				doPickPhotoAction();
				
				break;

			default:
				break;
			}
			
		}
	};
	private void regist() {
		String email = userName.getText().toString().trim();
		if (Util.checkEmail(email) == false) {
			MyToast.showMsgLong(this, "邮箱错误！");
			return ;
		}

		String pass1 = passwordOne.getText().toString().trim();
		String pass2 = passwordTwo.getText().toString().trim();
		String name=liangcangName.getText().toString().trim();
		if(pass1.length()<6)
		{
			MyToast.showMsgLong(this, "密码必须大于对于6位");
			return ;
		}
		if(!pass1.equals(pass2))
		{
			MyToast.showMsgLong(this, "两次密码输入不一样");
			return ;
		}
		if(TextUtils.isEmpty(name))
		{
			MyToast.showMsgLong(this, "请输入良仓名");
			return;
		}
		DataManager.getInstance(this).doRegister(email, pass1, name,
				user_image, new DataCallBack<User>() {
					@Override
					public void success(User t) {
						MyLog.e("register", "doRegister="+t);
						MyApplication mMyApplication=(MyApplication) getApplication();
						mMyApplication.setUser(t);
						
						Intent intent=new Intent();
						intent.setClass(RegisterActivity.this, MenuActivity.class);
						startActivity(intent);
						finish();
					}

					@Override
					public void failure(String msg) {
						MyLog.e("register", "failure="+msg);
						MyToast.showMsgLong(RegisterActivity.this, msg);
						
					}
				});

	}

	@Override
	public String getNavigationLeftText() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isShowRightClose() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onClickRightButton() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClickLeftButton() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub

	}
	private String user_image;
	@Override
	public void callBackPath(String path) {
		MyLog.e("photo", "path="+path);
		user_image=path;
	}

	@Override
	public void setImageBitmap(Bitmap bitmap) {
		if(bitmap!=null)
		{
			this.userImage.setImageBitmap(bitmap);
		}
		
	}

}
