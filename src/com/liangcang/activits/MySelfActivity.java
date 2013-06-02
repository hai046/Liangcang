package com.liangcang.activits;

import android.os.Bundle;

import com.liangcang.base.BaseActivity;
import com.liangcang.base.MyApplication;
import com.liangcang.views.UserView;

public class MySelfActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		UserView mUserView = new UserView(this);
		// MyApplication app=(MyApplication)getApplication();
		mUserView.setUser(MyApplication.getUser().getUser_id());
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
