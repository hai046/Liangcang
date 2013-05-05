package com.liangcang.weigets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.liangcang.R;

public class LoadMoreListView extends ListView {
    // String TAG = "LoadMoreListView";
    private View footerView;

    public LoadMoreListView(Context context, AttributeSet attrs, int defStyle) {
        super( context, attrs, defStyle );
        init( context );
        
    }

    public LoadMoreListView(Context context, AttributeSet attrs) {
        super( context, attrs );
        init( context );
    }

    public LoadMoreListView(Context mContext) {
        super( mContext );
        init( mContext );
    }

    private void init(Context context) {
        footerView = inflate( context, R.layout.loading_more, null );
        addFooterView( footerView );
        setOnScrollListener( mOnScrollListener );
    }

    private void hideFooterView() {
        footerView.setVisibility( View.GONE );
    }

    public void showFooterView() {
        footerView.setVisibility( View.VISIBLE );
    }

    private OnScrollListener mOnScrollListener = new OnScrollListener( ) {

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            // MyLog.e( TAG, "scrollState=" + scrollState );
            // MyLog.e( TAG, "view.getLastVisiblePosition( )=" +
            // view.getLastVisiblePosition( ) );

            // // MyLog.e(TAG, " LoadMoreListView.this.getCount( )="
            // + LoadMoreListView.this.getCount());
            if (enable && scrollState == SCROLL_STATE_IDLE && (view.getLastVisiblePosition( ) != 0 && view.getLastVisiblePosition( ) == LoadMoreListView.this.getCount( ) - 1)) {
                // MyLog.e( TAG, "scrollState=" + scrollState );
                showFooterView( );
                onLoading( );
            }
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        }
    };
    private boolean enable = true;

    public void setMyEnabel(boolean enable) {

        this.enable = enable;
    }

    public void onStopLoading() {
        hideFooterView( );
    }

    public void onLoading() {
        if(mLoadCallBack!=null)
        {
            mLoadCallBack.onLoading( );
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
