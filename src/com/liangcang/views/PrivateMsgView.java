package com.liangcang.views;

import com.liangcang.ChatActivity;
import com.liangcang.R;
import com.liangcang.base.MyBaseAdapter;
import com.liangcang.views.GridPicsItemView.MyAdapter;
import com.liangcang.weigets.LoadMoreListView;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class PrivateMsgView extends BaseView implements OnClickListener{

	private LoadMoreListView moreListView;

	public PrivateMsgView(Context mContext) {
		super(mContext);
		moreListView=new LoadMoreListView(mContext);
		moreListView.setDividerHeight(0);
		setContentView(moreListView);
		for (int i = 0; i < 10; i++) {
			adapter.add("" + i);
		}
		moreListView.setAdapter(adapter);

	}

	private MyBaseAdapter<String> adapter = new MyBaseAdapter<String>() {

		@Override
		public View bindView(int position, String t, View view) {
			if (view == null) {
				view = getLayoutInflater().inflate(R.layout.private_msg_layout,
						null);
				
			}
			Button btnReply=(Button) view.findViewById(R.id.privateMsgReplyBtn);
			btnReply.setOnClickListener(PrivateMsgView.this);
			return view;
		}
	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.privateMsgReplyBtn:
			Intent intent=new Intent();
			intent.setClass(mContext, ChatActivity.class);
			mContext.startActivity(intent);
			break;

		default:
			break;
		}
		
	}

}
