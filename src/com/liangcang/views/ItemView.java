package com.liangcang.views;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;

import com.liangcang.R;
import com.liangcang.mode.TopicHolder;
import com.liangcang.util.ImageDownloader;
import com.liangcang.util.MyHandler;
import com.liangcang.util.MyHandler.HandlerCallBack;
import com.liangcang.util.RichText;
import com.liangcang.weigets.MyGallery;

public class ItemView extends BaseView {

	public ItemView(Context mContext) {
		super(mContext);
		View viewTop=getLayoutInflater().inflate(R.layout.recomment_layout, null);
		
		
	}
	
	   @Override
	    public void onResume() {

	        if (list.size( ) > 0) {
	            MyHandler.getInstance( ).removeMessages( SCROLLER );
	            MyHandler.getInstance( ).sendEmptyMessageDelayed( SCROLLER, 4000 );
	        }
	        super.onResume( );
	    }

	    @Override
	    public void onPause() {
	        MyHandler.getInstance( ).removeMessages( SCROLLER );
	        super.onPause( );
	    }

	    private TextView tvProgress=null;
	    private MyGallery mGallery;
	    private BaseAdapter galleryAdapter;
	    private boolean isGalleryClear;
	    private List<TopicHolder> list = new ArrayList<TopicHolder>( );

	    public void setDatas(List<TopicHolder> list) {
	        if (list != null) {
	            if (isGalleryClear) {
	                this.list.clear( );
	                isGalleryClear = false;
	            }
	            this.list.addAll( list );
	            if (galleryAdapter == null) {
	                galleryAdapter = new BaseAdapter( ) {

	                    private Gallery.LayoutParams params = new Gallery.LayoutParams( Gallery.LayoutParams.FILL_PARENT, Gallery.LayoutParams.FILL_PARENT );

	                    public View bindView(int position, String t, View view) {
	                        ImageView image;
	                        if (view == null) {
	                            image = new ImageView( mContext );
	                            image.setScaleType( ImageView.ScaleType.CENTER_CROP );
	                            image.setLayoutParams( params );
	                        } else {
	                            image = (ImageView) view;
	                        }
	                        ImageDownloader.getInstance( ).download( t, image );
	                        // MyLog.e( TAG, "Gallery getView" + position );
	                        return image;
	                    }

	                    @Override
	                    public View getView(int position, View convertView, ViewGroup parent) {
	                        if (ItemView.this.list.size( ) > 0) {
	                            position = position % ItemView.this.list.size( );
	                            return bindView( position, ItemView.this.list.get( position ).getPic( ), convertView );
	                        } else {
	                            return null;
	                        }

	                    }

	                    public int getCount() {
	                        // TODO　　内网测试去掉循环
	                        return ItemView.this.list.size( );
	                    }

	                    @Override
	                    public Object getItem(int position) {
	                        return null;
	                    }

	                    @Override
	                    public long getItemId(int position) {
	                        return position;
	                    }
	                };
	                mGallery.setAdapter( galleryAdapter );
	            } else {
	                galleryAdapter.notifyDataSetChanged( );
	            }
	            mGallery.setFadingEdgeLength( 0 );
	            mGallery.setSelection( 0/* this.list.size( ) * 500 */);
	            onPageSelected( 0 );
	            MyHandler.getInstance( ).removeMessages( SCROLLER );
	            MyHandler.getInstance( ).sendEmptyMessageDelayed( SCROLLER, 4000 );
	        } else {
	            return;
	        }

	    }

	    private static final int SCROLLER = 1215;

	    private HandlerCallBack mHandlerCallBack = new HandlerCallBack( ) {

	        @Override
	        public void handleMessage(Message msg) {

	            switch (msg.what) {

	            case SCROLLER:
	                int size = list.size( );
	                if (size > 0) {
	                    int current = mGallery.getSelectedItemPosition( ) + 1;
	                    onPageSelected( (current) % size );
	                    mGallery.setSelection( (current) % size );
	                }
	                break;
	            default:
	                break;
	            }

	        }
	    };

	    @Override
	    public void onDestroy() {
	        MyHandler.getInstance( ).removeMessages( SCROLLER );
	        MyHandler.getInstance( ).removeListener( mHandlerCallBack );
	        super.onDestroy( );
	    }

	    RichText rt = new RichText( mContext );

	    public void onPageSelected(int position) {
	        rt.clear( );
	        if (list.size( ) == 0)
	            return;
	        position = position % list.size( );
	        for (int i = 0; i < list.size( ); i++) {
	            rt.addText( " " );
	            if (position % list.size( ) == i) {
	                // rt.addText( " . ", Color.WHITE, Typeface.BOLD );
	                rt.addImage( BitmapFactory.decodeResource( mContext.getResources( ), R.drawable.point2 ), 14, 14 );

	            } else {
	                // rt.addText( " . ", Color.GRAY, Typeface.BOLD );
	                rt.addImage( BitmapFactory.decodeResource( mContext.getResources( ), R.drawable.point1 ), 14, 14 );
	            }
	            rt.addText( " " );
	        }

	        this.tvProgress.setText( rt );
	        MyHandler.getInstance( ).removeMessages( SCROLLER );
	        MyHandler.getInstance( ).sendEmptyMessageDelayed( SCROLLER, 4000 );
	    }

}
