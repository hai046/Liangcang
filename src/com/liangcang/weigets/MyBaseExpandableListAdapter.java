package com.liangcang.weigets;

import java.util.ArrayList;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ListView;

public class MyBaseExpandableListAdapter extends BaseExpandableListAdapter {

	
//	void initDate()
//	{
//		ArrayList<String>parentsList=new ArrayList<String>();
//		parentsList.add("分类");
//		parentsList.add("打人");
//		parentsList.add("店铺");
//		parentsList.add("折扣");
//		parentsList.add("信息");
//		
//		ArrayList<String>categoryList=new ArrayList<String>();
//		categoryList.add("男士");
//		categoryList.add("女士");
//		categoryList.add("家具");
//		categoryList.add("美容");
//		categoryList.add("宠物");
//		categoryList.add("孩子");
//		
//	}
	
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		
		return null;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		
		return 0;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return false;
	}

}
