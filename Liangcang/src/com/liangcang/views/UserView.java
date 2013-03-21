package com.liangcang.views;

import android.content.Context;
import android.hardware.Camera.Size;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.liangcang.R;
import com.liangcang.base.MyBaseAdapter;
import com.liangcang.weigets.LoadMoreListView;

public class UserView extends BaseView implements OnClickListener {
	private LoadMoreListView mListView;

	public UserView(Context mContext) {
		super(mContext);
		mListView = new LoadMoreListView(mContext);
		mListView.addHeaderView(getLayoutInflater().inflate(
				R.layout.user_list_layout, null));
		setContentView(mListView);
		// GridView mGridView = (GridView) findViewById(R.id.user_gridView);

		for (int i = 0; i < 50; i++) {
			adapter.add("dfa");
		}
		mListView.setAdapter(adapter);

	}

	private MyAdapter adapter = new MyAdapter();

	class MyAdapter extends MyBaseAdapter<String> {

		public MyAdapter() {
			params.weight = 1;
		}

		@Override
		public int getCount() {
			int size = super.getCount();
			return (size % 3 == 0 ? (size / 3) : (size / 3 + 1));
		}

		public int getSize() {
			return getDateSet().size();
		}

		private LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.FILL_PARENT);

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			LinearLayout mLinearLayout;
			if (convertView == null) {
				mLinearLayout = new LinearLayout(mContext);
				mLinearLayout.setGravity(Gravity.LEFT);
			} else {
				mLinearLayout = (LinearLayout) convertView;
				if (position + 1 == getCount())
					mLinearLayout.removeAllViews();
			}
			int index = position * 3;
			View view = mLinearLayout.getChildAt(0);
			if (view == null) {
				mLinearLayout.addView(
						bindView(index, adapter.getItem(index), view), 0,
						params);
			} else {
				bindView(index, adapter.getItem(index), view);
			}
			view = mLinearLayout.getChildAt(0);
			view.setTag(index);
			view.setOnClickListener(UserView.this);

			index++;
			view = mLinearLayout.getChildAt(1);
			if (index < getSize()) {
				if (view == null) {
					mLinearLayout.addView(
							bindView(index, adapter.getItem(index), view), 1,
							params);
				} else {
					bindView(index, adapter.getItem(index), view);
				}
				mLinearLayout.getChildAt(1).setVisibility(View.VISIBLE);
			} else {
				if (view == null) {
					mLinearLayout.addView(
							bindView(index, adapter.getItem(index-1), view), 1,
							params);
				} else {
					bindView(index, adapter.getItem(index-1), view);
				}
				mLinearLayout.getChildAt(1).setVisibility(View.INVISIBLE);
			}
			view = mLinearLayout.getChildAt(1);
			view.setTag(index);
			view.setOnClickListener(UserView.this);
			
			
			index++;
			view = mLinearLayout.getChildAt(2);
			if (index < getSize()) {
				if (view == null) {
					mLinearLayout.addView(
							bindView(index, adapter.getItem(index), view), 2,
							params);
				} else {
					bindView(index, adapter.getItem(index), view);
				}
				mLinearLayout.getChildAt(2).setVisibility(View.VISIBLE);
			} else {
				if (view == null) {
					mLinearLayout.addView(
							bindView(index, adapter.getItem(index-2), view), 2,
							params);
				} else {
					bindView(index, adapter.getItem(index-2), view);
				}
				mLinearLayout.getChildAt(2).setVisibility(View.INVISIBLE);
			}
			view = mLinearLayout.getChildAt(2);
			view.setTag(index);
			view.setOnClickListener(UserView.this);
			return mLinearLayout;
		}

		@Override
		public View bindView(int position, String t, View view) {
			if (view == null) {
				view = getLayoutInflater().inflate(R.layout.fans_layout, null);
			}
			return view;
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

}
