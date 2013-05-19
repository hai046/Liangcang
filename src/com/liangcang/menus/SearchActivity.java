package com.liangcang.menus;

import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.liangcang.R;
import com.liangcang.base.BaseActivity;
import com.liangcang.base.BaseThrAdapter;
import com.liangcang.base.BaseThrAdapter.Type;
import com.liangcang.base.IActivity;
import com.liangcang.base.TwoBaseAdapter;
import com.liangcang.managers.DataCallBack;
import com.liangcang.mode.Good;
import com.liangcang.util.ImageDownloader;
import com.liangcang.util.MyToast;
import com.liangcang.util.Util;
import com.liangcang.weigets.LoadMoreListView;

public class SearchActivity extends BaseActivity {
	private TwoBaseAdapter<Good>liangPinAdapter;
	private BaseThrAdapter userAdapter;
	private EditText etsearch_Context;
	private LoadMoreListView listView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_layout);
		findViewById(R.id.search_LiangPin).setOnClickListener(mClickListener);
		findViewById(R.id.search_User).setOnClickListener(mClickListener);
		etsearch_Context=(EditText) findViewById(R.id.search_Context);
		listView=(LoadMoreListView) findViewById(R.id.search_loadMoreListView);
		initAdapter();
	}
	
	private void initAdapter() {
		
		liangPinAdapter=new TwoBaseAdapter<Good>(this) {
			private DataCallBack<String> callBack=new DataCallBack<String>() {
				@Override
				public void success(String data) {
					List<Good>list=JSON.parseArray(data, Good.class);
					addAll(list	);
				}
				@Override
				public void failure(String msg) {
					MyToast.showMsgShort(SearchActivity.this, msg);
					
				}
			};
			@Override
			public String getPath() {
				
				return "search";
			}
			
			@Override
			public DataCallBack<String> getDataCallBack() {
				
				return callBack;
			}
			
			@Override
			public void bindViewAndListener(int position ,Good item, ImageView img,
					ImageButton btnHeart, ImageButton btnShare) {
				ImageDownloader.getInstance().download(item.getGoods_image(), img);
				img.setTag(position);
				btnHeart.setTag(position);
				btnShare.setTag(position);
				img.setOnClickListener(mClickListener);
				btnHeart.setOnClickListener(mClickListener);
				btnShare.setOnClickListener(mClickListener);

			}

			private OnClickListener mClickListener = new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (v.getTag() == null)
						return;
					int position = Integer.parseInt(v.getTag().toString());
					Good good = getItem(position);
					switch (v.getId()) {
					case R.id.category_grid_img:
						Util.gotoItemDetail(SearchActivity.this, good);
						break;

					default:
						break;
					}
				}
			};
		};
		userAdapter=new BaseThrAdapter(this);
		userAdapter.setType(Type.Search);
		
	}

	private OnClickListener mClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.search_LiangPin:
				liangPinAdapter.addParams("keywords", etsearch_Context.getText().toString());
				liangPinAdapter.onRefresh();
				listView.setAdapter(liangPinAdapter);
				break;
			case R.id.search_User:
				userAdapter.addParams("keywords", etsearch_Context.getText().toString());
				userAdapter.onRefresh();
				listView.setAdapter(userAdapter);
				break;
			default:
				break;
			}

		}
	};
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
