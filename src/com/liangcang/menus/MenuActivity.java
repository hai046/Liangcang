package com.liangcang.menus;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.liangcang.LoginActivity;
import com.liangcang.R;
import com.liangcang.RegisterActivity;
import com.liangcang.base.IActivityGroup;
import com.liangcang.menus.FlyInMenu.ClickCallBack;
import com.umeng.analytics.MobclickAgent;

public class MenuActivity extends IActivityGroup {
	FlyInMenu mFlyInMenu;
	LinearLayout mLinear;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_layout);
		mFlyInMenu = (FlyInMenu) findViewById(R.id.leftPanel2);
		mFlyInMenu.setAdapter(0);
		mLinear = (LinearLayout) findViewById(R.id.rightcontent);
		switchIntent(-1);
		mFlyInMenu.setClickCallBack(new ClickCallBack() {

			@Override
			public void onClick(Object item, int position) {
				if (position == 0) {
					Intent intent = new Intent();
					intent.setClass(MenuActivity.this,
							MenuCagegoryActivity.class);
					startActivity(intent);
				} else {
					switchIntent(position);
				}

			}
		});
		showRegister();
	}

	@Override
	protected void onResume() {
		mFlyInMenu.requestFocus();
		super.onResume();
	}

	void switchIntent(int i) {
		Intent intent = new Intent();
		mLinear.removeAllViews();
		switch (i) {
		case 0:
			intent.setClass(this, SearchActivity.class);
			MobclickAgent.onEvent(this, "SearchActivity");
			break;
		case 1:
			intent.setClass(this, RecommendActivity.class);
			MobclickAgent.onEvent(this, "FirstTwoColumnPicActivity");
			break;
		// case 2:
		// // intent.setClass( this, ShowDescWebActivity.class );
		// MobclickAgent.onEvent(this, "seeItem_web");
		// break;
		case 5:
			intent.setClass(this, SettingActivity.class);
			MobclickAgent.onEvent(this, "FirstTwoColumnPicActivity");
			break;
		default:
			intent.setClass(this, RecommendActivity.class);
			MobclickAgent.onEvent(this, "RecommendActivity");
			break;
		}
		Window subActivity = getLocalActivityManager().startActivity(
				"subActivity" + i, intent);
		mLinear.addView(subActivity.getDecorView(), LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT);
		mFlyInMenu.requestFocus();
	}

	@Override
	public void onClickLeftButton() {
		mFlyInMenu.showOfHide();
	}

	@Override
	public void onClickRightButton() {
		Intent intent = new Intent();
		intent.setClass(this, LoginActivity.class);
		startActivity(intent);
	}

	public void onClickRightTwoButton() {
		Intent intent = new Intent();
		intent.setClass(this, RegisterActivity.class);
		startActivity(intent);
	}

	@Override
	public void setCurrentTitleString() {
		// TODO Auto-generated method stub

	}
}
