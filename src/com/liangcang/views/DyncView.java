package com.liangcang.views;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.liangcang.R;
import com.liangcang.base.MyBasePageAdapter;
import com.liangcang.managers.DataCallBack;
import com.liangcang.managers.DataManager;
import com.liangcang.mode.Good;
import com.liangcang.util.ImageDownloader;
import com.liangcang.weigets.LoadMoreListView;

/**
 * 实现动态页面
 * @author 邓海柱
 * haizhu12345@gmail.com
 */
public class DyncView extends BaseView {

	MyBasePageAdapter<Good>adapter;
	public DyncView(Context mContext) {
		super(mContext);
		LoadMoreListView listView = new LoadMoreListView(mContext);
		listView.setDividerHeight(0);
		adapter=new MyBasePageAdapter<Good>(mContext) {
			
			@Override
			public String path() {
				return "recommendation";
			}
			
			@Override
			public void onReceiveSuccess() {
				
			}
			
			@Override
			public void onReceiveFailure(String msg) {
				
			}
			
			@Override
			public boolean isDoGet() {
				return true;
			}
			
			
			@Override
			public Class<Good> getModeClass() {
				return Good.class;
			}
			
			@Override
			public View bindView(int position, Good t, View view) {
				if (view == null) {
					view = getLayoutInflater().inflate(R.layout.dync_layout, null);

				}
				ImageView imgShopping = (ImageView) view
						.findViewById(R.id.dync_img_shopping);
				ImageDownloader.getInstance().download(t.getGoods_image(), imgShopping);
				return view;
			}
		};
		listView.setAdapter(adapter);
		setContentView(listView);
		//getData(0);
	}
	@Override
	public void onRefresh() {
		//getData(0);
		adapter.onRefresh();
		super.onRefresh();
	}
	/*int currentPage;
	private void getData(int page)
	{
		currentPage=page;
		DataManager.getInstance(mContext).getRecomends(currentPage, 20, mDataCallBack);
		
	}
	private void LoadMore()
	{
		currentPage+=1;
		DataManager.getInstance(mContext).getRecomends(currentPage, 20, mDataCallBack);
		
	}
	
	private DataCallBack<Good> mDataCallBack=new DataCallBack<Good>() {
		
		
		@Override
		public void success(List<Good> list) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void failure(String msg) {
			// TODO Auto-generated method stub
			
		}
	};*/
}
