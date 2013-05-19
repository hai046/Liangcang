package com.liangcang.views;

import android.content.Context;

import com.liangcang.base.BaseThrAdapter;
import com.liangcang.base.BaseThrAdapter.Type;
import com.liangcang.weigets.LoadMoreListView;
import com.liangcang.weigets.LoadMoreListView.LoadCallBack;

public class FansView extends BaseView {

	private BaseThrAdapter adapter;
	private LoadMoreListView listView;

	public FansView(Context mContext) {
		super(mContext);
		listView = new LoadMoreListView(mContext);
		adapter = new BaseThrAdapter(mContext) {
			@Override
			public void onSuccess() {
				listView.onStopLoading();
				super.onSuccess();
			}

			@Override
			public void failure(String msg) {
				listView.onStopLoading();
				super.failure(msg);
			}
		};
		listView.setAdapter(adapter);
		listView.setOnLoadCallBack(new LoadCallBack() {
			@Override
			public void onLoading() {
				adapter.onLoadMore();
			}
		});
		setContentView(listView);
		adapter.setType(Type.Followed);
		adapter.onRefresh();
	}

	

}
