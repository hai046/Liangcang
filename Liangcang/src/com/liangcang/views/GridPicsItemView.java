package com.liangcang.views;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.liangcang.R;
import com.liangcang.base.MyBaseAdapter;
import com.liangcang.weigets.LoadMoreListView;

public class GridPicsItemView extends BaseView {

	public GridPicsItemView(Context mContext) {
		super(mContext);
		LoadMoreListView listview = new LoadMoreListView(mContext);
		setContentView(listview);
		for (int i = 0; i < 10; i++) {
			adapter.add("" + i);
		}
		listview.setAdapter(adapter);

	}

	/* private MyBaseAdapter<String> adapter = new */
	class MyAdapter extends MyBaseAdapter<String> {

		@Override
		public int getCount() {
			return (size() % 2 == 0 ? size() / 2 : ((size() / 2 + 1)));
		}

		public int size() {
			return super.getCount();
		}

		@Override
		public View bindView(int position, String t, View view) {
			// TODO Auto-generated method stub
			return getGridItemView(position, view);
		}
	}

	MyAdapter adapter = new MyAdapter();
	LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
			LinearLayout.LayoutParams.FILL_PARENT,
			LinearLayout.LayoutParams.WRAP_CONTENT);

	protected View getGridItemView(int position, View view) {
		if (view == null || !(view instanceof LinearLayout)) {
			params.weight = 1;
			LinearLayout layout = new LinearLayout(mContext);
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

	private View getLayoutItemView(View convertView, String item, int position) {
		ImageView img = null;
		if (convertView == null) {
			convertView = getLayoutInflater().inflate(
					R.layout.category_grid_item, null);
		}
		// img = (ImageView) convertView.findViewById(R.id.category_grid_img);
		// TextView tvScreditScore = (TextView) convertView
		// .findViewById(R.id.category_grid_screditscore);
		// TextView tvPrice = (TextView) convertView
		// .findViewById(R.id.category_grid_price);
		// TextView tvVolumn = (TextView) convertView
		// .findViewById(R.id.category_grid_volumn);

		return convertView;
	}

}
