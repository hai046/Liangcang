package com.liangcang.views;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.liangcang.R;
import com.liangcang.base.MyApplication;
import com.liangcang.base.MyBaseAdapter;
import com.liangcang.managers.DataCallBack;
import com.liangcang.managers.DataManager;
import com.liangcang.mode.Comment;
import com.liangcang.mode.CommentMode;
import com.liangcang.util.ImageDownloader;
import com.liangcang.util.MyLog;
import com.liangcang.util.MyToast;
import com.liangcang.util.Util;
import com.liangcang.weigets.LoadMoreListView;
import com.liangcang.weigets.LoadMoreListView.LoadCallBack;

public class GoodCommentView extends BaseView {
	View headView;
	private ImageView gComment_like0, gComment_like1, gComment_like2,
			gComment_like3, gComment_like4, gComment_goodImage;
	private Button btnMore;
	private View TopViewInfo;
	// private String myUserId;
	private EditText etCommentText;
	private LoadMoreListView listView;

	public GoodCommentView(Context mContext, final String goods_id,
			String goods_image) {
		super(mContext);
		isShow = false;
		setContentView(R.layout.chat_send_item);
		findViewById(R.id.chat_sendBtnSend).setOnClickListener(mClickListener);
		etCommentText = (EditText) findViewById(R.id.chat_sendTextEt);
		listView = (LoadMoreListView) findViewById(R.id.chat_sendLoadMoreListView);

		View headView = getLayoutInflater().inflate(
				R.layout.good_comment_layout, null);
		TopViewInfo = headView.findViewById(R.id.gComment_layoutTop);
		gComment_like0 = (ImageView) headView.findViewById(R.id.gComment_like0);
		gComment_like1 = (ImageView) headView.findViewById(R.id.gComment_like1);
		gComment_like2 = (ImageView) headView.findViewById(R.id.gComment_like2);
		gComment_like3 = (ImageView) headView.findViewById(R.id.gComment_like3);
		gComment_like4 = (ImageView) headView.findViewById(R.id.gComment_like4);
		gComment_goodImage = (ImageView) headView
				.findViewById(R.id.gComment_goodImage);
		ImageDownloader.getInstance().download(goods_image, gComment_goodImage);
		btnMore = (Button) headView.findViewById(R.id.gComment_more);
		btnMore.setOnClickListener(mClickListener);
		gComment_like0.setOnClickListener(mClickListener);
		gComment_like1.setOnClickListener(mClickListener);
		gComment_like2.setOnClickListener(mClickListener);
		gComment_like3.setOnClickListener(mClickListener);
		gComment_like4.setOnClickListener(mClickListener);
		listView.addHeaderView(headView);

		adapter = new MyBaseAdapter<Comment>() {
			// RelativeLayout.LayoutParams params=new
			// RelativeLayout.LayoutParams(w, h);
			@Override
			public View bindView(int position, Comment t, View view) {
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

				if (t.isReply) {
					leftTemp.setVisibility(View.VISIBLE);
					// btn.setVisibility(View.INVISIBLE);
				} else {
					leftTemp.setVisibility(View.GONE);
					btn.setText("回复");
				}
				if (MyApplication.getUser().getUser_id().equals(t.getUser_id())) {
					btn.setText("删除");
					// btn.setVisibility(View.VISIBLE);
				} else {
					// leftTemp.setVisibility(View.GONE);
					// btn.setVisibility(View.VISIBLE);
					btn.setText("回复");
				}

				return view;
			}

		};
		this.goods_id = goods_id;
		listView.setAdapter(adapter);
		listView.setOnLoadCallBack(new LoadCallBack() {

			@Override
			public void onLoading() {
				getData();
			}
		});
		getData();
	}

	private MyBaseAdapter<Comment> adapter;
	private OnClickListener mClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.chat_sendBtnSend:

				sendComment();
				break;
			case R.id.comment_userImage:
				if (v.getTag() == null)
					return;
				int position = Integer.parseInt(v.getTag().toString());
				Comment mComment = adapter.getItem(position);
				Util.gotoUser(mContext, mComment.getUser_id(),
						mComment.getUser_image(), mComment.getUser_name());

				break;
			case R.id.comment_userReplyOrDelete:
				if (v.getTag() == null)
					return;
				position = Integer.parseInt(v.getTag().toString());
				mComment = adapter.getItem(position);

				if (MyApplication.getUser().getUser_id()
						.equals(mComment.getUser_id())) {
					deleteComment(mComment, position);
				} else {
					replyComment(mComment);
				}

				break;
			case R.id.gComment_like0:

				break;
			case R.id.gComment_like1:

				break;
			case R.id.gComment_like2:

				break;
			case R.id.gComment_like3:

				break;
			case R.id.gComment_like4:

				break;
			case R.id.gComment_more:

