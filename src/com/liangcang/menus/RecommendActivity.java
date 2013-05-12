package com.liangcang.menus;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.liangcang.R;
import com.liangcang.base.BaseListActivity;
import com.liangcang.base.MyBaseAdapter;
import com.liangcang.mode.Good;
import com.liangcang.util.ImageDownloader;
import com.liangcang.util.MyLog;
import com.liangcang.util.MyToast;
import com.liangcang.util.Util;

public class RecommendActivity extends BaseListActivity<Good> {

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setAdapter(adapter);
		onRefresh();
		int widht = (int) (Util.getDisplayWindth(this) * 0.5);
		params = new LinearLayout.LayoutParams(widht, widht);
	}

	class MyAdapter extends MyBaseAdapter<Good> {

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
	}

	MyAdapter adapter = new MyAdapter();
	LinearLayout.LayoutParams params;

	protected View getGridItemView(int position, View view) {
		if (view == null || !(view instanceof LinearLayout)) {
			params.weight = 1;
			LinearLayout layout = new LinearLayout(this);
			layout.setOrientation(LinearLayout.HORIZONTAL);
			params.leftMargin = 1;
			params.rightMargin = 0;
			adapter.getItem(position * 2);
			View v1 = getLayoutItemView(null, adapter.getItem(position * 2),
					position * 2);
			v1.setTag(position * 2);
			layout.addView(v1, params);
			params.leftMargin = 0;
			params.rightMargin = 1;
			if (position * 2 + 1 < adapter.size()) {
				View v = getLayoutItemView(null,
						adapter.getItem(position * 2 + 1), position * 2 + 1);
				layout.addView(v, params);
				// v.setTag( position * 2 + 1 );
			} else {
				View v = getLayoutItemView(null, adapter.getItem(position * 2),
						-1);
				v.setVisibility(View.INVISIBLE);
				layout.addView(v, params);
				// v.setTag( -1 );
			}
			return layout;
		} else {
			LinearLayout layout = (LinearLayout) view;
			// View v1 = layout.getChildAt( 0 );
			getLayoutItemView(layout.getChildAt(0),
					adapter.getItem(position * 2), position * 2);

			if (position * 2 + 1 < adapter.size()) {
				View v = getLayoutItemView(layout.getChildAt(1),
						adapter.getItem(position * 2 + 1), position * 2 + 1);
				v.setVisibility(View.VISIBLE);
			} else {
				View v = getLayoutItemView(layout.getChildAt(1),
						adapter.getItem(position * 2), -1);
				v.setVisibility(View.INVISIBLE);
			}
			return layout;
		}

	}

	private View getLayoutItemView(View convertView, Good item, int position) {

		if (convertView == null) {
			convertView = getLayoutInflater().inflate(
					R.layout.category_grid_item, null);
		}
		ImageView img = (ImageView) convertView
				.findViewById(R.id.category_grid_img);
		ImageDownloader.getInstance().download(item.getGoods_image(), img);
		img.setTag(position);
		img.setOnClickListener(mOnClickListener);
		return convertView;
	}

	private OnClickListener mOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (v.getTag() instanceof Integer) {
				MyLog.i("Recommend", "onClick  v.getTag()=" + v.getTag());
				int position = (Integer) v.getTag();
				if (position >= 0 || position < adapter.size()) {
					Util.gotoItemDetail(RecommendActivity.this,
							adapter.getItem(position));

				}

			}

		}
	};

	@Override
	public String path() {
		return "recommendation";
	}

	@Override
	public Class<Good> getModeClass() {
		return Good.class;
	}

	@Override
	public void onReceiveFailure(String msg) {
		listview.onStopLoading();
		MyToast.showMsgLong(this, msg);
	}


	@Override
	public void onReceiveSuccess() {
		listview.onStopLoading();
	}

}
