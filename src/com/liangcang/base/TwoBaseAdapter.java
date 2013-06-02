package com.liangcang.base;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.liangcang.R;
import com.liangcang.managers.DataCallBack;
import com.liangcang.managers.DataManager;
import com.liangcang.util.Util;

public abstract class TwoBaseAdapter<T> extends MyBaseAdapter<T> {
	private LinearLayout.LayoutParams params;
	private Context mContext;

	public TwoBaseAdapter(Context mContext) {
		this.mContext = mContext;
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
	public View bindView(int position, T t, View view) {
		return getGridItemView(position, view);
	}

	protected View getGridItemView(int position, View view) {
		if (view == null || !(view instanceof LinearLayout)) {
			params.weight = 1;
			LinearLayout layout = new LinearLayout(mContext);
			layout.setOrientation(LinearLayout.HORIZONTAL);
			params.leftMargin = 1;
			params.rightMargin = 0;
			getItem(position * 2);
			View v1 = getLayoutItemView(null, getItem(position * 2),
					position * 2);
			v1.setTag(position * 2);
			layout.addView(v1, params);
			params.leftMargin = 0;
			params.rightMargin = 1;
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

	public View getLayoutItemView(View convertView, T item, int position) {
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
		bindViewAndListener(position,item, img, btnHeart, btnShare);
		return convertView;
	}

	/**
	 * "/user/like"
	 * 
	 * @return
	 */
	public abstract String getPath();

	private Map<String, String> qurestParams = new HashMap<String, String>();

	public void addParams(String key, String value) {
		qurestParams.put(key, value);
	}

	public void setUserId(String userId) {
		qurestParams.put("owner_id", userId);

	}

	private int page = 1;

	public void onRefresh() {
		page = 1;
		qurestParams.put("page", page + "");
		doRequest();
	}

	public void onLoadMore() {
		
		page=size()/18+1;
		qurestParams.put("page", page + "");
		doRequest();
	}

	
	
	private void doRequest() {
		qurestParams.put("count", "18");
		DataManager.getInstance(mContext).getData(getPath(), qurestParams,
				true, getDataCallBack());
	}

	/**
	 * 数据返回
	 * 
	 * @return
	 */
	public abstract DataCallBack<String> getDataCallBack();

	/**
	 * 绑定数据和事件
	 * 
	 * @param img
	 *            显示的大图片
	 * @param btnHeart
	 * @param btnShare
	 */
	public abstract void bindViewAndListener(int position,T item, ImageView img,
			ImageButton btnHeart, ImageButton btnShare);
	/*
	 * private DataCallBack<String> mDataCallBack = new DataCallBack<String>() {
	 * 
	 * @Override public void failure(String msg) { // TODO Auto-generated method
	 * stub
	 * 
	 * }
	 * 
	 * public void success(String t) {
	 * 
	 * }
	 * 
	 * };
	 */

	/*
	 * private OnClickListener mOnClickListener = new OnClickListener() {
	 * 
	 * @Override public void onClick(View v) { if (v.getTag() instanceof
	 * Integer) { MyLog.i("Recommend", "onClick  v.getTag()=" + v.getTag()); int
	 * position = (Integer) v.getTag();
	 * 
	 * 
	 * }
	 * 
	 * } };
	 */
}
