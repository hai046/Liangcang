package com.liangcang.views;

import android.content.Context;

import com.liangcang.base.BaseThrAdapter;
import com.liangcang.base.BaseThrAdapter.Type;
import com.liangcang.base.MyApplication;
import com.liangcang.weigets.LoadMoreListView;
import com.liangcang.weigets.LoadMoreListView.LoadCallBack;

public class FansView extends BaseView {

	private BaseThrAdapter adapter;
	public FansView(Context mContext) {
		super(mContext);
//		GridView mGridView = new GridView(mContext);
//		mGridView.setVerticalSpacing(5);
//		mGridView.setBackgroundColor(mContext.getResources().getColor(
//				R.color.black));
//		mGridView.setNumColumns(3);
		LoadMoreListView listView=new LoadMoreListView(mContext);
		adapter=new BaseThrAdapter(mContext);
		listView.setAdapter(adapter);
		listView.setOnLoadCallBack(new  LoadCallBack() {
			
			@Override
			public void onLoading() {
				adapter.onLoadMore();
				
			}
		});
		setContentView(listView);
		adapter.setType(Type.Followed);
		isLoad=false;
	}
	private boolean isLoad=false;
	public void ifLoadMoreNotData() {
		if(isLoad)
		{
			return;
		}
		MyApplication mApp=(MyApplication) mContext.getApplicationContext();
		adapter.setUserId(mApp.getUser().getUser_id());
		isLoad=true;
		adapter.onRefresh();
		
		
	}

}
