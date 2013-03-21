package com.liangcang.weigets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Gallery;

public class MyGallery extends Gallery {

    public MyGallery(Context context, AttributeSet attrs) {
        super( context, attrs );
       
    }

    public MyGallery(Context context, AttributeSet attrs, int defStyle) {
        super( context, attrs, defStyle );
    }

    public MyGallery(Context context) {
        super( context );
    }
   

    @Override
    public void setSelection(int position) {
        
        super.setSelection( position );
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

        // if (velocityX > 800) {
        // velocityX = 800;
        // } else if (velocityX < -800) {
        // velocityX = -800;
        // }
        return super.onFling( e1, e2, (float) (velocityX * 0.1), (float) (velocityY * 0.1) );
    }

}
