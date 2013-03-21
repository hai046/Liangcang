package com.liangcang.base;

import android.os.Bundle;
import android.view.Window;

import com.actionbarsherlock.app.SherlockActivity;
import com.liangcang.R;

public class IActivity extends SherlockActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	setTheme(R.style.Theme_Sherlock_Light_DarkActionBar);
        super.onCreate( savedInstanceState );
        requestWindowFeature( Window.FEATURE_NO_TITLE );
    }
    

}
