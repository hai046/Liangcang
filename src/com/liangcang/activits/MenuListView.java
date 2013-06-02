package com.liangcang.activits;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.liangcang.R;
import com.liangcang.base.MyBaseAdapter;
import com.liangcang.menus.SearchActivity;

public abstract class MenuListView extends ListView {
	private Context mContext;

	public MenuListView(final Context mContext) {
		super(mContext);
		setFadingEdgeLength(0);
		this.mContext = mContext;
		setAdapter(adapter);
		setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Item item = adapter.getItem(arg2);
				if (arg2 == 0) {
					Intent intent = new Intent();
					intent.setClass(mContext, SearchActivity.class);
					mContext.startActivity(intent);
					return;
				}
				if (item.isCanClick) {
					onSelect(item.showText, item.value, item.flag);
				}
			}
		});
	}

	/**
	 * 
	 * @param showText
	 * @param value
	 * @param flag
	 *            1代表价格 eg:orderby=price&min=1001&max=3000,<br>
	 *            2代表分类eg:goods/class?type=1
	 */
	public abstract void onSelect(String showText, String value, int flag);

	private MyBaseAdapter<Item> adapter = new MyBaseAdapter<Item>() {

		@Override
		public View bindView(int position, Item t, View view) {
			if (view == null) {
				view = LayoutInflater.from(mContext).inflate(
						R.layout.menu_new_item, null);
			}

			TextView name = (TextView) view.findViewById(R.id.menuText);
			if (t.isBold) {
				name.setTextSize(20.0f);
				name.setTypeface(Typeface.DEFAULT_BOLD, Typeface.BOLD);
			} else {
				name.setTypeface(Typeface.DEFAULT);
				name.setTextSize(16.0f);
			}

			name.setText(t.showText);
			View rightArr = view.findViewById(R.id.menuArrow);
			if (t.isCanClick) {
				rightArr.setVisibility(View.VISIBLE);
			} else {
				rightArr.setVisibility(View.GONE);
			}
			// if (position == 0) {
			// view.setBackgroundResource(R.drawable.header_search);
			// } else {
			// view.setBackgroundColor(Color.BLACK);
			// }
			return view;
		}
	};

	public void switchCategorysMenus() {
		List<Item> categorys = new ArrayList<MenuListView.Item>();
		categorys.clear();
		String[] cagegoryTypes = mContext.getResources().getStringArray(
				R.array.menu_categorye_types);
		String[] cagegorys = mContext.getResources().getStringArray(
				R.array.menu_categorye);
		categorys.add(new Item("搜索", "search", true, true, 0));
		categorys.add(new Item("精选", "recommendation", true, true, 0));
		categorys.add(new Item("分类", null, false, true, 0));
		for (int i = 0; i < cagegorys.length; i++) {
			categorys.add(new Item(cagegorys[i], "goods/class?type="
					+ cagegoryTypes[i], true, false, 2));
		}
		categorys.add(new Item("价格", null, false, true, 1));
		categorys.add(new Item("0-200", "orderby=price&min=0&max=200", true,
				false, 1));
		categorys.add(new Item("201-500", "orderby=price&min=201&max=500",
				true, false, 1));
		categorys.add(new Item("501-1000", "orderby=price&min=501&max=1000",
				true, false, 1));
		categorys.add(new Item("1001-3000", "orderby=price&min=1001&max=3000",
				true, false, 1));
		categorys.add(new Item("3000以上", "orderby=price&min=3000", true, false,
				1));
		categorys.add(new Item("❤喜欢", "like/list", true, true, 0));
		adapter.addAll(categorys);
		adapter.notifyDataSetChanged();
	}

	public void switchDarenMenus() {
		List<Item> categorys = new ArrayList<MenuListView.Item>();
		categorys.add(new Item("搜索", "search", true, true, 0));
		categorys.add(new Item("最多推荐", "expert?orderby=goods_sum", true, true,
				0));
		categorys.add(new Item("最受欢迎", "expert?orderby=followers", true, true,
				0));
		categorys.add(new Item("最新推荐", "expert?orderby=action_time", true,
				true, 0));
		categorys
				.add(new Item("最新加入", "expert?orderby=reg_time", true, true, 0));
		adapter.addAll(categorys);
		adapter.notifyDataSetChanged();

	}

	class Item {

		public Item(String showText, String value, boolean isCanClick,
				boolean isBold, int flag) {
			this.showText = showText;
			this.value = value;
			this.isCanClick = isCanClick;
			this.isBold = isBold;
			this.flag = flag;
		}

		public int flag;
		public boolean isBold;
		public String showText;
		public String value;
		public boolean isCanClick;
	}

}
