package com.liangcang.views;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.liangcang.R;
import com.liangcang.base.MyApplication;
import com.liangcang.base.MyBaseAdapter;
import com.liangcang.managers.DataCallBack;
import com.liangcang.managers.DataManager;
import com.liangcang.mode.CommentNewMode;
import com.liangcang.util.ImageDownloader;
import com.liangcang.util.MyToast;
import com.liangcang.weigets.LoadMoreListView;
import com.liangcang.weigets.LoadMoreListView.LoadCallBack;

public class GoodsCommentNewView extends BaseView {
	private LoadMoreListView listView;
	private MyBaseAdapter<CommentNewMode> adapter;

	public GoodsCommentNewView(Context mContext) {
		super(mContext);
		listView = new LoadMoreListView(mContext);

		initAdapter();
		listView.setAdapter(adapter);
		setContentView(listView);
		listView.setOnLoadCallBack(new LoadCallBack() {

			@Override
			public void onLoading() {
				loadMore();

			}
		});
		getData(1);
	}

	protected void loadMore() {
		if (adapter.getCount() % 20 == 0) {
			getData(adapter.getCount() / 20 + 1);
		}
		listView.onStopLoading();

	}

	private HashMap<String, String> params = new HashMap<String, String>();

	private void getData(int page) {
		params.put("page", page + "");
		params.put("count", "20");
		DataManager.getInstance(mContext).getData("comments", params, true,
				mDataCallBack);

	}

	DataCallBack<String> mDataCallBack = new DataCallBack<String>() {

		public void success(String t) {
			if (!TextUtils.isEmpty(t)) {
				List<CommentNewMode> list = JSON.parseArray(t,
						CommentNewMode.class);
				if (list != null && list.size() > 0) {
					adapter.addAll(list);
					adapter.notifyDataSetChanged();
				}

			}
			listView.onStopLoading();
		}

		@Override
		public void failure(String msg) {
			MyToast.showMsgLong(mContext, msg);
			listView.onStopLoading();
		}
	};

	private void initAdapter() {
		adapter = new MyBaseAdapter<CommentNewMode>() {
			// RelativeLayout.LayoutParams params=new
			// RelativeLayout.LayoutParams(w, h);
			@Override
			public View bindView(int position, CommentNewMode t, View view) {
				if (view == null) {
					view = getLayoutInflater().inflate(R.layout.comment_item,
							null);
				}
				ImageView imgUser = (ImageView) view
						.findViewById(R.id.comment_userImage);

				Button btn = (Button) view
						.findViewById(R.id.comment_userReplyOrDelete);
				TextView tvName = (TextView) view
						.findViewById(R.id.comment_userName);
				TextView tvContext = (TextView) view
						.findViewById(R.id.comment_Context);
				btn.setTag(position);

				btn.setOnClickListener(mClickListener);
				imgUser.setOnClickListener(mClickListener);
				ImageDownloader.getInstance().download(t.getUser_image(),
						imgUser);
				tvName.setText(t.getUser_name());
				tvContext.setText(t.getMsg());
				View leftTemp = view.findViewById(R.id.comment_tempLeft);

				if (!"1".equals(t.getType())) {
					leftTemp.setVisibility(View.VISIBLE);
					btn.setVisibility(View.INVISIBLE);
				} else {
					leftTemp.setVisibility(View.GONE);
					btn.setText("回复");
				}
				if (MyApplication.getUser().getUser_id().equals(t.getUser_id())) {
					btn.setText("删除");
					btn.setVisibility(View.VISIBLE);
				}

				return view;
			}

		};

	}

	private OnClickListener mClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

		}
	};

}
