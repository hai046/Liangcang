package com.liangcang.views;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.liangcang.R;
import com.liangcang.base.BaseThrAdapter;
import com.liangcang.base.BaseThrAdapter.Type;
import com.liangcang.base.TwoBaseAdapter;
import com.liangcang.managers.DataCallBack;
import com.liangcang.mode.DetailUser;
import com.liangcang.mode.Good;
import com.liangcang.util.ImageDownloader;
import com.liangcang.util.Util;
import com.liangcang.weigets.LoadMoreListView;
import com.liangcang.weigets.LoadMoreListView.LoadCallBack;

public class UserView extends BaseView {
	private LoadMoreListView mListView;
	private TwoBaseAdapter<Good> likeAdapter, recommendAdapter;
	private BaseThrAdapter followAdapter, fanAdaper;

	public void setUser(String userId) {
		likeAdapter.setUserId(userId);
		followAdapter.setUserId(userId);
		recommendAdapter.setUserId(userId);
		fanAdaper.setUserId(userId);
		likeAdapter.onRefresh();
		recommendAdapter.onRefresh();
		followAdapter.onRefresh();
		fanAdaper.onRefresh();
	}

	@Override
	public void onRefresh() {
		switch (index) {
		case 0:

			likeAdapter.onRefresh();
			break;
		case 1:
			recommendAdapter.onRefresh();
			break;
		case 2:
			followAdapter.onRefresh();
			break;
		case 3:
			fanAdaper.onRefresh();
			break;

		default:
			break;
		}

	}

	private void loadMore() {
		switch (index) {
		case 0:

			likeAdapter.onLoadMore();
			break;
		case 1:
			recommendAdapter.onLoadMore();
			break;
		case 2:
			followAdapter.onLoadMore();
			break;
		case 3:
			fanAdaper.onLoadMore();
			break;

		default:
			break;
		}
	}

	public UserView(Context mContext) {
		super(mContext);
		mListView = new LoadMoreListView(mContext);
		View viewTop = getLayoutInflater().inflate(R.layout.user_list_layout,
				null);
		initTop(viewTop);
		mListView.addHeaderView(viewTop);
		setContentView(mListView);
		initAdapter();

		mListView.setAdapter(likeAdapter);
		mListView.setOnLoadCallBack(new LoadCallBack() {

			@Override
			public void onLoading() {
				loadMore();

			}
		});

	}

	private void initAdapter() {
		followAdapter = new BaseThrAdapter(mContext);
		followAdapter.setType(Type.Following);
		fanAdaper = new BaseThrAdapter(mContext);
		fanAdaper.setType(Type.Followed);
		likeAdapter = new TwoBaseAdapter<Good>(mContext) {

			private DataCallBack<String> mCallBack = new DataCallBack<String>() {
				public void success(String t) {
					DetailUser user = JSON.parseObject(t, DetailUser.class);
					if (user.getGoods() != null) {
						addAll(user.getGoods());
						user.getGoods().clear();
						notifyDataSetChanged();
					} else {
						
					}

				}

				@Override
				public void failure(String msg) {

				}
			};
			private String path = "user/like";

			@Override
			public String getPath() {
				return path;
			}

			@Override
			public DataCallBack<String> getDataCallBack() {

				return mCallBack;
			}

			@Override
			public void bindViewAndListener(int position,Good item, ImageView img,
					ImageButton btnHeart, ImageButton btnShare) {
				ImageDownloader.getInstance().download(item.getGoods_image(),
						img);

				
				img.setTag(position);
				btnHeart.setTag(position);
				btnShare.setTag(position);
				img.setOnClickListener(mClickListener);
				btnHeart.setOnClickListener(mClickListener);
				btnShare.setOnClickListener(mClickListener);

			}

			private OnClickListener mClickListener = new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (v.getTag() == null)
						return;
					int position = Integer.parseInt(v.getTag().toString());
					Good good = getItem(position);
					switch (v.getId()) {
					case R.id.category_grid_img:
						Util.gotoItemDetail(mContext, good);
						break;

					default:
						break;
					}
				}
			};
		};
		recommendAdapter = new TwoBaseAdapter<Good>(mContext) {
			private DataCallBack<String> mCallBack = new DataCallBack<String>() {
				public void success(String t) {
					DetailUser user = JSON.parseObject(t, DetailUser.class);
					if (user.getGoods() != null) {
						addAll(user.getGoods());
						user.getGoods().clear();
						initHeadData(user);
					} else {
					}
					notifyDataSetChanged();
				}

				@Override
				public void failure(String msg) {

				}
			};
			private String path = "user/recommendation";

			@Override
			public String getPath() {
				return path;
			}

			@Override
			public DataCallBack<String> getDataCallBack() {

				return mCallBack;
			}

			@Override
			public void bindViewAndListener(int position ,Good item, ImageView img,
					ImageButton btnHeart, ImageButton btnShare) {
				ImageDownloader.getInstance().download(item.getGoods_image(),
						img);

				img.setTag(position);
				btnHeart.setTag(position);
				btnShare.setTag(position);
				img.setOnClickListener(mClickListener);
				btnHeart.setOnClickListener(mClickListener);
				btnShare.setOnClickListener(mClickListener);

			}

			private OnClickListener mClickListener = new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (v.getTag() == null)
						return;
					int position = Integer.parseInt(v.getTag().toString());
					Good good = getItem(position);
					switch (v.getId()) {
					case R.id.category_grid_img:
						Util.gotoItemDetail(mContext, good);
						break;

					default:
						break;
					}
				}
			};
		};

