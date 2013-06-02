package com.liangcang.views;

import android.content.Context;

import com.liangcang.base.BaseGoodAdapter;
import com.liangcang.weigets.LoadMoreListView;
import com.liangcang.weigets.LoadMoreListView.LoadCallBack;

public class CategoryView extends BaseView {
	public enum Type
	{
		Men(1),
		Women(2),
		Furniture(20),
		Camera(3),
		Tool(4),
		Toy(5),
		/**
		 * 美容
		 */
		Cosmetology(7),
		Child(10),
		Pet(9),
		Movement(23),
		/**
		 * 饮食
		 */
		Diet(8),
		/**
		 * 文化
		 */
		Culture(15),
		;
		private int value;
		Type(int value)
		{
			this.value=value;
		}
		public int getValue()
		{
			return value;
		}
		
		
		
	}
	private LoadMoreListView listView;
	private BaseGoodAdapter adapter;
	public CategoryView(Context mContext) {
		super(mContext);
		listView=new LoadMoreListView(mContext);
		listView.setOnLoadCallBack(new LoadCallBack() {
			
			@Override
			public void onLoading() {
				adapter.onLoadMore();
				
			}
		});
		adapter=new BaseGoodAdapter(mContext);
		listView.setAdapter(adapter);
		setContentView(listView);
	}
	public void setType(int type)
	{
		adapter.setType(type);
		adapter.onRefresh();
	}
	
}
