package com.liangcang.views;

import android.content.Context;

import com.liangcang.base.BaseGoodAdapter;
import com.liangcang.weigets.LoadMoreListView;

public class RecommendView extends BaseView {

	public RecommendView(Context mContext)
	{
		BaseGoodAdapter adapter=new BaseGoodAdapter(mContext)
		{
			@Override
			public String getPath() {
				return "recommendation";
			}
		};
		LoadMoreListView listview=new LoadMoreListView(mContext);
		listview.setAdapter(adapter);
		adapter.onRefresh();
		setContentView(listview);
		
	}
}
