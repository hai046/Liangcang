package com.liangcang.activits;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.liangcang.R;
import com.liangcang.base.BaseActivity;
import com.liangcang.managers.ColorManager;
import com.liangcang.util.MyToast;
import com.liangcang.util.Util;

public class ColorModeActivity extends BaseActivity {
	private LinearLayout.LayoutParams params;
	private LinearLayout contentLayout;
	private int widthPx;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		contentLayout = new LinearLayout(this);
		contentLayout.setOrientation(LinearLayout.VERTICAL);
		setLeftImage(R.drawable.navigation_back);
		int padding = getResources().getDimensionPixelSize(R.dimen.margin5);
		widthPx = (Util.getDisplayWindth(this) - 10 * padding) / 5;
		params = new LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
				widthPx);
		params.weight = 1;
		params.topMargin = padding;
		params.bottomMargin = padding;
		params.leftMargin = padding;
		params.rightMargin = padding;
		initColors();
		setContentView(contentLayout);
	}

	private void initColors() {

		addColor(1, Color.rgb(166, 222, 223), Color.rgb(137, 197, 224),
				Color.rgb(133, 173, 218), Color.rgb(104, 149, 186),
				Color.rgb(81, 118, 159));
		addColor(2, Color.rgb(128, 222, 98), Color.rgb(128, 201, 74),
				Color.rgb(20, 204, 77), Color.rgb(26, 152, 72),
				Color.rgb(17, 129, 73));
		addColor(3, Color.rgb(255, 192, 171), Color.rgb(237, 143, 122),
				Color.rgb(247, 127, 119), Color.rgb(255, 102, 94),
				Color.rgb(255, 76, 78));
		addColor(4, Color.rgb(253, 234, 79), Color.rgb(255, 216, 80),
				Color.rgb(249, 202, 70), Color.rgb(249, 190, 47),
				Color.rgb(213, 130, 82));
		addColor(5, Color.rgb(204, 204, 204), Color.rgb(166, 166, 166),
				Color.rgb(128, 128, 128), Color.rgb(102, 102, 102),
				Color.rgb(77, 77, 77));

	}
	
	private void addColor(int id, int rgb, int rgb2, int rgb3, int rgb4,
			int rgb5) {
		LinearLayout rowLinear = new LinearLayout(this);
		rowLinear.setOrientation(LinearLayout.HORIZONTAL);
		rowLinear.addView(getColorView(rgb), params);
		rowLinear.addView(getColorView(rgb2), params);
		rowLinear.addView(getColorView(rgb3), params);
		rowLinear.addView(getColorView(rgb4), params);
		rowLinear.addView(getColorView(rgb5), params);
		rowLinear.setId(id);
		rowLinear.setOnClickListener(mOnClickListener);
		contentLayout.addView(rowLinear);
	}

	private OnClickListener mOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			int colorModeIndex = v.getId();
			ColorManager.getInsance().setTemplate_id(colorModeIndex);
			MyToast.showMsgLong(ColorModeActivity.this, "选择了模板"
					+ colorModeIndex);

		}
	};

	private View getColorView(int color) {
		View view = new View(this);
		view.setBackgroundColor(color);
		return view;
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
		finish();

	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub

	}

}
