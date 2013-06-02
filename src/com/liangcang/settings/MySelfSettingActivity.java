package com.liangcang.settings;

import java.util.HashMap;
import java.util.Map;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.liangcang.R;
import com.liangcang.base.MyApplication;
import com.liangcang.base.MyBaseAdapter;
import com.liangcang.base.PhotoBaseActivity;
import com.liangcang.managers.DataCallBack;
import com.liangcang.managers.DataManager;
import com.liangcang.mode.User;
import com.liangcang.util.ImageDownloader;
import com.liangcang.util.MyLog;
import com.liangcang.util.MyToast;
import com.liangcang.webUtil.FileItem;

/**
 * 个人设置页面
 * 
 * @author 邓海柱 haizhu12345@gmail.com
 */
public class MySelfSettingActivity extends PhotoBaseActivity {

	private ListView mListView;

	// http://api.iliangcang.com/user/account?
	// user_image=(二进制数据)&
	// email=i@iliangcang.com&
	// user_name=liyu&
	// user_desc=败家子
	@Override
	public void callBackPath(String path) {
		MyLog.e("setting", "path=" + path);
		upDateAccountUserImage(path);
	}

	private void upDateAccountUserImage(String filePath) {
		Map<String, FileItem> fileParams = new HashMap<String, FileItem>();
		FileItem mFileItem = new FileItem(filePath);
		fileParams.put("user_image", mFileItem);
		DataManager.getInstance(this).doPostFile("user/account", fileParams,
				mDataCallBack);
	}

	private DataCallBack<String> mDataCallBack = new DataCallBack<String>() {
		@Override
		public void success(String t) {
			MyToast.showMsgLong(MySelfSettingActivity.this, t);
			MyLog.e("updata", "updata="+t);
		}

		@Override
		public void failure(String msg) {
			MyLog.e("updata", "updata  msg="+msg);
			MyToast.showMsgLong(MySelfSettingActivity.this, msg);
		}
	};

	@Override
	public void setImageBitmap(Bitmap bitmap) {
		imageUser.setImageBitmap(bitmap);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mListView = new ListView(this);
		initDataAndView();
		setContentView(mListView);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				onClick(arg2);

			}
		});
	}

	protected void onClick(int position) {
		switch (position) {
		case 0:
			doPickPhotoAction();
			break;

		default:
			break;
		}

	}

	private void initDataAndView() {
		MyBaseAdapter<String> adapter = new MyBaseAdapter<String>() {
			@Override
			public View bindView(int position, String t, View view) {
				if (view == null) {
					view = getLayoutInflater().inflate(
							R.layout.settings_item_content, null);

				}
				TextView name = (TextView) view.findViewById(R.id.name);
				TextView content = (TextView) view.findViewById(R.id.content);
				name.setText(t);
				if (position == 0) {
					content.setText(user.getEmail());
				} else if (position == 1) {
					content.setText(user.getUser_name());
				} else if (position == 2) {
					content.setText(user.getUser_desc());
				}

				return view;
			}
		};
		addHeadView();
		adapter.add("邮箱");
		adapter.add("昵称");
		adapter.add("我的工作");

		mListView.setAdapter(adapter);
	}

	private User user;

	/**
	 * 设置个人图像
	 */
	ImageView imageUser;

	private void addHeadView() {
		View viewHead = getLayoutInflater().inflate(
				R.layout.settings_item_imagecenter, null);
		imageUser = (ImageView) viewHead.findViewById(R.id.imageUser);
		
		user = MyApplication.getUser();
		ImageDownloader.getInstance().download(user.getUser_image(), imageUser);
		mListView.addHeaderView(viewHead);
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub

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

	}

}
