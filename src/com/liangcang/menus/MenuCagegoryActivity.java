package com.liangcang.menus;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.liangcang.R;
import com.liangcang.base.IActivityGroup;
import com.liangcang.views.GridPicsItemView;
import com.umeng.analytics.MobclickAgent;

public class MenuCagegoryActivity extends IActivityGroup {
	FlyInMenu mFlyInMenu;
	LinearLayout mLinear;
	private GridPicsItemView mGridPicsItemView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_layout);
		mFlyInMenu = (FlyInMenu) findViewById(R.id.leftPanel2);
		mFlyInMenu.setAdapter(1);
		mLinear = (LinearLayout) findViewById(R.id.rightcontent);
		mGridPicsItemView = new GridPicsItemView(this);
		mLinear.addView(mGridPicsItemView.getView());
	}

	@Override
	protected void onResume() {
		mFlyInMenu.requestFocus();
		super.onResume();
	}

	@Override
	public void onClickLeftButton() {
		mFlyInMenu.showOfHide();
	}

	@Override
	public void onClickRightButton() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setCurrentTitleString() {
		// TODO Auto-generated method stub

	}
}
