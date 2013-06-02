/**
 * @file XListView.java
 * @package me.maxwin.view
 * @create Mar 18, 2012 6:28:41 PM
 * @author Maxwin
 * @description An ListView support (a) Pull down to refresh, (b) Pull up to load more.
 * 		Implement IXListViewListener, and see stopRefresh() / stopLoadMore().
 */
package com.liangcang.weigets;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.liangcang.R;
import com.liangcang.util.MyLog;

public class RefreshListView extends LoadMoreListView implements OnScrollListener {

    private float mLastY = -1; // save event y
    private Scroller mScroller; // used for scroll back
    private OnScrollListener mScrollListener; // user's scroll listener

    // the interface to trigger refresh and load more.
    private RefreshListener mListViewListener;

    // -- header view
    private RefreshListViewHeader mHeaderView;
    // header view content, use it to calculate the Header's height. And hide it
    // when disable pull refresh.
    private RelativeLayout mHeaderViewContent;
    private TextView mHeaderTimeView;
    private int mHeaderViewHeight; // header view's height
    private boolean mEnablePullRefresh = true;
    private boolean mPullRefreshing = true; // is refreashing.


    // total list items, used to detect is at the bottom of listview.
//    private int mTotalItemCount;

    // for mScroller, scroll back from header or footer.
    private int mScrollBack;
    private final static int SCROLLBACK_HEADER = 0;
//    private final static int SCROLLBACK_FOOTER = 1;

    private final static int SCROLL_DURATION = 400; // scroll back duration
//    private final static int PULL_LOAD_MORE_DELTA = 50; // when pull up >= 50px
                                                        // at bottom, trigger
                                                        // load more.
    private final static float OFFSET_RADIO = 1.0f; // support iOS like pull
                                                    // feature.
    private Context context;

    /**
     * @param context
     */
    public RefreshListView(Context context) {
        super( context );
        initWithContext( context );
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        super( context, attrs );
        initWithContext( context );
    }

    public RefreshListView(Context context, AttributeSet attrs, int defStyle) {
        super( context, attrs, defStyle );
        initWithContext( context );
    }

    private void initWithContext(Context context) {
        mScroller = new Scroller( context, new DecelerateInterpolator( ) );
        // XListView need the scroll event, and it will dispatch the event to
        // user's listener (as a proxy).
        setFastScrollEnabled( true );
        super.setOnScrollListener( this );
        setFadingEdgeLength( 0 );
        this.context = context;
        // init header view
        mHeaderView = new RefreshListViewHeader( context );
        mHeaderViewContent = (RelativeLayout) mHeaderView.findViewById( R.id.xlistview_header_content );
        mHeaderTimeView = (TextView) mHeaderView.findViewById( R.id.xlistview_header_time );
        addHeaderView( mHeaderView );
        // init footer view

        // init header height
        mHeaderView.getViewTreeObserver( ).addOnGlobalLayoutListener( new OnGlobalLayoutListener( ) {
            @Override
            public void onGlobalLayout() {
                mHeaderViewHeight = mHeaderViewContent.getHeight( );
                getViewTreeObserver( ).removeGlobalOnLayoutListener( this );
            }
        } );

    }


    /**
     * enable or disable pull down refresh feature.
     * 
     * @param enable
     */
    public void setPullRefreshEnable(boolean enable) {
        mEnablePullRefresh = enable;
        if (!mEnablePullRefresh) { // disable, hide the content
            mHeaderViewContent.setVisibility( View.INVISIBLE );
        } 
    }
    @Override
    public void onStopLoading() {
    	super.onStopLoading();
    	stopRefresh();
    }
    /**
     * stop refresh, reset header view.
     */
    public void stopRefresh() {
    	MyLog.e("refreshListView", "stopRefresh mPullRefreshing="+mPullRefreshing);
        setRefreshTime( time );
        if (mPullRefreshing == true) {
            mPullRefreshing = false;
            resetHeaderHeight( );
        }
    }


    /**
     * set last refresh time
     * 
     * @param time
     */
    public void setRefreshTime(String time) {
        mHeaderTimeView.setText( time == null ? null : context.getString( R.string.lastUpdataTime ) + time );
    }

