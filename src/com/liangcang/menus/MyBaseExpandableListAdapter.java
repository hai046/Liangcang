package com.liangcang.menus;

import java.util.ArrayList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.liangcang.R;

public abstract class MyBaseExpandableListAdapter extends
		BaseExpandableListAdapter {

	private ArrayList<String> parentsList = new ArrayList<String>();
	private ArrayList<String> categoryList = new ArrayList<String>();

	public MyBaseExpandableListAdapter() {
		parentsList.add("分类");
		parentsList.add("达人");
		parentsList.add("店铺");
		parentsList.add("折扣");
		parentsList.add("消息");

		categoryList.add("男士");
		categoryList.add("女士");
		categoryList.add("数码");
		categoryList.add("工具");
		categoryList.add("玩具");
		categoryList.add("美容");
		categoryList.add("美容");
		categoryList.add("美容");
	}

	@Override
	public Object getChild(int arg0, int arg1) {
		if (arg0 == 0)
			return categoryList.get(arg1);
		return null;
	}

	@Override
	public long getChildId(int arg0, int arg1) {
		return arg0 * 1000 + arg1;
	}

	@Override
	public View getChildView(int arg0, int arg1, boolean arg2,
			View convertView, ViewGroup arg4) {
		if (convertView == null) {
			convertView = getLayoutInflater().inflate(R.layout.menu_item_list,
					null);
		}
		TextView textContent = (TextView) convertView.findViewById(R.id.tvMenu);
		textContent.setText(categoryList.get(arg1));
		return convertView;
	}

	@Override
	public int getChildrenCount(int arg0) {
		if (arg0 == 0) {
			return categoryList.size();
		}
		return 0;
	}

	@Override
	public Object getGroup(int arg0) {
		return parentsList.get(arg0);
	}

	@Override
	public int getGroupCount() {
		return parentsList.size();
	}

	@Override
	public long getGroupId(int arg0) {
		return arg0;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		// 取得用于显示给定分组的视图。 这个方法仅返回分组的视图对象， 要想获取子元素的视图对象，
		// 就需要调用 getChildView(int, int, boolean, View, ViewGroup)。
		// View v;
		if (convertView == null) {
			convertView = getLayoutInflater().inflate(R.layout.groups, null);
		}
		TextView groupTextView = (TextView) convertView
				.findViewById(R.id.groupTextView);
		groupTextView.setText(parentsList.get(groupPosition));
		return convertView;
	}

	public abstract LayoutInflater getLayoutInflater();

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int arg0, int arg1) {
		return true;
	}

}
