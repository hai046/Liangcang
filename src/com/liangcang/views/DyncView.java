package com.liangcang.views;

import com.liangcang.R;
import com.liangcang.base.MyBaseAdapter;
import com.liangcang.util.iImageDownloader;
import com.liangcang.util.ImageDownloader;
import com.liangcang.weigets.LoadMoreListView;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

/**
 * 实现动态页面
 * @author 邓海柱
 * haizhu12345@gmail.com
 */
public class DyncView extends BaseView {
//	iImageDownloader mDownloader = new iImageDownloader();
	private MyBaseAdapter<String> adapter = new MyBaseAdapter<String>() {

		@Override
		public View bindView(int position, String t, View view) {
			if (view == null) {
				view = getLayoutInflater().inflate(R.layout.dync_layout, null);

			}
			ImageView imgShopping = (ImageView) view
					.findViewById(R.id.dync_img_shopping);
			ImageDownloader.getInstance().download(t, imgShopping);
			return view;
		}
	};

	public DyncView(Context mContext) {
		super(mContext);
		LoadMoreListView listView = new LoadMoreListView(mContext);
		listView.setDividerHeight(0);
		adapter.add("http://www.iliangcang.com/ware/goods/mid/2/9/9068.jpg");
		adapter.add("http://www.iliangcang.com/ware/goods/mid/2/9/9101.jpg");
		adapter.add("http://www.iliangcang.com/ware/goods/mid/2/8/8974.jpg");
		adapter.add("http://www.iliangcang.com/ware/goods/mid/2/8/8828.jpg");
		adapter.add("http://www.iliangcang.com/ware/goods/mid/2/9/9081.jpg");
		adapter.add("http://www.iliangcang.com/ware/goods/mid/2/8/8933.jpg");
		adapter.add("http://www.iliangcang.com/ware/goods/mid/2/9/9112.jpg");
		adapter.add("http://www.iliangcang.com/ware/goods/mid/2/6/6373.jpg");
		adapter.add("http://www.iliangcang.com/ware/goods/mid/2/9/9188.jpg");
		adapter.add("http://www.iliangcang.com/ware/goods/mid/2/8/8958.jpg");
		adapter.add("http://www.iliangcang.com/ware/goods/mid/2/9/9053.jpg");
		adapter.add("http://www.iliangcang.com/ware/goods/mid/2/9/9190.jpg");
		adapter.add("http://www.iliangcang.com/ware/goods/mid/2/8/8958.jpg");
		listView.setAdapter(adapter);
		setContentView(listView);
	}
}
