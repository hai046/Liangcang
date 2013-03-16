package com.liangcang;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.GridView;

import com.liangcang.base.BaseViewPagerActivity;
import com.liangcang.views.ChatView;
import com.liangcang.views.DyncView;
import com.liangcang.views.FansView;
import com.liangcang.views.HomeView;
import com.liangcang.views.MsgView;

public class MainActivity extends BaseViewPagerActivity {
	DyncView mDyncView;
	MsgView mMsgView;
	ChatView mChatView;
	FansView mFansView;
	HomeView mHomeView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mDyncView = new DyncView(this);
		mMsgView = new MsgView(this);
		mChatView = new ChatView(this);
		mFansView = new FansView(this);
		mHomeView=new HomeView(this);
		switchView(0);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public void onClickLeftButton(int position) {
		switch (position) {
		case 4:
			mHomeView.showOrHide();
			break;

		default:
			break;
		}

	}

	@Override
	public void onClickRightButton(int position) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 5;
	}

	@Override
	public View getShowView(int index) {
		switch (index) {
		case 0:
			return mDyncView.getView();
		case 1:
			return mMsgView.getView();
		case 2:
			return mChatView.getView();
		case 3:
			return mFansView.getView();
		case 4:
			return mHomeView.getView();
		default:
			break;
		}
		return null;
	}

	@Override
	public void onPageSelected(int position) {
		switch (position) {
		case 0:
			setLeftText("好友动态 18");
			break;
		case 1:
			setLeftText("消息 10");
			break;
		case 2:
			setLeftText("私信 20");
			break;
		case 3:
			setLeftText("粉丝 7");
			break;
		case 4:
			setLeftText(null);
			setNaviationLeftBg(R.drawable.menu_mzsm);
			break;

		default:
			break;
		}

	}

}
