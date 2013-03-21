package com.liangcang.base;

import java.io.File;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.buy.stores.Settings;
import com.liangcang.R;
import com.liangcang.util.BitmapUtil;
import com.liangcang.util.Rotate3dAnimation;
import com.liangcang.webUtil.CheckUpdataManager;

public abstract class BaseViewPagerActivity extends IActivity implements
		OnClickListener {
	// private SectionsPagerAdapter mSectionsPagerAdapter;
	// private MyViewPager mViewPager;
	private LinearLayout linearBottomLayout;
	private Button btnLeft, btnRight;
	private TextView tvTitle;
	private RelativeLayout mainLayout;
	String TAG = "BaseViewPagerActivity";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// try {
		super.onCreate(savedInstanceState);
		// } catch (Exception e) {
		// finish( );
		// Intent intent = new Intent( );
		// intent.setClass( this, MainActivity.class );
		// startActivity( intent );

		// return;
		// }

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		btnLeft = (Button) findViewById(R.id.btn_left_title);
		btnRight = (Button) findViewById(R.id.btn_right_title);
		tvTitle = (TextView) findViewById(R.id.tv_title);
		// RelativeLayout include_topLayout = (RelativeLayout) findViewById(
		// R.id.mainTitleLayout );
		// SkinManger.getInstance( this ).setBackgroundDrable(
		// include_topLayout, "dhbj" );
		btnLeft.setOnClickListener(this);
		btnRight.setOnClickListener(this);
		mainLayout = (RelativeLayout) findViewById(R.id.main_relalayout);
		linearBottomLayout = (LinearLayout) findViewById(R.id.mainBottomLayout);
		linearBottomLayout.removeAllViews();
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.FILL_PARENT);
		params.weight = 1;
		for (int i = 0; i < getCount(); i++) {
			View view = getBottomMenu(i);
			if (view != null) {
				view.setId(100 + i);
				linearBottomLayout.addView(view, params);
				view.setOnClickListener(this);
			}
		}
		switchMenuBg(0);
		// listenerNetworkChange( );
		checkUpdata();

	}

	private void checkUpdata() {
		long time = Settings.getInSettings(this).getLong("lastCheckTime",
				24 * 60 * 60 * 1000 + 1);
		if (System.currentTimeMillis() - time >= 24 * 60 * 60 * 1000) {
			new CheckUpdataManager(this).checkAndUpdata(false);
			Settings.getInSettings(this).putLong("lastCheckTime",
					System.currentTimeMillis());
		}

	}

	public void deleteCacheFiles() {
		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... params) {

				File file = getCacheDir();

				if (file != null && file.exists() && file.isDirectory()) {
					for (File item : file.listFiles()) {
						item.delete();
						// MyLog.d( TAG, "file " + item.getPath( ) );
					}
					file.delete();
				}
				BitmapUtil.clearAllLoacalCache();
				return null;
			}
		}.execute();
	}

	// private NetUtil mNetUtil;
	// private boolean first = true;
	//
	// private void listenerNetworkChange() {
	// mNetUtil = new NetUtil( this );
	// mNetUtil.setNetWorkCallback( new NetWorkCallback( ) {
	// @Override
	// public void netChanger(int typeMobile, State mobile, boolean
	// isFastNetwork) {
	// MyLog.i( "network", "typeMobile=" + typeMobile + "  mobile=" + mobile );
	// switch (typeMobile) {
	// case ConnectivityManager.TYPE_WIFI:
	// MyToast.showMsgLong( BaseViewPagerActivity.this, "在WIFI模式下，自动为您切换到高清模�?
	// );
	// if (getMinWH( ) <= 0)
	// ImageDownloader.getInstance( ).setQuality( Quality.MIDDLE );
	// else {
	// Quality mQuality = ImageDownloader.getInstance( ).getNextQuality(
	// getMinWH( ) );
	// MyLog.e( "network", "222mQuality=" + mQuality + "  " + getMinWH( ) );
	// ImageDownloader.getInstance( ).setQuality( mQuality );
	// }
	// if (!first) {
	// deleteCacheFiles( );
	// }
	// first = false;
	// break;
	// case ConnectivityManager.TYPE_MOBILE:
	// if (isFastNetwork) {
	// MyToast.showMsgLong( BaseViewPagerActivity.this, "在手�?G模式下，自动为您切换到标清模�?
	// );
	//
	// if (getMinWH( ) <= 0)
	// ImageDownloader.getInstance( ).setQuality( Quality.LOW );
	// else {
	// Quality mQuality = ImageDownloader.getInstance( ).getQuality( getMinWH( )
	// );
	// ImageDownloader.getInstance( ).setQuality( mQuality );
	// MyLog.e( "network", "11mQuality=" + mQuality + "  " + getMinWH( ) );
	// }
	// } else {
	// MyToast.showMsgLong( BaseViewPagerActivity.this, "由于您的网�?较慢，自动为您切换到极�?模式"
	// );
	// ImageDownloader.getInstance( ).setQuality( Quality.LOWEST );
	// }
	// break;
	// default:
	// break;
	// }
	// }
	// } );
	//
	// }

	private int getMinWH() {

		return (int) (Math
				.min(getWindowManager().getDefaultDisplay().getHeight(),
						getWindowManager().getDefaultDisplay().getWidth()) * 0.5);

	}

	protected void switchView(int position) {
		currentIndex = position;
		mainLayout.removeAllViews();
		if (getShowView(position) != null) {
			mainLayout.addView(getShowView(position));
		}
		switchMenuBg(position);
		onPageSelected(position);
	}

	// @Override
	// public void netChanger(int typeMobile, State mobile) {
	//
	// if (typeMobile == ConnectivityManager.TYPE_MOBILE) {
	// if (mobile == State.CONNECTED) {
	// Toast.makeText( this, "当前为模式，切换到低质量图片", Toast.LENGTH_LONG ).show( );
	// ImageDownloader.getInstance( ).setQuality( ImageDownloader.Quality.LOW );
	// }
	// } else if (typeMobile == ConnectivityManager.TYPE_WIFI) {
	// if (mobile == State.CONNECTED) {
	// Toast.makeText( this, "当前为wifi模式，切换到高清原图", Toast.LENGTH_LONG ).show( );
	// ImageDownloader.getInstance( ).setQuality( ImageDownloader.Quality.HIGHT
	// );
	// }
	// }
	//
	// }

	public void startLeftAnimation() {

		startLeftAnimation(0, 360);

	}

	public void startLeftAnimation(final int from, final int to) {

		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				int w = (int) (btnLeft.getWidth() * 0.5);
				int h = (int) (btnLeft.getHeight() * 0.5);
				if (Math.min(w, h) <= 0)
					return;
				Rotate3dAnimation mRotate3dAnimation = new Rotate3dAnimation(
						from, to, w, h, 0, false);
				mRotate3dAnimation.setInterpolator(new BounceInterpolator());
				mRotate3dAnimation.setDuration(3000);
				btnLeft.startAnimation(mRotate3dAnimation);
			}
		});

	}

	private static int currentIndex = 0;

	@Override
	public void onClick(View v) {
		// MyLog.d( "base", v.getId( ) );
		if (v.getId() >= 100 && v.getId() < getCount() + 100) {
			switchView(v.getId() - 100);
			return;
		}
		switch (v.getId()) {
		case R.id.btn_left_title:
			// onClickLeftButton( mViewPager.getCurrentItem( ) );
			onClickLeftButton(currentIndex);
			break;
		case R.id.btn_right_title:
			// onClickRightButton( mViewPager.getCurrentItem( ) );
			onClickRightButton(currentIndex);
			break;
		default:
			break;
		}
	}

	public void setCurrentTitleString(String leftBtnText, String centerText,
			String rightBtnText) {
		if (leftBtnText == null) {
			btnLeft.setVisibility(View.GONE);
		} else {
			btnLeft.setVisibility(View.VISIBLE);
			btnLeft.setText(leftBtnText);
		}

		tvTitle.setText(centerText);

		if (rightBtnText == null) {
			btnRight.setVisibility(View.GONE);
		} else {
			btnRight.setVisibility(View.VISIBLE);
			btnRight.setText(rightBtnText);
		}
	}

	public void setLeftText(String text) {
		if (text == null) {
			btnLeft.setText(null);
//			btnLeft.setVisibility(View.GONE);
		} else {
//			btnLeft.setVisibility(View.VISIBLE);
			btnLeft.setBackgroundColor(Color.TRANSPARENT);
			btnLeft.setText(text);
		}
	}

	public View getBottomMenu(int position) {
		View view = getLayoutInflater().inflate(R.layout.menu_item, null);
		ImageView btn = (ImageView) view.findViewById(R.id.menu_btn);
		switch (position) {
		case 0:
			// btn.setImageResource( R.drawable.tuijian );
			break;
		case 1:
			// btn.setImageResource( R.drawable.fenlei );
			break;
		case 2:
			// btn.setImageResource( R.drawable.yule );
			break;
		case 3:
			// btn.setImageResource( R.drawable.shoucang );
			break;
		case 4:
			// btn.setImageResource( R.drawable.shezhi );
			break;
		default:
			break;
		}
		btn.setImageResource(R.drawable.tuijian);
		TextView tv = (TextView) view.findViewById(R.id.menu_title);
		tv.setText(getResources().getStringArray(R.array.centerText)[position]);
		return view;
	}

	private void switchMenuBg(int position) {
		// MyLog.e( "base", "positon=" + position );
		for (int i = 0; i < getCount(); i++) {
			if (position == i)
				continue;
			// MyLog.e( "base", "iiiiiiiiiiiiiiii=" + i );
			View v = linearBottomLayout.getChildAt(i);
			TextView tv = (TextView) v.findViewById(R.id.menu_title);
			tv.setTextColor(getResources().getColor(R.color.black_98));
			ImageView btn = (ImageView) v.findViewById(R.id.menu_btn);
			switch (i) {
			case 0:
				// btn.setImageResource( R.drawable.tuijian );
				break;
			case 1:
				// btn.setImageResource( R.drawable.fenlei );
				break;
			case 2:
				// btn.setImageResource( R.drawable.yule );
				break;
			case 3:
				// btn.setImageResource( R.drawable.shoucang );
				break;
			case 4:
				// btn.setImageResource( R.drawable.shezhi );
				break;
			default:
				break;
			}
			btn.setImageResource(R.drawable.tuijian);
		}
		ImageView btn = (ImageView) linearBottomLayout.getChildAt(position)
				.findViewById(R.id.menu_btn);
		switch (position) {
		case 0:
			// btn.setImageResource( R.drawable.tuijian_on );
			break;
		case 1:
			// btn.setImageResource( R.drawable.fenlei_on );
			break;
		case 2:
			// btn.setImageResource( R.drawable.yule_on );
			break;
		case 3:
			// btn.setImageResource( R.drawable.shoucang_on );
			break;
		case 4:
			// btn.setImageResource( R.drawable.shezhi_on );
			break;
		default:
			break;
		}
		btn.setImageResource(R.drawable.tuijian_on);
		TextView tv = (TextView) linearBottomLayout.getChildAt(position)
				.findViewById(R.id.menu_title);
		tv.setTextColor(getResources().getColor(R.color.bg));
	}

	/**
	 * 
	 * @param position
	 */
	public abstract void onClickLeftButton(int position);

	/**
	 * 
	 * @param position
	 */
	public abstract void onClickRightButton(int position);

	/**
	 * 
	 * @return
	 */
	public abstract int getCount();

	/**
	 * 初始化对应index 上的view
	 * 
	 * @param index
	 * @param inflater
	 * @param container
	 * @param savedInstanceState
	 * @return
	 */
	public abstract View getShowView(int index);

	/**
	 * 
	 * @param position
	 */
	public abstract void onPageSelected(int position);

	public void setNaviationLeftBg(int leftImgId) {
		if (leftImgId > 0) {
			btnLeft.setBackgroundResource(leftImgId);
			// btnLeft.setBackgroundDrawable( SkinManger.getInstance( this
			// ).getDrawable( leftImgId ) );
		} else {
			btnLeft.setBackgroundColor(Color.TRANSPARENT);
		}
	}

	public void setNaviationBg(int leftImgId, int centerImageId,
			int rightImageId) {
		if (leftImgId > 0) {
			btnLeft.setBackgroundResource(leftImgId);
			// SkinManger.getInstance( this ).setBackgroundDrable( btnLeft,
			// leftImgId );
		} else {
			btnLeft.setBackgroundColor(Color.TRANSPARENT);
		}

		if (centerImageId > 0) {
			tvTitle.setText(null);
			// SkinManger.getInstance( this ).setBackgroundDrable( tvTitle,
			// centerImageId );
			tvTitle.setBackgroundResource(centerImageId);
		} else {
			tvTitle.setBackgroundColor(Color.TRANSPARENT);
		}
		if (rightImageId > 0) {
			btnRight.setBackgroundResource(rightImageId);
			// SkinManger.getInstance( this ).setBackgroundDrable( btnRight,
			// rightImageId );
		} else {
			btnRight.setBackgroundColor(Color.TRANSPARENT);
		}

	}

	public int getCurrentItemId() {
		return currentIndex;
		// return mViewPager.getCurrentItem( );
	}

	public void setCurrentItem(int index) {
		switchView(index);
		// mViewPager.setCurrentItem( index );
	}

	@Deprecated
	@Override
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
	}

	@Deprecated
	@Override
	public void setContentView(View view, LayoutParams params) {
		super.setContentView(view, params);
	}

	@Deprecated
	@Override
	public void setContentView(View view) {
		super.setContentView(view);
	}

	@Override
	protected void onRestart() {
		// map.clear( );
//		startLeftAnimation(180, 0);
		// MyLog.d( "status", "onRestart" );
		super.onRestart();
	}

	// private Animation mRotaAnimation;

	// protected void onRightBtnRota() {
	// if (mRotaAnimation == null) {
	// mRotaAnimation = AnimationUtils.loadAnimation( this, R.anim.rota360 );
	// }
	// mRotaAnimation.setRepeatCount( 100000 );
	// mRotaAnimation.setRepeatMode( Animation.RESTART );
	// btnRight.clearAnimation( );
	// btnRight.startAnimation( mRotaAnimation );
	// }

	protected void stopRinghtBtn() {
		btnRight.clearAnimation();
	}

	@Override
	protected void onPause() {
		// MyLog.d( "status", "onPause" );
		super.onPause();
	}

	@Override
	protected void onStop() {
		// MyLog.d( "status", "==============onStop" );
		super.onStop();
	}

	@Override
	protected void onDestroy() {

		super.onDestroy();
	}

}
