package com.liangcang.views;

import android.content.Context;
import android.widget.LinearLayout;

import com.liangcang.R;
import com.liangcang.weigets.FlyInMenu;

public class HomeView extends BaseView {
	FlyInMenu  mFlyInMenu;
	public HomeView(Context mContext) {
		super(mContext);
		setContentView(R.layout.home_layout);
		mFlyInMenu=(FlyInMenu) findViewById(R.id.leftPanel2);
		LinearLayout mLinear = (LinearLayout) findViewById(R.id.rightcontent);
//		mLinear.removeAllViews();
//		GridPicsItemView mGridPicsItemView = new GridPicsItemView(mContext);
//		mLinear.addView(mGridPicsItemView.getView());

	}

	public void showOrHide() {
		// TODO Auto-generated method stub
		mFlyInMenu.showOrHide();
	}

}
