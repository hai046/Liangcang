package com.liangcang.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.liangcang.R;
import com.liangcang.managers.ColorManager;
import com.liangcang.managers.DataCallBack;
import com.liangcang.managers.DataManager;
import com.liangcang.mode.DetailUser;
import com.liangcang.mode.User;
import com.liangcang.util.ImageDownloader;
import com.liangcang.util.MyLog;
import com.liangcang.util.MyToast;
import com.liangcang.util.Util;

public class BaseThrAdapter extends MyBaseAdapter<User> implements
		OnClickListener {

	public enum Type {
		/**
		 * 粉丝列表
		 */
		Followed("user/followed"),
		/**
		 * 关注列表
		 */
		Following("user/following"),
		/**
		 * 达人
		 */
		EXPERT("expert"), Search("search/user");

		private String path;

		Type(String path) {
			this.path = path;
		}

		public String getValue() {
			return this.path;
		}
	}

	private Type type;

	public void setType(Type type) {
		this.type = type;
		path = null;
	}

	private String path = null;

	public void setPath(String path) {
		this.path = path;
	}

	private String getPath() {
		return TextUtils.isEmpty(path) ? type.getValue() : path;
	}

	Map<String, String> qurestParams = new HashMap<String, String>();

	public void onRefresh() {
		qurestParams.put("page", "1");
		clear();
		doRequest();
	}

	public void addParams(String key, String value) {
		qurestParams.put(key, value);
	}

	public void setUserId(String userId) {
		qurestParams.put("owner_id", userId);

	}

	public void onLoadMore() {
		if (getSize() % 18 == 0) {
			qurestParams.put("page", (getSize() / 18 + 1) + "");
			doRequest();
		}

	}

	private void doRequest() {
		qurestParams.put("count", "18");
		DataManager.getInstance(mContext).getData(getPath(), qurestParams,
				true, mDataCallBack);
	}

	public void onSuccess() {

	}

	public void failure(String msg) {

	}

	private DataCallBack<String> mDataCallBack = new DataCallBack<String>() {

		@Override
		public void failure(String msg) {
			MyLog.e("baseThrAdapte", "failure=" + msg);
			MyToast.showMsgLong(mContext, msg);
			BaseThrAdapter.this.failure(msg);
		}

		public void success(String t) {
			// MyLog.e("success", "success=" + t);
			BaseThrAdapter.this.onSuccess();
			if (type == Type.Search || type == Type.EXPERT || type == null) {
				List<User> list = JSON.parseArray(t, User.class);
				if (list != null) {
					addAll(list);
					notifyDataSetChanged();
				}
			} else {

				DetailUser user = JSON.parseObject(t, DetailUser.class);
				if (user.getUsers() != null) {
					if (data == null) {
						addAll(user.getUsers());
						user.getUsers().clear();
						BaseThrAdapter.this.data = user;
					}
					notifyDataSetChanged();
				} else {
					BaseThrAdapter.this.data = user;
				}
			}

		}

	};
	private LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
			LinearLayout.LayoutParams.MATCH_PARENT,
			LinearLayout.LayoutParams.MATCH_PARENT);
	private LinearLayout.LayoutParams paramsItem;
	private Context mContext;

	private DetailUser data;

	public BaseThrAdapter(Context mContext) {
		this.mContext = mContext;
		params.weight = 1;
		int width = (int) (Util.getDisplayWindth(mContext) * 0.33);
		if (width < 158) {
			width = 158;
		}

		paramsItem = new LinearLayout.LayoutParams(width, width);
	}

	@Override
	public int getCount() {
		int size = super.getCount();
		return (size % 3 == 0 ? (size / 3) : (size / 3 + 1));
	}

	public int getSize() {
		return getDateSet().size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		LinearLayout mLinearLayout;
		if (convertView == null) {
			mLinearLayout = new LinearLayout(mContext);
			mLinearLayout.setGravity(Gravity.LEFT);
		} else {
			mLinearLayout = (LinearLayout) convertView;
			if (position + 1 == getCount())
				mLinearLayout.removeAllViews();
		}
		int index = position * 3;
		View view = mLinearLayout.getChildAt(0);
		if (view == null) {
			mLinearLayout.addView(bindView(index, getItem(index), view), 0,
					params);
		} else {
			bindView(index, getItem(index), view);
		}
		view = mLinearLayout.getChildAt(0);
		view.setTag(index);
		view.setOnClickListener(this);

		index++;
		view = mLinearLayout.getChildAt(1);
		if (index < getSize()) {
			if (view == null) {
				mLinearLayout.addView(bindView(index, getItem(index), view), 1,
						params);
			} else {
				bindView(index, getItem(index), view);
			}
			mLinearLayout.getChildAt(1).setVisibility(View.VISIBLE);
		} else {
			if (view == null) {
				mLinearLayout.addView(
						bindView(index, getItem(index - 1), view), 1, params);
			} else {
				bindView(index, getItem(index - 1), view);
			}
			mLinearLayout.getChildAt(1).setVisibility(View.INVISIBLE);
		}
		view = mLinearLayout.getChildAt(1);
		view.setTag(index);
		view.setOnClickListener(this);

		index++;
		view = mLinearLayout.getChildAt(2);
		if (index < getSize()) {
			if (view == null) {
				mLinearLayout.addView(bindView(index, getItem(index), view), 2,
						params);
			} else {
				bindView(index, getItem(index), view);
			}
			mLinearLayout.getChildAt(2).setVisibility(View.VISIBLE);
		} else {
			if (view == null) {
				mLinearLayout.addView(
						bindView(index, getItem(index - 2), view), 2, params);
			} else {
				bindView(index, getItem(index - 2), view);
			}
			mLinearLayout.getChildAt(2).setVisibility(View.INVISIBLE);
		}
		view = mLinearLayout.getChildAt(2);
		view.setTag(index);
		view.setOnClickListener(this);
		mLinearLayout.setBackgroundColor(Color.BLACK);
		return mLinearLayout;
	}

	@Override
	public View bindView(int position, User t, View view) {
		ImageView userImage = null;
		if (view == null) {
			view = LayoutInflater.from(mContext).inflate(R.layout.fans_layout,
					null);
			userImage = (ImageView) view.findViewById(R.id.fans_imageUser);
			userImage.setLayoutParams(paramsItem);
		} else {
			userImage = (ImageView) view.findViewById(R.id.fans_imageUser);
		}
		TextView tvName = (TextView) view.findViewById(R.id.fans_userName);
		TextView tvDesc = (TextView) view.findViewById(R.id.fans_userDesc);
		tvDesc.setTextColor(ColorManager.getInsance().getDefaltColor());
		ImageDownloader.getInstance().download(t.getUser_image(), userImage);
		tvName.setText(t.getUser_name());
		tvDesc.setText(t.getUser_desc());
		return view;
	}

	@Override
	public void onClick(View v) {
		MyLog.d("baseThrAdapter", v.getTag());
		if (v.getTag() == null)
			return;
		int position = Integer.parseInt(v.getTag().toString());
		User user = getItem(position);
		MyLog.d("baseThrAdapter", user);
		Util.gotoUser(mContext, user.getUser_id(), user.getUser_image(),
				user.getUser_name());

	}
}
