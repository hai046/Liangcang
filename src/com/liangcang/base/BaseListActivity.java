package com.liangcang.base;

import java.util.HashMap;
import java.util.List;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.liangcang.managers.DataCallBack;
import com.liangcang.managers.DataManager;
import com.liangcang.weigets.LoadMoreListView;
import com.liangcang.weigets.LoadMoreListView.LoadCallBack;

public abstract class BaseListActivity<T> extends IActivity {

	protected LoadMoreListView listview;
	private HashMap<String, String> params;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		listview = new LoadMoreListView(this);
		listview.setBackgroundColor(Color.BLACK);
		setContentView(listview);
		params = new HashMap<String, String>();
		params.put("count", 20+ "");
		listview.setOnLoadCallBack(new LoadCallBack() {

			@Override
			public void onLoading() {
				loadMore();

			}
		});
	}

	public void setAdapter(MyBaseAdapter<T> adapter) {
		this.adapter = adapter;
		listview.setAdapter(adapter);

	}

	private MyBaseAdapter<T> adapter;


	public void loadMore() {
		
		params.put("page", (1+currentPage) + "");
		DataManager.getInstance(this).getListData(path(), params, isDoGet(),
				mDataCallBack);
	}

	public void onRefresh() {
		currentPage = 1;
		adapter.clear();
		params.put("page", currentPage + "");
		DataManager.getInstance(this).getListData(path(), params, isDoGet(),
				mDataCallBack);
	}

	private DataCallBack<String> mDataCallBack = new DataCallBack<String>() {

		@Override
		public void failure(String msg) {

			onReceiveFailure(msg);
			listview.onStopLoading();
		}

		public void success(String str) {

			if (!TextUtils.isEmpty(str)) {
				List<T> list = JSON.parseArray(str, getModeClass());
				adapter.addAll(list);
				adapter.notifyDataSetChanged();
				currentPage+=1;
			}
			listview.onStopLoading();
			onReceiveSuccess();
		}
	};

	/**
	 * 是否是get请求，否则为post
	 * 
	 * @return
	 */
	public boolean isDoGet() {
		return true;
	}

	/**
	 * 请求的path部分
	 * 
	 * @return
	 */
	public abstract String path();

	/**
	 * 数据的类型
	 * 
	 * @return
	 */
	public abstract Class<T> getModeClass();

	public abstract void onReceiveFailure(String msg);

	public abstract void onReceiveSuccess();

	// public abstract View bindView(int position, T t, View view);

	private int currentPage = 0;

}
