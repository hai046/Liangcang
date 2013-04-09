package com.liangcang;

import android.content.res.Configuration;
import android.os.Bundle;

import com.liangcang.base.BaseActivity;
import com.liangcang.views.ChatView;

public class ChatActivity extends BaseActivity {
	private ChatView mChatView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mChatView=new ChatView(this);
		setContentView(mChatView.getView());
		
		
	}
	@Override
	public void onConfigurationChanged(Configuration newConfig) {

		mChatView.selectLastItem();
//		super.onConfigurationChanged(newConfig);
	}
	

	@Override
	public void onClickRightButton() {
		// TODO Auto-generated method stub

	}

	

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub

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
	public void onClickLeftButton() {
		// TODO Auto-generated method stub
		
	}

}
