package com.liangcang.menus;

import android.os.Bundle;

import com.liangcang.base.IActivity;
import com.liangcang.views.GridPicsItemView;

public class FirstTwoColumnPicActivity extends IActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(new GridPicsItemView(this).getView());

	}
}
