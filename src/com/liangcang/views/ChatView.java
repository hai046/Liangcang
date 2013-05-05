package com.liangcang.views;

import java.text.SimpleDateFormat;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.liangcang.R;
import com.liangcang.base.MyApplication;
import com.liangcang.base.MyBasePageAdapter;
import com.liangcang.mode.Good;
import com.liangcang.util.ImageDownloader;
import com.liangcang.util.MyLog;
import com.liangcang.weigets.LoadMoreListView;
import com.liangcang.weigets.LoadMoreListView.LoadCallBack;

public class ChatView extends BaseView {
	/*
	 * private MyBaseAdapter<String> adapter = new MyBaseAdapter<String>() {
	 * 
	 * @Override public View bindView(int position, String t, View view) { if
	 * (view == null) { if (position % 3 == 0) { view =
	 * getLayoutInflater().inflate(R.layout.chat_item_me, null); } else { view =
	 * getLayoutInflater().inflate(R.layout.chat_item_left, null); }
	 * 
	 * } return view; } };
	 */

	MyBasePageAdapter<Good> adapter = new MyBasePageAdapter<Good>(mContext) {

		@Override
		public String path() {
			return "messages";
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
			MyLog.e("hh", "good " + t);
//			if (position % 3 == 0) {
//				
//			} else {
//				view = getLayoutInflater().inflate(R.layout.chat_item_left,
//						null);
//			}
			if(!t.getUser_id().equals(MyUserId))
			{
				view = getLayoutInflater().inflate(R.layout.chat_item_left,
						null);
				TextView tvUserName=(TextView) view.findViewById(R.id.chat_userName);
				TextView tvUserDesc=(TextView) view.findViewById(R.id.chat_userDesc);
				tvUserDesc.setText(t.getUser_desc());
				tvUserName.setText(t.getUser_name());
				
			}else
			{
				view = getLayoutInflater().inflate(R.layout.chat_item_me, null);
			}
			
			
			ImageView userImage=(ImageView) view.findViewById(R.id.chat_meImage);
			TextView chat_meContent=(TextView) view.findViewById(R.id.chat_meContent);
			TextView  chat_meTime=(TextView) view.findViewById(R.id.chat_meTime);
			
			ImageDownloader.getInstance().download(t.getUser_image(), userImage);
			chat_meContent.setText(t.getMsg());
			chat_meTime.setText(sdf.format(t.getCreate_time()));
			return view;
		}

	};
	SimpleDateFormat sdf=new SimpleDateFormat("MM-dd HH:mm:ss");
	private EditText etSendContext;
	LoadMoreListView listView;

	public void setMessageId(String msg_id) {
		adapter.addParam("msg_id", msg_id);
		adapter.onRefresh();
	}
	private String MyUserId;
	public ChatView(Context mContext) {
		super(mContext);
		MyUserId=((MyApplication)mContext.getApplicationContext()).getUser().getUser_id();
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

		listView.setAdapter(adapter);
		listView.setOnLoadCallBack(new LoadCallBack() {

			@Override
			public void onLoading() {
				adapter.loadMore();

			}
		});
		
	}

	public void selectLastItem() {
		listView.setSelection(19);

	}

}
