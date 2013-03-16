package com.liangcang.views;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

public class BaseView {

    private View view;
    public Context mContext;

    public void onRefresh() {

    }

    public void stopRefresh() {

    }

    public void onFailure(String msg) {

    }

    public void onSuccess() {

    }

    /**
     * 请使用{@link #BaseView(Context mContext)}
     */
    @Deprecated
    protected BaseView() {

    }

    public View getView() {
        return view;
    }

    /**
     * 初始化view,代替无参数的构造函数
     * 
     * @param mContext
     */
    public BaseView(Context mContext) {
        this.mContext = mContext;
    }

    public LayoutInflater getLayoutInflater() {
        LayoutInflater mLayoutInflater = LayoutInflater.from( mContext );
        return mLayoutInflater;
    }

    public View findViewById(int id) {
        return view.findViewById( id );
    }

    public void setContentView(int layoutResID) {
        view = getLayoutInflater( ).inflate( layoutResID, null );
    }

    public void setContentView(View view, LayoutParams params) {
        this.view = view;
        view.setLayoutParams( params );
    }

    /**
     * 
     * @param view
     */
    public void setContentView(View view) {
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams( ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT );
        setContentView( view, params );
    }

    public void finish() {

    }

    public void onCreate(Bundle savedInstanceState) {

    }

    public void onDestroy() {
    }

    public void onPause() {
    }

    public void onRestart() {
    }

    public void onResume() {
    }

    public void onStart() {

    }

    public void onStop() {
    }

}
