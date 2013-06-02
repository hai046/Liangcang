package com.liangcang.activits;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.liangcang.R;
import com.liangcang.base.BaseActivity;
import com.liangcang.base.ModeCallBack;
import com.liangcang.managers.ColorManager;
import com.liangcang.mode.MessageNum;
import com.liangcang.util.Util;
import com.liangcang.views.DyncView;
import com.liangcang.views.FansView;
import com.liangcang.views.GoodsCommentNewView;
import com.liangcang.views.MsgView;
import com.liangcang.views.PrivateMsgView;

public class MessageActivity extends BaseActivity {

	private LinearLayout menuLayout;
	private RelativeLayout mContentLayout;
	private DyncView mDyncView;
	private MsgView mMsgView;
	private PrivateMsgView mChatView;
	private FansView mFansView;
	private GoodsCommentNewView mCommentNewView;
	// private LikeListView mLikeListView;
	private LinearLayout.LayoutParams menuParams = new LinearLayout.LayoutParams(
			LinearLayout.LayoutParams.WRAP_CONTENT,
			LinearLayout.LayoutParams.WRAP_CONTENT);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.message_home_layout);
		menuLayout = (LinearLayout) findViewById(R.id.mainHeaderMenu);
		mContentLayout = (RelativeLayout) findViewById(R.id.mainCenterContentLayout);
		initExtraData();
		mDyncView = new DyncView(this);
		mMsgView = new MsgView(this);
		mChatView = new PrivateMsgView(this);
		mFansView = new FansView(this);
		mCommentNewView = new GoodsCommentNewView(this);
		initMenu();
		mContentLayout.addView(mDyncView.getView());

		
	}

	private void initExtraData() {
		Util.getMessageNum(this, new ModeCallBack<MessageNum>() {

			@Override
			public void onCallBack(MessageNum t) {
				// menuLayout.removeAllViews();
				setMessageValue(0, t.getActivity());
				setMessageValue(1, t.getNotifications());
				setMessageValue(2, t.getMessages());
				setMessageValue(3, t.getFollowed());
				setMessageValue(4, t.getNew_comm_sum());

			}
		});

	}

	private void setMessageValue(int index, String value) {
		if (menuLayout.getChildCount() > index) {
			View menu = menuLayout.getChildAt(index);
			// TextView name=(TextView) menu.findViewById(R.id.msg_header_name);
			TextView num = (TextView) menu
					.findViewById(R.id.msg_header_msg_num);
			num.setText(value);

		}
	}

	private void initMenu() {
		menuParams.weight = 1;
		String[] messageList = getResources().getStringArray(
				R.array.messageHeaderMenu);
		for (int i = 0; i < messageList.length; i++) {
			addMenu(i, messageList[i]);
		}
	}

	private void addMenu(int index, String msg) {
		View menu = getLayoutInflater().inflate(R.layout.message_menu_item,
				null);
		TextView name = (TextView) menu.findViewById(R.id.msg_header_name);
		TextView num = (TextView) menu.findViewById(R.id.msg_header_msg_num);
		menuLayout.addView(menu, menuParams);
		menu.setId(index);
		name.setText(msg);
		name.setTextColor(ColorManager.getInsance().getDefaltColor());
		num.setTextColor(ColorManager.getInsance().getDefaltColor());
		menu.setOnClickListener(mClickListener);
		// menuLayout
	}

	private View.OnClickListener mClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			mContentLayout.removeAllViews();
			switch (v.getId()) {
			case 0:
				mContentLayout.addView(mDyncView.getView());
				break;
			case 1:
				mContentLayout.addView(mMsgView.getView());
				break;
			case 2:
				mContentLayout.addView(mChatView.getView());
				break;
			case 3:
				mContentLayout.addView(mFansView.getView());
				break;
			case 4:
				mContentLayout.addView(mCommentNewView.getView());
				break;
			default:
				break;
			}
			initExtraData();
		}
	};

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

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub

	}

}