    private void invokeOnScrolling() {
        if (mScrollListener instanceof OnXScrollListener) {
            OnXScrollListener l = (OnXScrollListener) mScrollListener;
            l.onXScrolling( this );
        }
    }

    private void updateHeaderHeight(float delta) {
        mHeaderView.setVisiableHeight( (int) delta + mHeaderView.getVisiableHeight( ) );
        if (mEnablePullRefresh && !mPullRefreshing) {
            if (mHeaderView.getVisiableHeight( ) > mHeaderViewHeight) {
                mHeaderView.setState( RefreshListViewHeader.STATE_READY );
            } else {
                mHeaderView.setState( RefreshListViewHeader.STATE_NORMAL );
            }
        }
        setSelection( 0 );
    }

    /**
     * reset header view's height.
     */
    private void resetHeaderHeight() {
        int height = mHeaderView.getVisiableHeight( );
        if (height == 0) // not visible.
            return;
        // refreshing and header isn't shown fully. do nothing.
        if (mPullRefreshing && height <= mHeaderViewHeight) {
            return;
        }
        int finalHeight = 0; // default: scroll back to dismiss header.
        // is refreshing, just scroll back to show all the header.
        if (mPullRefreshing && height > mHeaderViewHeight) {
            finalHeight = mHeaderViewHeight;
        }
        mScrollBack = SCROLLBACK_HEADER;
        mScroller.startScroll( 0, height, 0, finalHeight - height, SCROLL_DURATION );
        // trigger computeScroll
        invalidate( );
    }

//    private void updateFooterHeight(float delta) {
//
//        setSelection( mTotalItemCount - 1 ); // scroll to bottom
//    }
//
//    private void resetFooterHeight() {
//      
//    }

    private String time;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mLastY == -1) {
            mLastY = ev.getRawY( );
        }
        switch (ev.getAction( )) {
        case MotionEvent.ACTION_DOWN:
            mLastY = ev.getRawY( );
            break;
        case MotionEvent.ACTION_MOVE:
            final float deltaY = ev.getRawY( ) - mLastY;
            mLastY = ev.getRawY( );
            if (getFirstVisiblePosition( ) == 0 && (mHeaderView.getVisiableHeight( ) > 0 || deltaY > 0)) {
                // the first item is showing, header has shown or pull down.
                updateHeaderHeight( deltaY / OFFSET_RADIO );
                invokeOnScrolling( );
            }  
            break;
        default:
            mLastY = -1; // reset
            if (getFirstVisiblePosition( ) == 0) {
                // invoke refresh
                if (mEnablePullRefresh && mHeaderView.getVisiableHeight( ) > mHeaderViewHeight) {
                    mPullRefreshing = true;
                    mHeaderView.setState( RefreshListViewHeader.STATE_REFRESHING );
                    if (mListViewListener != null) {

                        mListViewListener.onRefresh( );
                        time = sdf.format( new Date( ) );
                    }

                }
                resetHeaderHeight( );
            }  
            break;
        }
        return super.onTouchEvent( ev );
    }

    private SimpleDateFormat sdf = new SimpleDateFormat( "yyyy/MM/dd HH:ss" );

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset( )) {
            if (mScrollBack == SCROLLBACK_HEADER) {
                mHeaderView.setVisiableHeight( mScroller.getCurrY( ) );
            } 
            postInvalidate( );
            invokeOnScrolling( );
        }
        super.computeScroll( );
    }

    @Override
    public void setOnScrollListener(OnScrollListener l) {
        mScrollListener = l;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (mScrollListener != null) {
            mScrollListener.onScrollStateChanged( view, scrollState );
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        // send to user's listener
//        mTotalItemCount = totalItemCount;
        if (mScrollListener != null) {
            mScrollListener.onScroll( view, firstVisibleItem, visibleItemCount, totalItemCount );
        }
    }

    public void setOnRefreshListener(RefreshListener l) {
        mListViewListener = l;
    }

    /**
     * you can listen ListView.OnScrollListener or this one. it will invoke onXScrolling when header/footer scroll back.
     */
    public interface OnXScrollListener extends OnScrollListener {
        public void onXScrolling(View view);
    }

    /**
     * implements this interface to get refresh/load more event.
     */
    public interface RefreshListener {
        public void onRefresh();

    }
}
