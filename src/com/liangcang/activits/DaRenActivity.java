package com.liangcang.activits;

import android.os.Bundle;

import com.liangcang.R;
import com.liangcang.app.SlidingActivity;
import com.liangcang.base.BaseThrAdapter;
import com.liangcang.util.MyLog;
import com.liangcang.util.MyToast;
import com.liangcang.weigets.LoadMoreListView.LoadCallBack;
import com.liangcang.weigets.RefreshListView;
import com.liangcang.weigets.RefreshListView.RefreshListener;

public class DaRenActivity extends SlidingActivity {
	String TAG = "DaRenActivity";
	private BaseThrAdapter adapter;// = new BaseGoodAdapter(this) ;
	private RefreshListView listview;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		adapter = new BaseThrAdapter(this) {

			public void failure(String msg) {
				listview.onStopLoading();
				MyToast.showMsgLong(DaRenActivity.this, msg);
			}

			public void onSuccess() {
				listview.onStopLoading();
			}
		};
		initMenu();
		initContent();
		setLeftImage(R.drawable.navigation_menu);
	}

	protected void switchToView(String value) {
		adapter.setPath(value);
		adapter.onRefresh();
		listview.setSelection(0);
	}

	private void initContent() {

		listview = new RefreshListView(this);
		listview.setAdapter(adapter);
		switchToView("expert?orderby=goods_sum");
		adapter.onRefresh();
		setContentView(listview);
		listview.setOnLoadCallBack(new LoadCallBack() {

			@Override
			public void onLoading() {
				adapter.onLoadMore();

			}
		});
		initMenu();
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
		mMenuListView.switchDarenMenus();
		setBehindContentView(mMenuListView);

	}

}
