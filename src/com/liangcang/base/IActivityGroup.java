package com.liangcang.base;

import android.app.ActivityGroup;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.liangcang.R;

public abstract class IActivityGroup extends ActivityGroup {

    private Button btnLeft, btnRight,btnRightTwo;
    private TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        requestWindowFeature( Window.FEATURE_NO_TITLE );
        super.setContentView( R.layout.base_layout );

        btnLeft = (Button) findViewById( R.id.btn_left_title );
        btnRight = (Button) findViewById( R.id.btn_right_title );
        btnRightTwo = (Button) findViewById( R.id.btn_right2_title );
        btnLeft.setOnClickListener( mOnClickListener );
        btnRight.setOnClickListener( mOnClickListener );
        btnRightTwo.setOnClickListener(mOnClickListener);
        tvTitle = (TextView) findViewById( R.id.tv_title );
        btnLeft.setBackgroundResource(R.drawable.menu_mzsm);
        setCurrentTitleString( );
    }

    private OnClickListener mOnClickListener = new OnClickListener( ) {

        @Override
        public void onClick(View v) {
            //MyLog.e( "IActivityGroup", "v vvvv=" + v.getId( ) );
            switch (v.getId( )) {
            case R.id.btn_left_title:
                onClickLeftButton( );
                break;
            case R.id.btn_right_title:
                onClickRightButton( );
                break;
            case R.id.btn_right2_title:
            	onClickRightTwoButton();
            	break;
            default:
                break;
            }
        }
    };

    public abstract void onClickLeftButton();

    public abstract void onClickRightButton();
    public  void onClickRightTwoButton()
    {
    	
    }

    /**
     * 可使用 {@link setTitle}设置导航的文字<br>
     * 
     */
    public abstract void setCurrentTitleString();

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
        }
        if (btnRightBackground > 0) {
            btnRight.setVisibility( View.VISIBLE );
            btnRight.setBackgroundResource( btnRightBackground );
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
}
