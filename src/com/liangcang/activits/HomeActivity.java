package com.liangcang.activits;

import android.os.Bundle;

import com.liangcang.R;
import com.liangcang.app.SlidingActivity;
import com.liangcang.base.BaseGoodAdapter;
import com.liangcang.util.MyLog;
import com.liangcang.util.MyToast;
import com.liangcang.weigets.LoadMoreListView.LoadCallBack;
import com.liangcang.weigets.RefreshListView;
import com.liangcang.weigets.RefreshListView.RefreshListener;

public class HomeActivity extends SlidingActivity {
	String TAG = "HomeActivity";
	// private RecommendView mRecommendView;
	private BaseGoodAdapter adapter;// = new BaseGoodAdapter(this) ;
	private RefreshListView listview;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		adapter = new BaseGoodAdapter(this) {

			public void failure(String msg) {
				listview.onStopLoading();
				listview.stopRefresh();
				MyToast.showMsgLong(HomeActivity.this, msg);
				
			}

			public void success() {
				listview.stopRefresh();
				if (!hasMore()) {
					listview.onStopLoading();
				}

			}
		};
		// mRecommendView = new RecommendView(this);
		// setContentView(mRecommendView.getView());
		initMenu();
		initContent();
		switchToView("recommendation");
		setLeftImage(R.drawable.navigation_menu);
	}

	protected void switchToView(String value) {
		adapter.setPath(value);
		adapter.onRefresh();
		listview.stopRefresh();
		listview.setSelection(0);
	}

	private void initContent() {

		listview = new RefreshListView(this);
		listview.setAdapter(adapter);
		adapter.onRefresh();
		setContentView(listview);
		listview.setOnLoadCallBack(new LoadCallBack() {

			@Override
			public void onLoading() {
				adapter.onLoadMore();

			}
		});
		listview.setOnRefreshListener(new RefreshListener() {

			@Override
			public void onRefresh() {
				adapter.onRefresh();
				listview.onShowLoadMore();
			}
		});

	}

	private String baseUrl = "";

	private void initMenu() {
		MenuListView mMenuListView = new MenuListView(this) {
			@Override
			public void onSelect(String showText, String value, int flag) {
				MyLog.e(TAG, "showText=" + showText + "\tvalue=" + value
						+ " flag=" + flag);
				if (flag == 2) {
					baseUrl = value;
				}
				switchToView((flag == 1) ? (baseUrl + "&" + value) : value);
			}
		};
		mMenuListView.switchCategorysMenus();
		setBehindContentView(mMenuListView);

	}

}
