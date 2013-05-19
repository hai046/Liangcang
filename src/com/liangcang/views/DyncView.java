package com.liangcang.views;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.liangcang.R;
import com.liangcang.base.MyBasePageAdapter;
import com.liangcang.mode.Good;
import com.liangcang.util.ImageDownloader;
import com.liangcang.util.Util;
import com.liangcang.weigets.LoadMoreListView;
import com.liangcang.weigets.LoadMoreListView.LoadCallBack;

/**
 * 实现动态页面
 * 
 * @author 邓海柱 haizhu12345@gmail.com
 */
public class DyncView extends BaseView {

	MyBasePageAdapter<Good> adapter;

	public DyncView(final Context mContext) {
		super(mContext);
		LoadMoreListView listView = new LoadMoreListView(mContext);
		listView.setDividerHeight(0);

		final LinearLayout.LayoutParams leftParams;
		final LinearLayout.LayoutParams rightParams;
		int width = Util.getDisplayWindth(mContext) / 3;
		// int mar=(int) (widht*0.1);
		// int leftWidth = widht * 3;
		// int rightWidth = widht * 5;
		int pix = mContext.getResources().getDimensionPixelSize(
				R.dimen.margin10);
		leftParams = new LayoutParams(width - pix, width - pix);
		//
		// leftParams.leftMargin=pix;
		// leftParams.rightMargin=2*pix;
		rightParams = new LayoutParams(width * 2 - pix, width * 2 - pix);
		// rightParams.leftMargin = 2*pix;;
		// rightParams.rightMargin=pix;
		adapter = new MyBasePageAdapter<Good>(mContext) {

			@Override
			public String path() {
				return "activity";
			}

			@Override
			public void onReceiveSuccess() {

			}

			@Override
			public void onReceiveFailure(String msg) {

			}

			@Override
			public boolean isDoGet() {
				return true;
			}

			@Override
			public Class<Good> getModeClass() {
				return Good.class;
			}

			@Override
			public View bindView(int position, Good t, View view) {
				ImageView imageUser = null;

				ImageView imgShopping = null;
				if (view == null) {
					view = getLayoutInflater().inflate(R.layout.dync_layout,
							null);
					imageUser = (ImageView) view
							.findViewById(R.id.dync_img_user);

					imgShopping = (ImageView) view
							.findViewById(R.id.dync_img_shopping);

					imageUser.setLayoutParams(leftParams);
					imgShopping.setLayoutParams(rightParams);
				} else {
					imageUser = (ImageView) view
							.findViewById(R.id.dync_img_user);

					imgShopping = (ImageView) view
							.findViewById(R.id.dync_img_shopping);
				}
				TextView tvUserName, tvUserDesc;
				tvUserName = (TextView) view.findViewById(R.id.userName);
				tvUserDesc = (TextView) view.findViewById(R.id.userDesc);

				tvUserName.setText(t.getOwner_name());
				tvUserDesc.setText(t.getOwner_desc());
				ImageDownloader.getInstance().download(t.getOwner_image(),
						imageUser);
				ImageDownloader.getInstance().download(t.getGoods_image(),
						imgShopping);
				imageUser.setTag(position);
				imageUser.setOnClickListener(mClickListener);
				imgShopping.setTag(position);
				imgShopping.setOnClickListener(mClickListener);
				return view;
			}

			private OnClickListener mClickListener = new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (v.getTag() == null)
						return;
					int index = Integer.parseInt(v.getTag().toString());
					Good good = getItem(index);
					switch (v.getId()) {
					case R.id.userName:
					case R.id.dync_img_user:

						Util.gotoUser(mContext, good.getOwner_id(),
								good.getOwner_image(), good.getOwner_name());
						break;
					case R.id.dync_img_shopping:
						Util.gotoItemDetail(mContext, good);
						break;
					default:
						break;
					}

				}
			};
		};

		listView.setOnLoadCallBack(new LoadCallBack() {
			@Override
			public void onLoading() {
				adapter.loadMore();

			}
		});
		listView.setAdapter(adapter);
		setContentView(listView);
		adapter.onRefresh();
		// hadLoadData = false;
	}

	// public void ifLoadMoreNotData() {
	// if (hadLoadData == false) {
	// onRefresh();
	// }
	//
	// }

	// private boolean hadLoadData = false;

	@Override
	public void onRefresh() {
		// getData(0);
		// hadLoadData = true;
		adapter.onRefresh();
		super.onRefresh();
	}

}
