package com.liangcang.base;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;

import com.liangcang.R;

public abstract class BaseActivity extends IActivity implements OnClickListener {

    private Button btnLeft, btnRight, btnRight2;
    private TextView tvTitle;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        super.setContentView( R.layout.base_layout );

        btnLeft = (Button) findViewById( R.id.btn_left_title );
        btnRight = (Button) findViewById( R.id.btn_right_title );
        btnRight2 = (Button) findViewById( R.id.btn_right2_title );
        btnRight2.setOnClickListener( this );
        btnLeft.setOnClickListener( this );
        btnRight.setOnClickListener( this );
        tvTitle = (TextView) findViewById( R.id.tv_title );

        setCurrentTitleString( );
    }


    public abstract boolean onClickLeftButton();

    public abstract void onClickRightButton();


    /**
     * 可使�?{@link setTitle}设置导航的文�?br>
     * 
     */
    public abstract void setCurrentTitleString();

    @Override
    public void onClick(View v) {

        switch (v.getId( )) {
        case R.id.btn_left_title:
            if (!onClickLeftButton( )) {
                finish( );
            }
            break;
        case R.id.btn_right_title:
            onClickRightButton( );
            break;
        case R.id.btn_right2_title:
            onClickRight2Btn( );
            break;
        default:
            break;
        }
    }

    public void onClickRight2Btn() {

    }

    private Animation mRotaAnimation;
//
//    protected void onRightBtnRota() {
//        if (mRotaAnimation == null) {
//            mRotaAnimation = AnimationUtils.loadAnimation( this, R.anim.rota360 );
//        }
//        btnRight.setBackgroundResource( R.drawable.selector_loading );
//        mRotaAnimation.setRepeatCount( Integer.MAX_VALUE );
//        btnRight.startAnimation( mRotaAnimation );
//    }

    protected void stopRinghtBtn() {
        btnRight.clearAnimation( );
    }

    public void setCurrentTitleString(String leftBtnText, String centerText, String rightBtnText) {
        if (leftBtnText == null) {
            btnLeft.setVisibility( View.INVISIBLE );
        } else {
            btnLeft.setVisibility( View.VISIBLE );

        }
        btnLeft.setText( leftBtnText );
        tvTitle.setText( centerText );
        btnRight.setText( rightBtnText );
        if (rightBtnText == null) {
            btnRight.setVisibility( View.INVISIBLE );
        } else {
            btnRight.setVisibility( View.VISIBLE );

        }
    }

    public void setRight2Background(int resid) {
        if (resid > 0) {
            btnRight2.setBackgroundResource( resid );
            btnRight2.setVisibility( View.VISIBLE );
        } else {
            btnRight2.setVisibility( View.GONE );
        }

    }

    public void setRightTitleString(String rightBtnText) {

        if (rightBtnText == null) {
            btnRight.setVisibility( View.INVISIBLE );
        } else {
            btnRight.setVisibility( View.VISIBLE );
            btnRight.setText( rightBtnText );
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        tvTitle.setText( title );
    }

    @Override
    public void setTitle(int titleId) {
        tvTitle.setText( titleId );
    }

    public void setTopBtnBackground(int btnLeftBackground, int btnRightBackground) {

        if (btnLeftBackground > 0) {
            btnLeft.setVisibility( View.VISIBLE );
            btnLeft.setBackgroundResource( btnLeftBackground );
        } else {
            btnLeft.setVisibility( View.GONE );
        }

        if (btnRightBackground > 0) {
            btnRight.setVisibility( View.VISIBLE );
            btnRight.setBackgroundResource( btnRightBackground );
        } else {
            btnRight.setVisibility( View.GONE );
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        setContentView( getLayoutInflater( ).inflate( layoutResID, null ) );
    }

    @Override
    public void setContentView(View view) {
        ViewGroup mViewGroup = (ViewGroup) findViewById( R.id.base_layout_mainContent );
        mViewGroup.removeAllViews( );
        mViewGroup.addView( view );

    }

    public void setContentView(View view, LayoutParams params) {
        ViewGroup mViewGroup = (ViewGroup) findViewById( R.id.base_layout_mainContent );
        mViewGroup.removeAllViews( );
        mViewGroup.addView( view, params );
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//        MenuItem item = menu.add( 0, 1, 0, R.string.menu_settings );
//        item.setIcon( R.drawable.menu_szan );
//        item = menu.add( 0, 2, 0, R.string.menu_suggest );
//        Intent intent = new Intent( );
//        intent.setClass( this, SuggestActivity.class );
//        item.setIntent( intent );
//        item.setIcon( R.drawable.menu_yjfk );
//
//        item = menu.add( 0, 3, 0, R.string.menu_refresh );
//        item.setIcon( R.drawable.menu_sxan );
//
//        item = menu.add( 1, 4, 0, R.string.menu_chenckupdata );
//        item.setIcon( R.drawable.menu_jcgx );
//
//        item = menu.add( 2, 5, 0, R.string.menu_usehelp );
//        item.setIcon( R.drawable.menu_gmbz );
//
//        item = menu.add( 3, 6, 0, R.string.menu_aboutme );
//        item.setIcon( R.drawable.menu_gywm );
//
//        return super.onCreateOptionsMenu( menu );
//    }

    public abstract void onRefresh();

//    @Override
//    public boolean onMenuItemSelected(int featureId, MenuItem item) {
//        switch (item.getItemId( )) {
//        case 1:
//            Util.gotoSettings( this );
//            break;
//        case 2:
//            Util.gotoSuggest( this );
//            break;
//        case 3:
//            onRefresh( );
//            break;
//        case 4:
//            new CheckUpdataManager( this ).checkAndUpdata( true );
//            break;
//        case 5:
//            Util.gotoBuyHelp( this );
//            break;
//        case 6:
//            Util.gotoAboutMe( this );
//            break;
//
//        default:
//            break;
//        }
//        return super.onMenuItemSelected( featureId, item );
//    }
//    @Override
//    public View onCreateView(String name, Context context, AttributeSet attrs) {
//
////        MyLog.e( "onCreateView", "name=" + name + "attrs=" + attrs );
//        MyLog.e( "onCreateView", attrs );
//        return super.onCreateView( name, context, attrs );
//    }

}
