package com.liangcang.views;

import com.liangcang.R;
import com.liangcang.base.MyBaseAdapter;

import android.R.color;
import android.content.Context;
import android.view.View;
import android.widget.GridView;

public class FansView extends BaseView {

	private MyBaseAdapter<String> adapter = new MyBaseAdapter<String>() {

		@Override
		public View bindView(int position, String t, View view) {
			if (view == null) {
				view = getLayoutInflater().inflate(R.layout.fans_layout, null);
			}
			return view;
		}
	};

	public FansView(Context mContext) {
		super(mContext);
		GridView mGridView = new GridView(mContext);
		mGridView.setVerticalSpacing(5);
		mGridView.setBackgroundColor(mContext.getResources().getColor(
				R.color.black));
		mGridView.setNumColumns(3);
		for (int i = 0; i < 7; i++) {
			adapter.add("" + i);
		}
		mGridView.setAdapter(adapter);
		setContentView(mGridView);

	}

	public void ifLoadMoreNotData() {
		// TODO Auto-generated method stub
		
	}

}
