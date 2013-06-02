package com.liangcang.menus;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.liangcang.util.MyLog;

/***
 * 行程详情的自定义布局
 * 
 * @author zhangjia
 * 
 */
public class MyLinearLayout extends LinearLayout {
	private GestureDetector mGestureDetector;
	View.OnTouchListener mGestureListener;

	private boolean isLock = true;

	private OnScrollListener onScrollListener;// 自定义接口

	private boolean b;

	public MyLinearLayout(Context context) {
		super(context);
	}

	public void setOnScrollListener(OnScrollListener onScrollListener) {
		this.onScrollListener = onScrollListener;
	}

	public MyLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		mGestureDetector = new GestureDetector(new MySimpleGesture());
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		Log.e("jj", "dispatchTouchEvent...");
		// 获取手势返回值
		b = mGestureDetector.onTouchEvent(ev);
		// 松开手要执行一些操作。(关闭 or 打开)
		if (ev.getAction() == MotionEvent.ACTION_UP) {
			onScrollListener.doLoosen();
		}
		return super.dispatchTouchEvent(ev);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		Log.e("jj", "onInterceptTouchEvent...");
		super.onInterceptTouchEvent(ev);
		return b;
	}
    /***
     * 在这里我简单说明一下
     */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Log.e("jj", "onTouchEvent...");
		isLock = false;
		return super.onTouchEvent(event);
	}

	/***
	 * 自定义手势执行
	 * 
	 * @author zhangjia
	 * 
	 */
	class MySimpleGesture extends SimpleOnGestureListener {

		@Override
		public boolean onDown(MotionEvent e) {
			Log.e("jj", "onDown...");
			isLock = true;
			return super.onDown(e);
		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {

			if (!isLock)
				onScrollListener.doScroll(distanceX);

			// 垂直大于水平
			if (Math.abs(distanceY) > Math.abs(distanceX)) {
				 MyLog.e("jjj", "ll...垂直...");
				return false;
			} else {
				 MyLog.e("jjj", "ll...水平...");
				// Log.e("jj", "distanceX===" + distanceX);
				return true;
			}

		}
	}

	/***
	 * 自定义接口 实现滑动...
	 * 
	 * @author zhangjia
	 * 
	 */
	public interface OnScrollListener {
		void doScroll(float distanceX);// 滑动...
		void doLoosen();// 手指松开后执行...
	}

}
