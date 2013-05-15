package com.liangcang;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.liangcang.base.IActivity;
import com.liangcang.base.MyApplication;
import com.liangcang.menus.MenuListAdapter;
import com.liangcang.mode.User;
import com.liangcang.util.ImageDownloader;
import com.liangcang.views.DyncView;
import com.liangcang.views.RecommendView;
import com.liangcang.weigets.FlyInMenu;

public class HomeActivity extends IActivity implements OnClickListener {

	private String TAG = "HomeActivity";
	private FlyInMenu mFlyInMenu;
	private ImageView btnLeft, btnRight, btnRight2;
	private TextView tvTitle;
	private View viewLine;
	private DyncView mDyncView;
	private RecommendView mRecommendView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		mFlyInMenu = (FlyInMenu) findViewById(R.id.leftFlyInMenu);
		mRecommendView=new RecommendView(this);
		mFlyInMenu.setRightContent(mRecommendView.getView());
		
//		mDyncView.onRefresh();
//		initNavigation();
		initView();
	}
	private void initView() {
		MenuListAdapter madapter=new MenuListAdapter(this);
		madapter.switchToSon(0);
//		mFlyInMenu.setAdapter(madapter);
//		mFlyInMenu.setRightContent(view);
		
	}
	void initNavigation() {
		User user = MyApplication.getUser();
		if (user != null) {
			btnRight.setVisibility(View.VISIBLE);
			btnRight2.setVisibility(View.GONE);
			viewLine.setVisibility(View.GONE);
			ImageDownloader.getInstance().download(user.getUser_image(),
					btnRight);
		} else {
			showRegister();
		}

	}
	public void showRegister() {
		btnRight.setVisibility(View.VISIBLE);
		btnRight2.setVisibility(View.VISIBLE);
		viewLine.setVisibility(View.VISIBLE);
		btnRight.setImageResource(R.drawable.nav_login);
		btnRight2.setImageResource(R.drawable.nav_register);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case 0:
			mFlyInMenu.setToOpen();
			break;
		case 1:
			mFlyInMenu.setToClose();
		default:
			break;
		}
		
		return super.onMenuItemSelected(featureId, item);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		 menu.add(0, 0, 0, "setToOpen");
		 menu.add(0, 0, 1, "setToClose");
		return super.onCreateOptionsMenu(menu);
	}

}
