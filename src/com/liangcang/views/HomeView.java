package com.liangcang.views;

import android.app.Activity;
import android.content.Context;
import android.widget.LinearLayout;

import com.liangcang.R;
import com.liangcang.weigets.FlyInMenu;

public class HomeView extends BaseView {
	FlyInMenu mFlyInMenu;
	private LinearLayout mLinear;

	public HomeView(Activity mContext) {
		super(mContext);
		setContentView(R.layout.home_layout);
		mFlyInMenu = (FlyInMenu) findViewById(R.id.leftPanel2);
		mLinear = (LinearLayout) findViewById(R.id.rightcontent);
		showView(1, 0);
	}

	private UserView mUserView;
	GridPicsItemView mGridPicsItemView;

	public void showView(int parents, int position) {
		switch (parents) {
		case 0:
			mLinear.removeAllViews();
			if (mGridPicsItemView == null) {
				mGridPicsItemView = new GridPicsItemView(mContext);
			}
			mLinear.addView(mGridPicsItemView.getView());
			break;
		case 1:
			mLinear.removeAllViews();
			if (mUserView == null) {
				mUserView = new UserView((Activity) mContext);
			}
			mLinear.addView(mUserView.getView());
			break;
		default:
			break;
		}
	}

	public void showOrHide() {
		// TODO Auto-generated method stub
//		mFlyInMenu.showOrHide();
	}

}
