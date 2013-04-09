package com.liangcang;

import android.os.Bundle;
import android.view.View;

import com.liangcang.base.BaseActivity;
import com.liangcang.weigets.LoadMoreListView;

public class RecommendActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View topView=getLayoutInflater().inflate(R.layout.comment_layout, null);
		LoadMoreListView moreListView=new LoadMoreListView(this);
		moreListView.addHeaderView(topView);
		setContentView(moreListView);
		
		
	}

	@Override
	public String getNavigationLeftText() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isShowRightClose() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onClickRightButton() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClickLeftButton() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		
	}

}
