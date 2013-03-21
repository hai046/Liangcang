package com.liangcang.weigets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.liangcang.R;

/**
 * 
 * @Description: TODO
 * 
 * @File: MenuListAdapter.java
 * 
 * @Paceage com.meiya.ui
 * 
 * @Version
 */
public class MenuListAdapter extends BaseAdapter {

	private Context mContext;

	private LayoutInflater inflater;
	private String[] list;

	public MenuListAdapter(Context mContext) {
		this.mContext = mContext;
		this.inflater = LayoutInflater.from(mContext);
		list = mContext.getResources().getStringArray(R.array.parentsMenu);
	}

	private int menuParentsIndex = 0;

	public int getMenuParentIndex() {
		return menuParentsIndex;
	}

	public boolean isParent() {

		return menuParentsIndex != 0 && menuParentsIndex != 5;
	}

	public void switchToSon(int index) {
		menuParentsIndex = index;
		switch (index) {
		case 0:
			list = mContext.getResources().getStringArray(
					R.array.menu_categorye);

			break;
		case 5:

			list = mContext.getResources()
					.getStringArray(R.array.menu_settings);

			break;

		default:
			list = mContext.getResources().getStringArray(R.array.parentsMenu);
			break;
		}
	}

	public MenuListAdapter() {
	}

	@Override
	public int getCount() {
		return list.length;
	}

	@Override
	public Object getItem(int arg0) {
		return list[arg0];
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		ViewHolder viewHolder;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = inflater.inflate(R.layout.menu_item_list, null);
//			viewHolder.ivSelected = (ImageView) convertView
//					.findViewById(R.id.ivSelected);
			viewHolder.tvMenu = (TextView) convertView
					.findViewById(R.id.tvMenu);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.tvMenu.setText(list[position]);
		convertView.setTag(viewHolder);
		return convertView;
	}

	class ViewHolder {
//		ImageView ivSelected;
		TextView tvMenu;
	}

}