				break;
			default:
				break;
			}
		}

	};
	private String goods_id;

	private void getData() {

		int page = 1;

		if (adapter.getCount() % 20 == 0) {
			page = adapter.getCount() / 20 + 1;
		} else {
			listView.onStopLoading();
			return;
		}

		DataManager.getInstance(mContext).getGoodComments(goods_id, page, 20,
				mDataCallBack);

	}

	private void sendComment() {
		String text = this.etCommentText.getText().toString();
		if (TextUtils.isEmpty(text)) {
			MyToast.showMsgShort(mContext, "评论不能为空！");
			return;
		}
		etCommentText.setTag(text);
		etCommentText.setText(null);
		Map<String, String> params = new HashMap<String, String>();
		params.put("goods_id", goods_id);
		if (replyComment != null) {
			if (replyComment.isReply) {
				// if (!TextUtils.isEmpty(replyComment.getUser_id())) {
				params.put("parent_uid", replyComment.getUser_id());
				// params.put("msg_id", replyComment.getReply_id());
				// }
			}
			params.put("msg_id", replyComment.getComment_id());

			// if(!TextUtils.isEmpty(replyComment.getParents_id()))
			// {
			// params.put("parents_id", replyComment.getParents_id());
			// }

		}
		params.put("text", text);
		DataManager.getInstance(mContext).doPost("comments/add", params,

		new DataCallBack<String>() {
			@Override
			public void success(String t) {
				MyLog.i("comment", t);
				Comment mComment = new Comment();
				mComment.setUser_name(MyApplication.getUser().getUser_name());
				mComment.setUser_id(MyApplication.getUser().getUser_id());
				mComment.setMsg(etCommentText.getTag().toString());
				mComment.setUser_image(MyApplication.getUser().getUser_image());
				if (replyComment != null) {
					mComment.isReply = true;
					GoodCommentView.this.replyComment = null;
					adapter.clear();
					listView.setSelection(0);
					getData();
					etCommentText.setHint(null);
				} else {
					adapter.insert(0, mComment);
					listView.setSelection(0);
					adapter.notifyDataSetChanged();
				}

			}

			@Override
			public void failure(String msg) {
				MyToast.showMsgShort(mContext, msg);
				etCommentText.setText(etCommentText.getTag().toString());

			}
		});
		try {
			InputMethodManager imm = (InputMethodManager) mContext
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(etCommentText.getWindowToken(), 0);
			// imm.toggleSoftInput(0, InputMethodManager.HIDE_IMPLICIT_ONLY);
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	private Comment replyComment;

	protected void replyComment(Comment mComment) {
		replyComment = mComment;// mComment.getComment_id();
		this.etCommentText.setHint("回复：" + mComment.getUser_name());
	}

	protected void deleteComment(Comment mComment, final int position) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("msg_id", mComment.getComment_id());
		params.put("goods_id", goods_id);
		DataManager.getInstance(mContext).doPost("comments/delete", params,
				new DataCallBack<String>() {

					@Override
					public void success(String t) {

						adapter.removeAt(position);
						adapter.notifyDataSetChanged();
					}

					@Override
					public void failure(String msg) {
						MyToast.showMsgShort(mContext, msg);

					}
				});

	}

	private boolean isShow = false;

	private void initView(CommentMode mCommentMode) {
		if (mCommentMode.getCommentList() != null) {
			adapter.addAll(mCommentMode.getCommentList());
			adapter.notifyDataSetChanged();
		}
		if (isShow)
			return;

		if (mCommentMode.getLike() == null || mCommentMode.getLike().size() < 1) {
			TopViewInfo.setVisibility(View.GONE);
			return;
		}
		isShow = true;
		if (mCommentMode.getLike().size() > 0
				&& mCommentMode.getLike().get(0) != null) {
			ImageDownloader.getInstance().download(
					mCommentMode.getLike().get(0).getUser_image(),
					gComment_like0);
			gComment_like0.setVisibility(View.VISIBLE);
		} else if (mCommentMode.getLike().size() > 1
				&& mCommentMode.getLike().get(1) != null) {
			ImageDownloader.getInstance().download(
					mCommentMode.getLike().get(1).getUser_image(),
					gComment_like1);
			gComment_like1.setVisibility(View.VISIBLE);
		} else if (mCommentMode.getLike().size() > 2
				&& mCommentMode.getLike().get(2) != null) {
			ImageDownloader.getInstance().download(
					mCommentMode.getLike().get(2).getUser_image(),
					gComment_like2);
			gComment_like2.setVisibility(View.VISIBLE);
		} else if (mCommentMode.getLike().size() > 3
				&& mCommentMode.getLike().get(3) != null) {
			ImageDownloader.getInstance().download(
					mCommentMode.getLike().get(3).getUser_image(),
					gComment_like3);
			gComment_like3.setVisibility(View.VISIBLE);
		} else if (mCommentMode.getLike().size() > 4
				&& mCommentMode.getLike().get(4) != null) {
			ImageDownloader.getInstance().download(
					mCommentMode.getLike().get(4).getUser_image(),
					gComment_like4);
			gComment_like4.setVisibility(View.VISIBLE);
		}

	}

	private DataCallBack<String> mDataCallBack = new DataCallBack<String>() {

		public void success(String t) {
			if (TextUtils.isEmpty(t)) {
				return;
			}
			CommentMode mCommentMode = JSON.parseObject(t, CommentMode.class);
			initView(mCommentMode);
			listView.onStopLoading();
		}

		@Override
		public void failure(String msg) {
			MyToast.showMsgLong(mContext, msg);
			listView.onStopLoading();
		}
	};

}