//		 recommendAdapter = new BaseThrAdapter(mContext);
//		 recommendAdapter.setType(Type.Recommendation);
//		
//		 likeAdapter = new BaseThrAdapter(mContext);
//		 likeAdapter.setType(Type.Like);
	}

	TextView user_name, user_desc, tvLikeNum, tvRecommendNum, tvAttention,
			tvFansNum;

	private void initHeadData(DetailUser user) {
		user_name.setText(user.getUser_name());
		user_desc.setText(user.getUser_desc());
		tvLikeNum.setText(user.getLike_count());
		tvRecommendNum.setText(user.getRecommendation_count());
		tvAttention.setText(user.getFollowing_count());
		tvFansNum.setText(user.getFollowed_count());
	}

	private OnClickListener mClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.user_menu_likeLayout:
				switchAdapter(0);
				break;
			case R.id.user_menu_recommendLayout:
				switchAdapter(1);
				break;
			case R.id.user_menu_attentionLayout:
				switchAdapter(2);
				break;
			case R.id.user_menu_fansLayout:
				switchAdapter(3);
				break;

			default:
				break;
			}

		}
	};

	private void initTop(View viewTop) {
		viewTop.findViewById(R.id.user_menu_likeLayout).setOnClickListener(
				mClickListener);
		viewTop.findViewById(R.id.user_menu_recommendLayout)
				.setOnClickListener(mClickListener);
		viewTop.findViewById(R.id.user_menu_attentionLayout)
				.setOnClickListener(mClickListener);
		viewTop.findViewById(R.id.user_menu_fansLayout).setOnClickListener(
				mClickListener);

		tvLikeNum = (TextView) viewTop.findViewById(R.id.user_menu_likeNum);
		tvRecommendNum = (TextView) viewTop
				.findViewById(R.id.user_menu_recommendNum);
		tvAttention = (TextView) viewTop
				.findViewById(R.id.user_menu_attentionNum);
		tvFansNum = (TextView) viewTop.findViewById(R.id.user_menu_fansNum);

		user_name = (TextView) viewTop.findViewById(R.id.user_name);
		user_desc = (TextView) viewTop.findViewById(R.id.user_desc);

		Button btnFollow = (Button) viewTop.findViewById(R.id.user_follow);
		Button btnUser_owermsg = (Button) viewTop
				.findViewById(R.id.user_owermsg);

	}

	// user/recommendation
	private int index = 0;

	public void switchAdapter(int position) {
		mListView.setSelection(0);
		index = position;
		switch (position) {
		case 0:
			mListView.setAdapter(likeAdapter);
			break;
		case 1:
			mListView.setAdapter(recommendAdapter);

			break;
		case 2:
			mListView.setAdapter(followAdapter);

			break;
		case 3:
			mListView.setAdapter(fanAdaper);
			break;

		default:
			break;
		}

	}

}
