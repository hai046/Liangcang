package com.liangcang.base;

import java.util.HashMap;

import android.content.Context;
import android.view.View;

import com.liangcang.managers.DataCallBack;
import com.liangcang.managers.DataManager;
import com.liangcang.util.MyLog;

public abstract class MyBasePageAdapter<T> extends MyBaseAdapter<T> {

	private Context mContext;
	private HashMap<String, String> params;

	private MyBasePageAdapter() {

	}
	public int getEachCout()
	{
		return 20;
	}
	public MyBasePageAdapter(Context mContext) {
		this.mContext=mContext;
		params = new HashMap<String, String>();
		params.put("count", getEachCout()+"");
		
	}
	public void addParam(String key,String value)
	{
		params.put(key, value);
	}

	
	/**
	 * 是否是get请求，否则为post
	 * 
	 * @return
	 */
	public abstract boolean isDoGet();

	/**
	 * 请求的path部分
	 * 
	 * @return
	 */
	public abstract String path();

	public abstract void onReceiveFailure(String msg);

	public abstract void onReceiveSuccess();

	public abstract View bindView(int position, T t, View view);

	public  Class<T> getModeClass()
	{
		return null;
	}

	private int currentPage = 0;

	public void loadMore()
	{
		int page=1;
		MyLog.i("MyBasePageAdapter", "loadMore   getCount()="+getCount());
		if(getCount()%getEachCout()==0)
		{
			page=getCount()/getEachCout()+1;
			
		}else
		{
			onReceiveSuccess();
			return;
		}
		
		currentPage=page;
		params.put("page", currentPage+"");
		DataManager.getInstance(mContext).getListData(path(), params,
				isDoGet(),getModeClass(), mDataCallBack);
	}
	
	public void onRefresh() {
		currentPage =1;
		clear();
		params.put("page", currentPage+"");
		DataManager.getInstance(mContext).getListData(path(), params,
				isDoGet(), getModeClass(), mDataCallBack);
	}

	private DataCallBack<T> mDataCallBack = new DataCallBack<T>() {

		@Override
		public void failure(String msg) {
			onReceiveFailure(msg);
		}

		public void success(java.util.List<T> list) {
			if(list!=null)
			{
				addAll(list);
				notifyDataSetChanged();
				
			}
			onReceiveSuccess();
		}
	};

}
