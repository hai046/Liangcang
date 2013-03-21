package com.liangcang;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;

import com.liangcang.base.IActivity;
import com.umeng.analytics.MobclickAgent;

public class LoadingActivity extends IActivity {
    private ImageView imgView;
    String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        getWindow( ).setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );
        setContentView( R.layout.loading_layout );
        imgView = (ImageView) findViewById( R.id.loading_background );
        com.umeng.common.Log.LOG = false;
        MobclickAgent.setDebugMode( true );
       
    }

    private void gotoMain() {
        Intent intent = new Intent( );
        intent.setClass( LoadingActivity.this, MainActivity.class );
        startActivity( intent );
        finish( );

    }

    private void gotoGuide() {
        Intent intent = new Intent( );
//        intent.setClass( LoadingActivity.this, GuideActivity.class );
        startActivity( intent );
        finish( );

    }

    @Override
    protected void onResume() {
        MobclickAgent.onResume( this );
        // MobclickAgent.setDebugMode( false );

        super.onResume( );
    }

    @Override
    protected void onPause() {
        MobclickAgent.onPause( this );
        super.onPause( );
    }
}
