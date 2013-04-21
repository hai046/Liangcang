package com.liangcang.base;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.liangcang.R;
import com.liangcang.mode.User;
import com.liangcang.util.ImageDownloader;

public abstract class BaseActivity extends IActivity implements OnClickListener {

	private ImageView btnLeft, btnRight, btnRight2;
	private TextView tvTitle;
	private View viewLine;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.base_layout);

		btnLeft = (ImageView) findViewById(R.id.btn_left_title);
		btnRight = (ImageView) findViewById(R.id.btn_right_title);
		btnRight2 = (ImageView) findViewById(R.id.btn_right2_title);
		btnRight2.setOnClickListener(this);
		btnLeft.setOnClickListener(this);
		btnRight.setOnClickListener(this);
		tvTitle = (TextView) findViewById(R.id.tv_title);
		// btnLeft.setText(getNavigationLeftText());
		viewLine = findViewById(R.id.navigation_line);
		initNavigation();
		if (isShowRightClose()) {
			setRightImage(R.drawable.nav_close);
		}
	}

	void initNavigation() {
		User user = ((MyApplication) getApplication()).getUser();
		if (user != null) {
			btnRight.setVisibility(View.VISIBLE);
			btnRight2.setVisibility(View.GONE);
			viewLine.setVisibility(View.GONE);
			ImageDownloader.getInstance().download(user.getUser_image(),
					btnRight);
		} else {
			showRegister();
		}

	}

	public void showRegister() {
		btnRight.setVisibility(View.VISIBLE);
		btnRight2.setVisibility(View.VISIBLE);
		viewLine.setVisibility(View.VISIBLE);
		btnRight.setImageResource(R.drawable.nav_login);
		btnRight2.setImageResource(R.drawable.nav_register);
	}

	public void setRightBackground(int resid) {
		// btnRight.setText(null);
		btnRight.setBackgroundColor(resid);
		btnRight.setVisibility(View.VISIBLE);
	}

	public void setLeftImage(int resId) {
		if (resId < 0) {
			btnLeft.setVisibility(View.GONE);
			return;

		}
		btnLeft.setImageResource(resId);
		btnLeft.setVisibility(View.VISIBLE);

	}

	public void setRightImage(int resId) {
		if (resId < 0) {
			btnRight.setVisibility(View.GONE);
			return;

		}
		btnRight.setImageResource(resId);
		btnRight.setVisibility(View.VISIBLE);
	}

	public void hideRightBtn2() {
		btnRight2.setVisibility(View.GONE);
		viewLine.setVisibility(View.GONE);
	}

	public abstract String getNavigationLeftText();

	public abstract boolean isShowRightClose();

	public abstract void onClickRightButton();

	public abstract void onClickLeftButton();

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.btn_left_title:
			onClickLeftButton();
			break;
		case R.id.btn_right_title:

			if (!isShowRightClose()) {
				finish();
			} else {
				onClickRightButton();
			}
			break;
		case R.id.btn_right2_title:
			onClickRight2Btn();
			break;
		default:
			break;
		}
	}

	public void onClickRight2Btn() {

	}

	private Animation mRotaAnimation;

	//
	// protected void onRightBtnRota() {
	// if (mRotaAnimation == null) {
	// mRotaAnimation = AnimationUtils.loadAnimation( this, R.anim.rota360 );
	// }
	// btnRight.setBackgroundResource( R.drawable.selector_loading );
	// mRotaAnimation.setRepeatCount( Integer.MAX_VALUE );
	// btnRight.startAnimation( mRotaAnimation );
	// }

	protected void stopRinghtBtn() {
		btnRight.clearAnimation();
	}

	public void setRight2Background(int resid) {
		if (resid > 0) {
			btnRight2.setBackgroundResource(resid);
			btnRight2.setVisibility(View.VISIBLE);
		} else {
			btnRight2.setVisibility(View.GONE);
		}

	}

	public void setRightTitleString(String rightBtnText) {

		if (rightBtnText == null) {
			btnRight.setVisibility(View.INVISIBLE);
		} else {
			btnRight.setVisibility(View.VISIBLE);
			// btnRight.setText(rightBtnText);
		}
	}

	@Override
	public void setTitle(CharSequence title) {
		tvTitle.setText(title);
	}

	@Override
	public void setTitle(int titleId) {
		tvTitle.setText(titleId);
	}

	public void setTopBtnLeftBackground(int btnLeftBackground) {

		if (btnLeftBackground > 0) {
			btnLeft.setVisibility(View.VISIBLE);
			btnLeft.setBackgroundResource(btnLeftBackground);
		} else {
			btnLeft.setVisibility(View.GONE);
		}
	}

	public void setTopBtnBackground(int btnLeftBackground,
			int btnRightBackground) {

		if (btnLeftBackground > 0) {
			btnLeft.setVisibility(View.VISIBLE);
			btnLeft.setBackgroundResource(btnLeftBackground);
		} else {
			btnLeft.setVisibility(View.GONE);
		}

		if (btnRightBackground > 0) {
			btnRight.setVisibility(View.VISIBLE);
			btnRight.setBackgroundResource(btnRightBackground);
		} else {
			btnRight.setVisibility(View.GONE);
		}
	}

	@Override
	public void setContentView(int layoutResID) {
		setContentView(getLayoutInflater().inflate(layoutResID, null));
	}

	@Override
	public void setContentView(View view) {
		ViewGroup mViewGroup = (ViewGroup) findViewById(R.id.base_layout_mainContent);
		mViewGroup.removeAllViews();
		mViewGroup.addView(view, new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.FILL_PARENT,
				ViewGroup.LayoutParams.FILL_PARENT));

	}

	public void setContentView(View view, LayoutParams params) {
		ViewGroup mViewGroup = (ViewGroup) findViewById(R.id.base_layout_mainContent);
		mViewGroup.removeAllViews();
		mViewGroup.addView(view, params);
	}

	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	//
	// MenuItem item = menu.add( 0, 1, 0, R.string.menu_settings );
	// item.setIcon( R.drawable.menu_szan );
	// item = menu.add( 0, 2, 0, R.string.menu_suggest );
	// Intent intent = new Intent( );
	// intent.setClass( this, SuggestActivity.class );
	// item.setIntent( intent );
	// item.setIcon( R.drawable.menu_yjfk );
	//
	// item = menu.add( 0, 3, 0, R.string.menu_refresh );
	// item.setIcon( R.drawable.menu_sxan );
	//
	// item = menu.add( 1, 4, 0, R.string.menu_chenckupdata );
	// item.setIcon( R.drawable.menu_jcgx );
	//
	// item = menu.add( 2, 5, 0, R.string.menu_usehelp );
	// item.setIcon( R.drawable.menu_gmbz );
	//
	// item = menu.add( 3, 6, 0, R.string.menu_aboutme );
	// item.setIcon( R.drawable.menu_gywm );
	//
	// return super.onCreateOptionsMenu( menu );
	// }

	public abstract void onRefresh();

	// @Override
	// public boolean onMenuItemSelected(int featureId, MenuItem item) {
	// switch (item.getItemId( )) {
	// case 1:
	// Util.gotoSettings( this );
	// break;
	// case 2:
	// Util.gotoSuggest( this );
	// break;
	// case 3:
	// onRefresh( );
	// break;
	// case 4:
	// new CheckUpdataManager( this ).checkAndUpdata( true );
	// break;
	// case 5:
	// Util.gotoBuyHelp( this );
	// break;
	// case 6:
	// Util.gotoAboutMe( this );
	// break;
	//
	// default:
	// break;
	// }
	// return super.onMenuItemSelected( featureId, item );
	// }
	// @Override
	// public View onCreateView(String name, Context context, AttributeSet
	// attrs) {
	//
	// // MyLog.e( "onCreateView", "name=" + name + "attrs=" + attrs );
	// MyLog.e( "onCreateView", attrs );
	// return super.onCreateView( name, context, attrs );
	// }

}
