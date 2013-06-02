package com.liangcang.menus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.liangcang.R;
import com.liangcang.activits.ColorModeActivity;
import com.liangcang.base.BaseActivity;
import com.liangcang.base.MyApplication;
import com.liangcang.base.MyBaseAdapter;
import com.liangcang.mode.User;
import com.liangcang.settings.MySelfSettingActivity;
import com.liangcang.settings.PasswordSettingActivity;
import com.liangcang.util.ImageDownloader;
import com.liangcang.util.Util;

public class SettingActivity extends BaseActivity {
	private ListView listView = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		listView = new ListView(this);
		initDataAndView();
		setContentView(listView);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				swtchTo(arg2);

			}
		});
		setLeftImage(R.drawable.navigation_back);
	}

	protected void swtchTo(int key) {
		switch (key) {
		case 0:
			if(MyApplication.isLogin()==false)
			{
				Util.gotoLogin(this);
				return;
			}
			Intent intent = new Intent();
			intent.setClass(this, MySelfSettingActivity.class);
			startActivity(intent);
			break;
		case 1:
			intent = new Intent();
			intent.setClass(this, PasswordSettingActivity.class);
			startActivity(intent);
			break;
		case 2:

			break;
		case 5:
			intent = new Intent();
			intent.setClass(this, ColorModeActivity.class);
			startActivity(intent);
			break;
		case 6:
			
			MyApplication.clearUser(getApplicationContext());
			updateHeader();
			adapter.notifyDataSetChanged();
			break;
		default:
			break;
		}

	}

	private MyBaseAdapter<String> adapter = null;

	private void initDataAndView() {

		if (adapter == null) {
			addHeaderView();
			adapter = new MyBaseAdapter<String>() {

				@Override
				public View bindView(int position, String t, View view) {
					if (view == null) {
						view = getLayoutInflater().inflate(
								R.layout.settings_item, null);

					}
					TextView name = (TextView) view.findViewById(R.id.name);
					name.setText(t);
					return view;
				}
			};
			for (String item : getResources().getStringArray(R.array.settings)) {
				adapter.add(item);
			}
			adapter.add("退出登录");
		}
		listView.setAdapter(adapter);
	}

	private ImageView imageUser;
	private View headView;

	/**
	 * 这里添加个人信息
	 */
	private void addHeaderView() {
		headView = getLayoutInflater()
				.inflate(R.layout.settings_item_top, null);
		listView.addHeaderView(headView);
		updateHeader();

	}

	private void updateHeader() {
		TextView name = (TextView) headView.findViewById(R.id.name);
		TextView content = (TextView) headView.findViewById(R.id.content);
		imageUser = (ImageView) headView.findViewById(R.id.image);
		User user = MyApplication.getUser();
		if(user!=null)
		{
			ImageDownloader.getInstance().download(user.getUser_image(), imageUser);
			name.setText(user.getUser_name());
			content.setText(user.getEmail());
		}else
		{
			imageUser.setImageResource (R.drawable.user);
			name.setText(null);
			content.setText(null);
		}
		

	}

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
		finish();
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub

	}

}
