package com.liangcang.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.liangcang.R;
import com.liangcang.managers.DataCallBack;
import com.liangcang.managers.DataManager;
import com.liangcang.mode.Good;
import com.liangcang.util.ImageDownloader;
import com.liangcang.util.Util;

/**
 * 商品分类适配器
 * 
 * @author 邓海柱 haizhu12345@gmail.com
 */
public class BaseGoodAdapter extends MyBaseAdapter<Good> {

	private LinearLayout.LayoutParams params;
	private Context mContext;
	private int margin = 5;

	public BaseGoodAdapter(Context mContext) {
		this.mContext = mContext;
		margin = mContext.getResources().getDimensionPixelSize(R.dimen.margin5);
		int widht = (int) (Util.getDisplayWindth(mContext) * 0.5);
		params = new LinearLayout.LayoutParams(widht, widht);
	}

	@Override
	public int getCount() {
		return (size() % 2 == 0 ? size() / 2 : ((size() / 2 + 1)));
	}

	public int size() {
		return super.getCount();
	}

	@Override
	public View bindView(int position, Good t, View view) {
		return getGridItemView(position, view);
	}

	protected View getGridItemView(int position, View view) {
		if (view == null || !(view instanceof LinearLayout)) {
			params.weight = 1;
			LinearLayout layout = new LinearLayout(mContext);
			layout.setOrientation(LinearLayout.HORIZONTAL);
			params.leftMargin = margin;
			params.rightMargin = margin;
			params.bottomMargin = margin * 2;
			getItem(position * 2);
			View v1 = getLayoutItemView(null, getItem(position * 2),
					position * 2);
			v1.setTag(position * 2);
			layout.addView(v1, params);
			// params.leftMargin = margin;
			// params.rightMargin = margin;
			if (position * 2 + 1 < size()) {
				View v = getLayoutItemView(null, getItem(position * 2 + 1),
						position * 2 + 1);
				layout.addView(v, params);
				// v.setTag( position * 2 + 1 );
			} else {
				View v = getLayoutItemView(null, getItem(position * 2), -1);
				v.setVisibility(View.INVISIBLE);
				layout.addView(v, params);
				// v.setTag( -1 );
			}
			return layout;
		} else {
			LinearLayout layout = (LinearLayout) view;
			// View v1 = layout.getChildAt( 0 );
			getLayoutItemView(layout.getChildAt(0), getItem(position * 2),
					position * 2);

			if (position * 2 + 1 < size()) {
				View v = getLayoutItemView(layout.getChildAt(1),
						getItem(position * 2 + 1), position * 2 + 1);
				v.setVisibility(View.VISIBLE);
			} else {
				View v = getLayoutItemView(layout.getChildAt(1),
						getItem(position * 2), -1);
				v.setVisibility(View.INVISIBLE);
			}
			return layout;
		}

	}

	public View getLayoutItemView(View convertView, Good item, int position) {
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.category_grid_item, null);
		}
		ImageView img = (ImageView) convertView
				.findViewById(R.id.category_grid_img);
		ImageButton btnHeart = (ImageButton) convertView
				.findViewById(R.id.catetory_grid_heartBtn);
		ImageButton btnShare = (ImageButton) convertView
				.findViewById(R.id.catetory_grid_share);
		ImageDownloader.getInstance().download(item.getGoods_image(), img);
		img.setTag(position);
		btnHeart.setTag(position);
		btnShare.setTag(position);
		img.setOnClickListener(mClickListener);
		btnHeart.setOnClickListener(mClickListener);
		btnShare.setOnClickListener(mClickListener);

		// bindViewAndListener(item,img, btnHeart, btnShare);
		return convertView;
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
	private String path;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	private Map<String, String> qurestParams = new HashMap<String, String>();
	private int count = 1;

	public void onRefresh() {

		isClear = true;
		count = 1;
		qurestParams.put("page", "" + count);
		doRequest();
	}

	public boolean hasMore() {
		return size() % 20 == 0;
	}

	public void onLoadMore() {

		if (hasMore()) {
			count = size() / 20 + 1;
			qurestParams.put("page", "" + count);
			isClear = false;
			doRequest();
		}
		success();

	}

	/**
	 * 
	 * @param type
	 */
	public void setType(int type) {
		qurestParams.put("type", "" + type);

	}

	private void doRequest() {
		qurestParams.put("count", "20");
		DataManager.getInstance(mContext).getData(getPath(), qurestParams,
				true, mCallBack);
	}

	public void failure(String msg) {

	}

	public void success() {

	}

	private DataCallBack<String> mCallBack = new DataCallBack<String>() {
		public void success(String t) {
			List<Good> users = JSON.parseArray(t, Good.class);
			if (users != null) {
				addAll(users);
				notifyDataSetChanged();
			}
			BaseGoodAdapter.this.success();
		}

		@Override
		public void failure(String msg) {
			BaseGoodAdapter.this.failure(msg);
		}
	};
}
