package com.liangcang.views;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.liangcang.R;
import com.liangcang.base.MyBaseAdapter;
import com.liangcang.util.RichText;
import com.liangcang.weigets.LoadMoreListView;

public class MsgView extends BaseView {

	private MyBaseAdapter<String> adapter = new MyBaseAdapter<String>() {

		@Override
		public View bindView(int position, String t, View view) {
			if (view == null) {
				view = getLayoutInflater().inflate(R.layout.msg_layout, null);

				TextView tv = (TextView) view.findViewById(R.id.msg_content);
				RichText rt = new RichText(mContext);
				rt.addTextColor("UserName",
						mContext.getResources().getColor(R.color.blue));
				rt.addTextColor("评论的内容是什么这里马上就可以知道了，亲 你说呢  哈哈哈", mContext
						.getResources().getColor(R.color.white));
				tv.setText(rt);
			}
			// view.findViewById(R.id.msg_userShop).setVisibility(
			// position % 2 == 0 ? View.VISIBLE : View.GONE);
			return view;
		}
	};

	public MsgView(Context mContext) {
		super(mContext);
		LoadMoreListView list = new LoadMoreListView(mContext);
		list.setDividerHeight(0);
		setContentView(list);
		for (int i = 0; i < 10; i++) {
			adapter.add("as");
		}
		list.setAdapter(adapter);
	}
}
