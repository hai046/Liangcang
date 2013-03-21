package com.liangcang.util;

import java.util.ArrayList;

import android.os.Handler;
import android.os.Message;

public class MyHandler extends Handler {

    private MyHandler() {
    }

    private static MyHandler mMyHandler;

    public static MyHandler getInstance() {
        if (mMyHandler == null) {
            mMyHandler = new MyHandler( );
        }
        return mMyHandler;
    }

    @Override
    public void handleMessage(Message msg) {

        for(HandlerCallBack mHandlerCallBack:list)
        {
            mHandlerCallBack.handleMessage( msg );
        }
    }

    private static ArrayList<HandlerCallBack> list = new ArrayList<MyHandler.HandlerCallBack>( );

    public void addListener(HandlerCallBack mHandlerCallBack) {
        list.add( mHandlerCallBack );
    }

    public void removeListener(HandlerCallBack mHandlerCallBack) {
        list.remove( mHandlerCallBack );
    }

    public interface HandlerCallBack {
        public void handleMessage(Message msg);
    }
}
