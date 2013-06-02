package com.liangcang.views;

import android.content.Context;

import com.liangcang.base.BaseThrAdapter;
import com.liangcang.base.BaseThrAdapter.Type;
import com.liangcang.weigets.LoadMoreListView;
import com.liangcang.weigets.LoadMoreListView.LoadCallBack;

public class DaRenView extends BaseView {

	private BaseThrAdapter adapter;
	public DaRenView(Context mContext) {
		super(mContext);
		LoadMoreListView listView=new LoadMoreListView(mContext);
		adapter=new BaseThrAdapter(mContext);
		adapter.setType(Type.EXPERT	);
		adapter.onRefresh();
		listView.setOnLoadCallBack(new LoadCallBack() {
			
			@Override
			public void onLoading() {
				adapter.onLoadMore();
			}
		});
		listView.setAdapter(adapter);
		setContentView(listView);
	}
	
}
