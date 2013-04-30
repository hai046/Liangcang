package com.liangcang.menus;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.liangcang.R;
import com.liangcang.base.IActivityGroup;
import com.liangcang.base.MyApplication;
import com.liangcang.util.MyLog;
import com.liangcang.util.Util;
import com.liangcang.views.DyncView;
import com.liangcang.views.FansView;
import com.liangcang.views.MsgView;
import com.liangcang.views.PrivateMsgView;
import com.liangcang.weigets.COFixListViewBugLinearLayout;
import com.liangcang.weigets.COSlidingMenu;
import com.liangcang.weigets.COSlidingState;
import com.umeng.analytics.MobclickAgent;

public class MenuActivity extends IActivityGroup implements OnClickListener {

	private String TAG = "MenuActivity";
	private COSlidingMenu mCOSlidingMenu;
	private MenuListAdapter mMenuListAdapter;
	private LinearLayout linearCenter, linearBottomLayout;
	DyncView mDyncView;
	MsgView mMsgView;
	PrivateMsgView mChatView;
	FansView mFansView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		initView();
		linearBottomLayout = (LinearLayout) findViewById(R.id.mainBottomLayout);
		linearBottomLayout.removeAllViews();
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.FILL_PARENT);
		params.weight = 1;
		for (int i = 0; i < 5; i++) {
			View view = getBottomMenu(i);
			if (view != null) {
				view.setId(100 + i);
				linearBottomLayout.addView(view, params);
				view.setOnClickListener(this);
			}
		}
		//switchMenuBg(0);
		mDyncView = new DyncView(this);
		mMsgView = new MsgView(this);
		mChatView = new PrivateMsgView(this);
		mFansView = new FansView(this);
		if (!isLogin()) {
			linearBottomLayout.setVisibility(View.GONE);
		}
	}

	private boolean isLogin() {
		MyApplication app = (MyApplication) getApplication();
		return app.isLogined();
	}

	private static int currentIndex = 0;

	protected void switchView(int position) {
		currentIndex = position;
		MyLog.e(TAG, "currentIndex=" + currentIndex);
		switchMenuBg(position);
		linearCenter.removeAllViews();
		switch (position) {
		case 0:
			linearCenter.addView(mDyncView.getView());
			break;
		case 1:
			linearCenter.addView(mMsgView.getView());
			break;
		case 2:
			linearCenter.addView(mChatView.getView());
			break;
		case 3:
			linearCenter.addView(mFansView.getView());
			break;

		case 4:
			switchIntent(-1);

		default:
			break;
		}

	}

	public View getBottomMenu(int position) {
		View view = getLayoutInflater().inflate(R.layout.menu_item, null);
		ImageView btn = (ImageView) view.findViewById(R.id.menu_btn);
		switch (position) {
		case 0:
			btn.setImageResource(R.drawable.menu_dync);
			break;
		case 1:
			btn.setImageResource(R.drawable.menu_msg);
			break;
		case 2:
			btn.setImageResource(R.drawable.menu_privatemsg);
			break;
		case 3:
			btn.setImageResource(R.drawable.menu_fan);
			break;
		case 4:
			btn.setImageResource(R.drawable.menu_home);
			break;
		default:
			break;
		}
		// btn.setImageResource(R.drawable.tuijian);
		// TextView tv = (TextView) view.findViewById(R.id.menu_title);
		// tv.setText(getResources().getStringArray(R.array.centerText)[position]);
		return view;
	}

	private void switchMenuBg(int position) {
		// MyLog.e( "base", "positon=" + position );
		for (int i = 0; i < 5; i++) {
			if (position == i)
				continue;
			// MyLog.e( "base", "iiiiiiiiiiiiiiii=" + i );
			View v = linearBottomLayout.getChildAt(i);
			// TextView tv = (TextView) v.findViewById(R.id.menu_title);
			// tv.setTextColor(getResources().getColor(R.color.black_98));
			ImageView btn = (ImageView) v.findViewById(R.id.menu_btn);
			switch (i) {
			case 0:
				btn.setImageResource(R.drawable.menu_dync);
				break;
			case 1:
				btn.setImageResource(R.drawable.menu_msg);
				break;
			case 2:
				btn.setImageResource(R.drawable.menu_privatemsg);
				break;
			case 3:
				btn.setImageResource(R.drawable.menu_fan);
				break;
			case 4:
				btn.setImageResource(R.drawable.menu_home);
				break;
			default:
				break;
			}
		}
		ImageView btn = (ImageView) linearBottomLayout.getChildAt(position)
				.findViewById(R.id.menu_btn);
		switch (position) {
		case 0:
			btn.setImageResource(R.drawable.menu_dync_on);
			break;
		case 1:
			btn.setImageResource(R.drawable.menu_msg_on);
			break;
		case 2:
			btn.setImageResource(R.drawable.menu_privatemsg_on);
			break;
		case 3:
			btn.setImageResource(R.drawable.menu_fan_on);
			break;
		case 4:
			btn.setImageResource(R.drawable.menu_home_on);
			break;
		default:
			break;
		}
		// btn.setImageResource(R.drawable.tuijian_on);
		// TextView tv = (TextView) linearBottomLayout.getChildAt(position)
		// .findViewById(R.id.menu_title);
		// tv.setTextColor(getResources().getColor(R.color.bg));
	}

	void initView() {
		mCOSlidingMenu = (COSlidingMenu) findViewById(R.id.slidingMenu);
		ViewGroup leftView = (ViewGroup) getLayoutInflater().inflate(
				R.layout.left_v, null);
		COFixListViewBugLinearLayout linearLayout = (COFixListViewBugLinearLayout) leftView
				.findViewById(R.id.meau_LinearLayout);
		ListView listView = new ListView(this);
		listView.setCacheColorHint(Color.TRANSPARENT);
		mMenuListAdapter = new MenuListAdapter(this);
		mMenuListAdapter.switchToSon(0);
		listView.setAdapter(mMenuListAdapter);
		linearLayout.addView(listView);

		leftView.findViewById(R.id.btn_menuSearch).setOnClickListener(this);
		leftView.findViewById(R.id.btn_menuHeart).setOnClickListener(this);
		leftView.findViewById(R.id.btn_menuSetting).setOnClickListener(this);
		leftView.findViewById(R.id.btn_menuExit).setOnClickListener(this);
		int w = (int) (Util.getDisplayWindth(this) * 0.33);
		if (w < 0)
			w = 200;
		mCOSlidingMenu.setLeftView(leftView, w);
		linearCenter = new LinearLayout(this);
		mCOSlidingMenu.setCenterView(linearCenter);
		switchIntent(-1);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (mMenuListAdapter.getMenuParentIndex() == 0) {
					switchIntent(arg2);
				}

			}
		});

	}

	@Override
	public void onClick(View v) {
		if (v.getId() >= 100 && v.getId() < 5 + 100) {
			switchView(v.getId() - 100);
			return;
		}
		switch (v.getId()) {

		case R.id.btn_menuExit:
			finish();
			break;
		case R.id.btn_menuSearch:
			switchIntent(R.id.btn_menuSearch);
			break;
		case R.id.btn_menuSetting:
			switchIntent(R.id.btn_menuSetting);
			break;
		default:
			break;
		}

	}

	@Override
	protected void onResume() {
		if (!isLogin()&&linearBottomLayout.getVisibility()==View.VISIBLE) {
			linearBottomLayout.setVisibility(View.GONE);
		}else if(isLogin()&&linearBottomLayout.getVisibility()!=View.VISIBLE)
		{
			linearBottomLayout.setVisibility(View.VISIBLE);
		}
		if(isLogin())
		{
			 mDyncView.ifLoadMoreNotData();
			 mMsgView.ifLoadMoreNotData();
			 mChatView.ifLoadMoreNotData();
			 mFansView.ifLoadMoreNotData();
		}
		
		super.onResume();
	}

	void switchIntent(int i) {
		Intent intent = new Intent();
		// mLinear.removeAllViews();
		switch (i) {
		/*
		 * case 0: intent.setClass(this, MenuCagegoryActivity.class);
		 * startActivity(intent); MobclickAgent.onEvent(this,
		 * "FirstTwoColumnPicActivity"); return; case 1: intent.setClass(this,
		 * RecommendActivity.class); MobclickAgent.onEvent(this,
		 * "FirstTwoColumnPicActivity"); break;
		 */
		case R.id.btn_menuSearch:
			intent.setClass(this, SearchActivity.class);
			MobclickAgent.onEvent(this, "SearchActivity");
			break;
		case R.id.btn_menuSetting:
			intent.setClass(this, SettingActivity.class);
			MobclickAgent.onEvent(this, "FirstTwoColumnPicActivity");
			break;
		case -1:
		default:
			intent.setClass(this, RecommendActivity.class);
			MobclickAgent.onEvent(this, "RecommendActivity");
			break;
		}
		Window subActivity = getLocalActivityManager().startActivity(
				"subActivity" + i, intent);

		linearCenter.removeAllViews();
		linearCenter.addView(subActivity.getDecorView());
	}

	@Override
	public void onClickLeftButton() {
		if (mCOSlidingMenu.getCurrentUIState() == COSlidingState.SHOWCENTER) {
			mCOSlidingMenu.showViewState(COSlidingState.SHOWLEFT);
		} else {
			mCOSlidingMenu.showViewState(COSlidingState.SHOWCENTER);
		}
	}

	@Override
	public void onClickRightButton() {

	}

	public void onClickRightTwoButton() {

	}

	@Override
	public void setCurrentTitleString() {
		// TODO Auto-generated method stub

	}

}
