package com.liangcang;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.liangcang.base.MyApplication;
import com.liangcang.base.MyBaseViewPagerActivity;
import com.liangcang.menus.MenuActivity;
import com.liangcang.views.DyncView;
import com.liangcang.views.FansView;
import com.liangcang.views.MsgView;
import com.liangcang.views.PrivateMsgView;

public class MainActivity extends MyBaseViewPagerActivity {
	DyncView mDyncView;
	MsgView mMsgView;
	PrivateMsgView mChatView;
	FansView mFansView;
	
	// HomeView mHomeView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		MyApplication mMyApplication = (MyApplication) getApplication();

		mDyncView = new DyncView(this);
		mMsgView = new MsgView(this);
		mChatView = new PrivateMsgView(this);
		mFansView = new FansView(this);

		// mHomeView = new HomeView(this);
		switchView(0);

	}

	@Override
	public void onClickLeftButton(int position) {
		switch (position) {
		case 4:
			// mHomeView.showOrHide();
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
			// return mHomeView.getView();
			Intent intent = new Intent();
			intent.setClass(this, MenuActivity.class);
			startActivity(intent);
		default:
			break;
		}
		return null;
	}

	@Override
	public void onPageSelected(int position) {
		setNavigationMsgNum(position);
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
