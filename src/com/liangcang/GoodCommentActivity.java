package com.liangcang;

import android.os.Bundle;

import com.liangcang.base.BaseActivity;
import com.liangcang.views.GoodCommentView;

public class GoodCommentActivity extends BaseActivity {
	public static final String GOODS_ID = "goods_id";
	public static final String GOODS_IMAGE = "GOODS_IMAGE";
	public static final String COMMENT_COUNT = "comment_count";

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		GoodCommentView mCommentView = new GoodCommentView(this, getIntent()
				.getStringExtra(GOODS_ID), getIntent().getStringExtra(
				GOODS_IMAGE));
		setContentView(mCommentView.getView());

		String comment_count = getIntent().getStringExtra(COMMENT_COUNT);

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
