package com.liangcang.views;

import com.liangcang.R;
import com.liangcang.base.MyBaseAdapter;
import com.liangcang.weigets.LoadMoreListView;

import android.content.Context;
import android.view.View;

public class ChatView extends BaseView {

	private MyBaseAdapter<String> adapter = new MyBaseAdapter<String>() {

		@Override
		public View bindView(int position, String t, View view) {
			if (view == null) {
				if (position % 3 == 0) {
					view = getLayoutInflater().inflate(R.layout.chat_item_me,
							null);
				} else {
					view = getLayoutInflater().inflate(R.layout.chat_item_left,
							null);
				}

			}
			return view;
		}
	};

	public ChatView(Context mContext) {
		super(mContext);
		LoadMoreListView listView = new LoadMoreListView(mContext);
		listView.setDividerHeight(0);
		
		setContentView(listView);
		for (int i = 0; i < 20; i++) {
			adapter.add("df");

		}
		listView.setAdapter(adapter);

	}
}
