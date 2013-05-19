package com.liangcang.views;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.liangcang.ChatActivity;
import com.liangcang.R;
import com.liangcang.base.MyBasePageAdapter;
import com.liangcang.mode.Good;
import com.liangcang.util.ImageDownloader;
import com.liangcang.util.RichText;
import com.liangcang.util.Util;
import com.liangcang.weigets.LoadMoreListView;
import com.liangcang.weigets.LoadMoreListView.LoadCallBack;

public class PrivateMsgView extends BaseView implements OnClickListener {

	private LoadMoreListView moreListView;
	private MyBasePageAdapter<Good> adapter;

	public PrivateMsgView(final Context mContext) {
		super(mContext);
		moreListView = new LoadMoreListView(mContext);
		moreListView.setDividerHeight(0);
		setContentView(moreListView);
		adapter = new MyBasePageAdapter<Good>(mContext) {

			@Override
			public String path() {
				return "messages/list";
			}

			@Override
			public void onReceiveSuccess() {
				moreListView.onStopLoading();
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
					view = getLayoutInflater().inflate(
							R.layout.private_msg_layout, null);
				}
				ImageView uesrImage = (ImageView) view
						.findViewById(R.id.privateMsgImageUser);
				ImageDownloader.getInstance().download(t.getUser_image(),
						uesrImage);
				TextView msg = (TextView) view
						.findViewById(R.id.privateMsgContent);

				RichText rt = new RichText(mContext);
				rt.addTextColor("来自" , mContext.getResources()
						.getColor(R.color.white));
				rt.addTextColor(t.getUser_name(), mContext.getResources()
						.getColor(R.color.blue));
				rt.addTextColor("：" + t.getMsg(), mContext.getResources()
						.getColor(R.color.white));
				msg.setText(rt);

				Button btnReply = (Button) view
						.findViewById(R.id.privateMsgReplyBtn);
				btnReply.setTag(position);
				msg.setTag(position);
				uesrImage.setTag(position);

				msg.setOnClickListener(PrivateMsgView.this);
				uesrImage.setOnClickListener(PrivateMsgView.this);
				btnReply.setOnClickListener(PrivateMsgView.this);
				return view;
			}

		};

		moreListView.setAdapter(adapter);
		
		moreListView.setOnLoadCallBack(new LoadCallBack() {

			@Override
			public void onLoading() {
				
				adapter.loadMore();

			}
		});
		adapter.onRefresh();
	}

	@Override
	public void onClick(View v) {

		if (v.getTag() == null) {
			return;
		}
		int position = Integer.parseInt(v.getTag().toString());
		Good good = adapter.getItem(position);
		switch (v.getId()) {
		case R.id.privateMsgReplyBtn:
		case R.id.privateMsgContent:
			Intent intent = new Intent();
			intent.setClass(mContext, ChatActivity.class);
			intent.putExtra(ChatActivity.MessageId, good.getMessage_id());
			mContext.startActivity(intent);
			
			break;
		case R.id.privateMsgImageUser:
		
			Util.gotoUser(mContext, good.getUser_id(), good.getUser_image(),
					good.getUser_name());
			// break;
			break;
		default:
			break;
		}

	}

	@Override
	public void onRefresh() {
		adapter.onRefresh();
	}

}
