package com.liangcang.views;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.liangcang.R;
import com.liangcang.base.MyBaseAdapter;
import com.liangcang.weigets.LoadMoreListView;

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

	private EditText etSendContext;
	LoadMoreListView listView;

	public ChatView(Context mContext) {
		super(mContext);
		setContentView(R.layout.chat_send_item);
		listView = (LoadMoreListView) findViewById(R.id.chat_sendLoadMoreListView);
		etSendContext = (EditText) findViewById(R.id.chat_sendTextEt);
		findViewById(R.id.chat_sendBtnSend).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

					}
				});

		listView.setDividerHeight(0);

		for (int i = 0; i < 20; i++) {
			adapter.add("df");

		}
		listView.setAdapter(adapter);

	}

	public void selectLastItem() {
		listView.setSelection(19);
		
	}
}
