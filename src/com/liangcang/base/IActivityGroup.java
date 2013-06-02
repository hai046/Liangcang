package com.liangcang.base;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.liangcang.R;
import com.liangcang.RegisterActivity;
import com.liangcang.mode.User;
import com.liangcang.util.ImageDownloader;
import com.liangcang.util.Util;

public abstract class IActivityGroup extends ActivityGroup {

//	private ImageView btnLeft, btnRight, btnRight2;
//	private TextView tvTitle;
//	private View viewLine;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.setContentView(R.layout.homebase_layout);
//		btnLeft = (ImageView) findViewById(R.id.btn_left_title);
//		btnRight = (ImageView) findViewById(R.id.btn_right_title);
//		btnRight2 = (ImageView) findViewById(R.id.btn_right2_title);
//		btnLeft.setOnClickListener(mOnClickListener);
//		btnRight.setOnClickListener(mOnClickListener);
//		btnRight2.setOnClickListener(mOnClickListener);
//		tvTitle = (TextView) findViewById(R.id.tv_title);
//		setCurrentTitleString();
//		this.viewLine = findViewById(R.id.navigation_line);
//		setLeftImage(R.drawable.nav_list);
	}

	@Override
	protected void onResume() {
//		initNavigation();
		super.onResume();
	}
//	void initNavigation() {
//		User user = ((MyApplication) getApplication()).getUser();
//		if (user != null) {
//			btnRight.setVisibility(View.VISIBLE);
//			btnRight2.setVisibility(View.GONE);
//			viewLine.setVisibility(View.GONE);
//			ImageDownloader.getInstance().download(user.getUser_image(),
//					btnRight);
//		} else {
//			showRegister();
//		}
//
//	}
//
//	public void setLeftImage(int resId) {
//		if (resId < 0) {
//			btnLeft.setVisibility(View.GONE);
//			return;
//
//		}
//		btnLeft.setImageResource(resId);
//		btnLeft.setVisibility(View.VISIBLE);
//
//	}
//	public void setRightImage(int resId)
//	{
//		if (resId < 0) {
//			btnRight.setVisibility(View.GONE);
//			return;
//
//		}
//		btnRight.setImageResource(resId);
//		btnRight.setVisibility(View.VISIBLE);
//	}
//	
//	public void showRegister() {
//		btnRight.setVisibility(View.VISIBLE);
//		btnRight2.setVisibility(View.VISIBLE);
//		viewLine.setVisibility(View.VISIBLE);
//		btnRight.setImageResource(R.drawable.nav_login);
//		btnRight2.setImageResource(R.drawable.nav_register);
//	}

	private OnClickListener mOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// MyLog.e( "IActivityGroup", "v vvvv=" + v.getId( ) );
			switch (v.getId()) {
			case R.id.btn_left_title:
				
				onClickLeftButton();
				break;
			case R.id.btn_right_title:
				if(!((MyApplication) getApplication()).isLogined())
				{
					Util.gotoLogin(IActivityGroup.this);
					break;
				}
				onClickRightButton();
				break;
			case R.id.btn_right2_title:
				if(!((MyApplication) getApplication()).isLogined())
				{
					Intent intent=new Intent();
					intent.setClass(getApplicationContext(), RegisterActivity.class);
					startActivity(intent);
					
					break;
				}
				onClickRightTwoButton();
				break;
			default:
				break;
			}
		}
	};

	public abstract void onClickLeftButton();

	public abstract void onClickRightButton();

	public void onClickRightTwoButton() {

	}

	/**
	 * 可使用 {@link setTitle}设置导航的文字<br>
	 * 
	 */
	public abstract void setCurrentTitleString();

//	public void setCurrentTitleString(String leftBtnText, String centerText,
//			String rightBtnText) {
//		if (leftBtnText == null) {
//			btnLeft.setVisibility(View.INVISIBLE);
//		} else {
//			btnLeft.setVisibility(View.VISIBLE);
//
//		}
//		// btnLeft.setText( leftBtnText );
//		tvTitle.setText(centerText);
//		// btnRight.setText( rightBtnText );
//		if (rightBtnText == null) {
//			btnRight.setVisibility(View.INVISIBLE);
//		} else {
//			btnRight.setVisibility(View.VISIBLE);
//
//		}
//	}
//
//	@Override
//	public void setTitle(CharSequence title) {
//		tvTitle.setText(title);
//	}
//
//	@Override
//	public void setTitle(int titleId) {
//		tvTitle.setText(titleId);
//	}
//
//	public void setTopBtnBackground(int btnLeftBackground,
//			int btnRightBackground) {
//		if (btnLeftBackground > 0) {
//			btnLeft.setVisibility(View.VISIBLE);
//			btnLeft.setImageResource(btnLeftBackground);
//		}
//		if (btnRightBackground > 0) {
//			btnRight.setVisibility(View.VISIBLE);
//			btnRight.setImageResource(btnRightBackground);
//		}
//	}

	@Override
	public void setContentView(int layoutResID) {
		setContentView(getLayoutInflater().inflate(layoutResID, null));
	}

	@Override
	public void setContentView(View view) {
		ViewGroup mViewGroup = (ViewGroup) findViewById(R.id.base_layout_mainContent);
		mViewGroup.removeAllViews();
		mViewGroup.addView(view);

	}

	public void setContentView(View view, LayoutParams params) {
		ViewGroup mViewGroup = (ViewGroup) findViewById(R.id.base_layout_mainContent);
		mViewGroup.removeAllViews();
		mViewGroup.addView(view, params);
	}
}
