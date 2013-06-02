package com.liangcang;

import java.util.ArrayList;
import java.util.List;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.liangcang.base.BaseActivity;
import com.liangcang.managers.ColorManager;
import com.liangcang.managers.DataCallBack;
import com.liangcang.managers.DataManager;
import com.liangcang.mode.Good;
import com.liangcang.mode.GoodDetail;
import com.liangcang.util.ImageDownloader;
import com.liangcang.util.MyHandler;
import com.liangcang.util.MyHandler.HandlerCallBack;
import com.liangcang.util.MyLog;
import com.liangcang.util.RichText;
import com.liangcang.util.Util;
import com.liangcang.weigets.MyGallery;

public class ItemDetailActivity extends BaseActivity implements OnClickListener {

	public static Good mGood;
	private Button btnLike, btnMsgNum;
	private TextView tvLiked_count, tv_goodPrice, tvOrderName, tvOrderDesc,
			tvGoodName, tvGoodsDesc;
	private ImageButton btnShareTo, btnBuy;
	private ImageView goodOrderImage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		if (mGood == null) {
			finish();
			return;
		}

		super.onCreate(savedInstanceState);
		setContentView(R.layout.item_detail_layout);

		initView();
		receviceData();
		setTopBtnLeftBackground(R.drawable.navigation_back);
		hideRightBtn2();
		setRightTitleString(null);
	}

	private void receviceData() {
		DataManager.getInstance(this).getGoodsDetail(mGood.getGoods_id(),
				new DataCallBack<GoodDetail>() {
					@Override
					public void success(GoodDetail t) {
						initData(t);
					}

					@Override
					public void failure(String msg) {

					}
				});

	}

	@Override
	public void onClick(View v) {

		super.onClick(v);
		switch (v.getId()) {
		case R.id.item_detail_buy:
			Util.gotoBuy(this, buyUrl);
			break;
		case R.id.item_detail_shareTo:

			break;
		case R.id.item_detail_love:

			break;
		case R.id.item_detail_msgNum:
			Util.gotoGoodComment(this, mGood);
			break;
		default:
			break;
		}
	}

	private int templete_id = 1;

	private void initView() {
		tvLiked_count = (TextView) findViewById(R.id.item_detail_likedCount);

		this.tv_goodPrice = (TextView) findViewById(R.id.item_detail_price);
		this.btnBuy = (ImageButton) findViewById(R.id.item_detail_buy);
		btnBuy.setOnClickListener(this);
		this.btnMsgNum = (Button) findViewById(R.id.item_detail_msgNum);
		btnMsgNum.setOnClickListener(this);
		this.btnShareTo = (ImageButton) findViewById(R.id.item_detail_shareTo);
		btnShareTo.setOnClickListener(this);
		goodOrderImage = (ImageView) findViewById(R.id.item_detail_userImage);
		btnLike = (Button) findViewById(R.id.item_detail_love);
		btnLike.setOnClickListener(this);
		this.mGallery = (MyGallery) findViewById(R.id.item_detail_gallery);

		this.tvProgress = (TextView) findViewById(R.id.item_detail_tvprogress);
		this.tvOrderName = (TextView) findViewById(R.id.item_detail_userName);
		tvOrderDesc = (TextView) findViewById(R.id.item_detail_Userdesc);

		tvGoodName = (TextView) findViewById(R.id.item_detail_goodsName);
		tvGoodsDesc = (TextView) findViewById(R.id.item_detail_goodsDesc);

		tvGoodName.setTextColor(ColorManager.getInsance().getDefaltColor(
				templete_id));
		tvLiked_count.setTextColor(ColorManager.getInsance().getDefaltColor(
				templete_id));
		initData();

	}

	private void initData() {

		tvLiked_count.setText(mGood.getLike_count());
		tv_goodPrice.setText("￥" + mGood.getPrice());
		btnMsgNum.setText(mGood.getComment_count());
		list.add(mGood.getGoods_image());
		setDatas(list);
	}

	private String buyUrl;

	protected void initData(GoodDetail t) {
		buyUrl = t.getGoods_url();
		isGalleryClear = true;
		if (t.getImages_item() != null && t.getImages_item().size() > 0) {
			setDatas(t.getImages_item());
		}
		tvLiked_count.setText(t.getLike_count());
		tv_goodPrice.setText("￥" + t.getPrice());
		btnMsgNum.setText(t.getComment_count());
		ImageDownloader.getInstance().download(t.getOwner_image(),
				goodOrderImage);
		this.tvGoodName.setText(t.getGoods_name());
		this.tvGoodsDesc.setText(t.getGoods_desc());
		this.tvLiked_count.setText(t.getLike_count());
		this.tvOrderName.setText(t.getOwner_name());
		this.tvOrderDesc.setText(t.getOwner_desc());

		// list.clear();
		// list.add(mGood.getGoods_image());
		// setDatas(list);
	}

	@Override
	public void onResume() {

		if (list.size() > 0) {
			MyHandler.getInstance().removeMessages(SCROLLER);
			MyHandler.getInstance().sendEmptyMessageDelayed(SCROLLER, 4000);
		}
		/*
		 * final int width = Util.getDisplayWindth(ItemDetailActivity.this);
		 * mGallery.setLayoutParams(new RelativeLayout.LayoutParams(width,
		 * width));
		 */
		super.onResume();
	}

	@Override
	public void onPause() {
		MyHandler.getInstance().removeMessages(SCROLLER);
		super.onPause();
	}

	private TextView tvProgress;
	private MyGallery mGallery;
	private BaseAdapter galleryAdapter;
	private boolean isGalleryClear = false;
	private List<String> list = new ArrayList<String>();

	public void setDatas(List<String> list) {
		if (list != null) {

			this.list.addAll(list);
			final int width = Util.getDisplayWindth(ItemDetailActivity.this);
			// mGallery.setLayoutParams(new RelativeLayout.LayoutParams(width,
			// width));

			if (galleryAdapter == null) {
				galleryAdapter = new BaseAdapter() {

					private Gallery.LayoutParams params = new Gallery.LayoutParams(
							width, width);

					public View bindView(int position, String t, View view) {
						ImageView image;
						if (view == null) {
							image = new ImageView(ItemDetailActivity.this);
							image.setScaleType(ImageView.ScaleType.FIT_XY);
							image.setLayoutParams(params);
							MyLog.e("gallery", "width=" + width);
						} else {
							image = (ImageView) view;
						}
						ImageDownloader.getInstance().download(t, image);
						// MyLog.e( TAG, "Gallery getView" + position );
						return image;
					}

					@Override
					public View getView(int position, View convertView,
							ViewGroup parent) {
						if (ItemDetailActivity.this.list.size() > 0) {
							position = position
									% ItemDetailActivity.this.list.size();
							return bindView(position,
									ItemDetailActivity.this.list.get(position),
									convertView);
						} else {
							return null;
						}

					}

					public int getCount() {
						// TODO　　内网测试去掉循环
						return ItemDetailActivity.this.list.size();
					}

					@Override
					public Object getItem(int position) {
						return null;
					}

					@Override
					public long getItemId(int position) {
						return position;
					}
				};
				mGallery.setAdapter(galleryAdapter);
			} else {
				galleryAdapter.notifyDataSetChanged();
			}
			mGallery.setFadingEdgeLength(0);
			mGallery.setSelection(0/* this.list.size( ) * 500 */);
			onPageSelected(0);
			MyHandler.getInstance().removeMessages(SCROLLER);
			MyHandler.getInstance().sendEmptyMessageDelayed(SCROLLER, 4000);
		} else {
			return;
		}

	}

	private static final int SCROLLER = 1215;

	private HandlerCallBack mHandlerCallBack = new HandlerCallBack() {

		@Override
		public void handleMessage(Message msg) {

			switch (msg.what) {

			case SCROLLER:
				int size = list.size();
				if (size > 0) {
					int current = mGallery.getSelectedItemPosition() + 1;
					onPageSelected((current) % size);
					mGallery.setSelection((current) % size);
				}
				break;
			default:
				break;
			}

		}
	};

	@Override
	public void onDestroy() {
		mGood = null;
		MyHandler.getInstance().removeMessages(SCROLLER);
		MyHandler.getInstance().removeListener(mHandlerCallBack);
		super.onDestroy();
	}

	RichText rt = new RichText(this);

	public void onPageSelected(int position) {
		rt.clear();
		if (list.size() == 0)
			return;
		position = position % list.size();
		for (int i = 0; i < list.size(); i++) {
			rt.addText(" ");
			if (position % list.size() == i) {
				// rt.addText( " . ", Color.WHITE, Typeface.BOLD );
				rt.addImage(BitmapFactory.decodeResource(getResources(),
						R.drawable.point2), 14, 14);

			} else {
				// rt.addText( " . ", Color.GRAY, Typeface.BOLD );
				rt.addImage(BitmapFactory.decodeResource(getResources(),
						R.drawable.point1), 14, 14);
			}
			rt.addText(" ");
		}

		this.tvProgress.setText(rt);
		MyHandler.getInstance().removeMessages(SCROLLER);
		MyHandler.getInstance().sendEmptyMessageDelayed(SCROLLER, 4000);
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub

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

}
