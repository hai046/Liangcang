package com.liangcang.weigets;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.liangcang.R;
import com.liangcang.util.MyLog;
import com.liangcang.util.Util;

public class LoadMoreListView extends ListView {
	// String TAG = "LoadMoreListView";
	private View footerView;

	public LoadMoreListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);

	}

	public LoadMoreListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public LoadMoreListView(Context mContext) {
		super(mContext);
		init(mContext);
	}

	private void init(Context context) {
		offsetItem = 0;
		setBackgroundColor(Color.BLACK);
		footerView = inflate(context, R.layout.loading_more, null);
		footerView.setMinimumWidth(Util.getDisplayWindth(context));
		addFooterView(footerView);
		setOnScrollListener(mOnScrollListener);
		setDividerHeight(0);
		setFadingEdgeLength(0);
		setCacheColorHint(getResources().getColor(R.color.transparent));
	}

	private void hideFooterView() {
		footerView.setVisibility(View.GONE);
	}

	public void onShowLoadMore() {
		footerView.setVisibility(View.VISIBLE);
	}

	private OnScrollListener mOnScrollListener = new OnScrollListener() {

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			// MyLog.e( TAG, "scrollState=" + scrollState );
			// MyLog.e( TAG, "view.getLastVisiblePosition( )=" +
			// view.getLastVisiblePosition( ) );

			MyLog.e("loadMoreListView",
					" LoadMoreListView.this.getCount( )="
							+ LoadMoreListView.this.getCount()
							+ "   view.getLastVisiblePosition()="
							+ view.getLastVisiblePosition());
			if (scrollState == SCROLL_STATE_IDLE
					&& (view.getLastVisiblePosition() != 0 && view
							.getLastVisiblePosition() == LoadMoreListView.this
							.getCount() - 1)) {
				// MyLog.e( TAG, "scrollState=" + scrollState );
				onShowLoadMore();
				onLoading();
			}
		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {

		}
	};
	private int offsetItem = 0;

	public void addHeaderView(View v) {
		super.addHeaderView(v);
		offsetItem++;
	}

	@Override
	public void addFooterView(View v) {
		super.addFooterView(v);
		offsetItem++;
	}

	public void onStopLoading() {
		hideFooterView();

	}

	public void onLoading() {
		if (mLoadCallBack != null) {
			mLoadCallBack.onLoading();
		}
	}

	private LoadCallBack mLoadCallBack;

	public void setOnLoadCallBack(LoadCallBack mLoadCallBack) {
		this.mLoadCallBack = mLoadCallBack;
	}

	public interface LoadCallBack {
		void onLoading();
	}

}
