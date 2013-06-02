package com.liangcang;

import java.util.ArrayList;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class HomeAdapter extends BaseAdapter {

	private ArrayList<String>menus=new ArrayList<String>();
	private void initCagegorys()
	{
		menus.clear();
		menus.add("精选");
		menus.add("分类");
		menus.add("全部");
		menus.add("男士");
		menus.add("");
		menus.add("");
		menus.add("");
		menus.add("");
		menus.add("");
		
	}
	@Override
	public int getCount() {
		
		return 0;
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return null;
	}

}
