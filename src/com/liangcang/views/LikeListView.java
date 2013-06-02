package com.liangcang.views;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.liangcang.R;
import com.liangcang.base.TwoBaseAdapter;
import com.liangcang.managers.DataCallBack;
import com.liangcang.mode.Good;
import com.liangcang.util.ImageDownloader;
import com.liangcang.util.MyToast;
import com.liangcang.util.Util;
import com.liangcang.weigets.LoadMoreListView;
import com.liangcang.weigets.LoadMoreListView.LoadCallBack;

public class LikeListView extends BaseView {

	public LikeListView(Context mContext) {
		super(mContext);
		LoadMoreListView listView = new LoadMoreListView(mContext);
		initAdapter();
		listView.setAdapter(adapter);
		listView.setOnLoadCallBack(new LoadCallBack() {

			@Override
			public void onLoading() {
				adapter.onLoadMore();
			}
		});
		setContentView(listView);
	}

	@Override
	public void onRefresh() {
		adapter.clear();
		adapter.onRefresh();
	}

	private TwoBaseAdapter<Good> adapter;

	private void initAdapter() {
		adapter = new TwoBaseAdapter<Good>(mContext) {
			private DataCallBack<String> callBack = new DataCallBack<String>() {
				@Override
				public void success(String data) {
					List<Good> list = JSON.parseArray(data, Good.class);
					addAll(list);
					notifyDataSetChanged();
				}

				@Override
				public void failure(String msg) {
					MyToast.showMsgShort(mContext, msg);

				}
			};

			@Override
			public String getPath() {

				return "like/list";
			}

			@Override
			public DataCallBack<String> getDataCallBack() {

				return callBack;
			}

			@Override
			public void bindViewAndListener(int position, Good item,
					ImageView img, ImageButton btnHeart, ImageButton btnShare) {
				ImageDownloader.getInstance().download(item.getGoods_image(),
						img);
				img.setTag(position);
				btnHeart.setTag(position);
				btnShare.setTag(position);
				btnHeart.setVisibility(View.VISIBLE);
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
					case R.id.catetory_grid_heartBtn:
						boolean isLike = "1".equals(good.getLiked());
						Util.doLiked(mContext, !isLike, good.getGoods_id());
						if (isLike) {
							good.setLiked("0");
						} else {
							good.setLiked("1");
						}
						break;
					default:
						break;
					}
				}
			};
		};

	}

}
