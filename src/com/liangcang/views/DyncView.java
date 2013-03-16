package com.liangcang.views;

import com.liangcang.R;
import com.liangcang.base.MyBaseAdapter;
import com.liangcang.weigets.LoadMoreListView;

import android.content.Context;
import android.view.View;

public class DyncView extends BaseView {

	private MyBaseAdapter<String> adapter = new MyBaseAdapter<String>() {

		@Override
		public View bindView(int position, String t, View view) {
			if (view == null) {
				view = getLayoutInflater().inflate(R.layout.dync_layout, null);

			}
			return view;
		}
	};

	public DyncView(Context mContext) {
		super(mContext);
		LoadMoreListView listView = new LoadMoreListView(mContext);
//		listView.setBackgroundColor(mContext.getResources().getColor(
//				R.color.black));
		listView.setDividerHeight(0);
		for (int i = 0; i < 10; i++) {
			adapter.add("position" + i);
		}
		listView.setAdapter(adapter);
		setContentView(listView);
	}
}
