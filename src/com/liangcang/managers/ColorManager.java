package com.liangcang.managers;

import java.util.ArrayList;
import java.util.HashMap;

import com.liangcang.R;
import com.liangcang.util.MyLog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

public class ColorManager {
	private static ColorManager mColorManager;

	public synchronized static ColorManager getInsance() {
		if (mColorManager == null) {
			mColorManager = new ColorManager();
		}
		return mColorManager;
	}

	private Context mContext;

	public void init(Context mContext) {
		this.mContext = mContext;
	}

	private HashMap<Integer, ArrayList<Integer>> allColors = new HashMap<Integer, ArrayList<Integer>>();

	private ColorManager() {
		addColor(1, Color.rgb(166, 222, 223), Color.rgb(137, 197, 224),
				Color.rgb(133, 173, 218), Color.rgb(104, 149, 186),
				Color.rgb(81, 118, 159));
		addColor(2, Color.rgb(128, 222, 98), Color.rgb(128, 201, 74),
				Color.rgb(20, 204, 77), Color.rgb(26, 152, 72),
				Color.rgb(17, 129, 73));
		addColor(3, Color.rgb(255, 192, 171), Color.rgb(237, 143, 122),
				Color.rgb(247, 127, 119), Color.rgb(255, 102, 94),
				Color.rgb(255, 76, 78));
		addColor(4, Color.rgb(253, 234, 79), Color.rgb(255, 216, 80),
				Color.rgb(249, 202, 70), Color.rgb(249, 190, 47),
				Color.rgb(213, 130, 82));
		addColor(5, Color.rgb(204, 204, 204), Color.rgb(166, 166, 166),
				Color.rgb(128, 128, 128), Color.rgb(102, 102, 102),
				Color.rgb(77, 77, 77));
		setTemplate_id(1);
	}

	private int template_id;

	public int getTemplate_id() {
		return template_id;
	}

	public void setTemplate_id(int template_id) {
		this.template_id = template_id;

	}

	public int getOffset() {
		return this.template_id - 1;
	}

	public int getColor(int index) {
		return getColor(getTemplate_id(), index);
	}

	public int getColor(int template_id, int index) {
		if (template_id > 4 || template_id < 0) {
			MyLog.e("ColorManager", "颜色模板范围错误！！！！  templete_id=" + template_id);
			template_id = 1;
		}
		return allColors.get(template_id).get(index);
	}

	public int getDefaltColor() {
		return getColor(1);
	}

	public int getDefaltTwoColor() {
		return getColor(3);
	}

	public int getDefaltColor(int templete_id) {

		return getColor(templete_id, 1);
	}

	public int getDefaltTwoColor(int templete_id) {
		return getColor(templete_id, 3);
	}

	private void addColor(int index, int rgb, int rgb2, int rgb3, int rgb4,
			int rgb5) {
		ArrayList<Integer> colors = new ArrayList<Integer>();
		colors.clear();
		colors.add(rgb);
		colors.add(rgb2);
		colors.add(rgb3);
		colors.add(rgb4);
		colors.add(rgb5);
		allColors.put(index, colors);

	}

	/**
	 * 
	 * @param resId
	 * @return
	 */
	public Drawable getDrawableByID(int resId) {
		try {
			return mContext.getResources().getDrawable(resId + getOffset());
		} catch (Exception e) {
			return mContext.getResources().getDrawable(resId);
		}
	}

	public int getDrawableIDByID(int resId) {
		try {
			return resId + getOffset();
		} catch (Exception e) {
			return resId;
		}
	}

}
