package com.liangcang.activits;

import android.os.Bundle;

import com.liangcang.base.BaseActivity;
import com.liangcang.views.UserView;

public class UserActivity extends BaseActivity {

	private UserView mUserView;
	public static String USERID = "user_id";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		mUserView = new UserView(this);
		// MyApplication app=(MyApplication)getApplication();
		mUserView.setUser(/* app.getUser().getUser_id() */getIntent()
				.getStringExtra(USERID));
		setContentView(mUserView.getView());
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

}
