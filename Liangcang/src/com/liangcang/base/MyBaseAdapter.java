package com.liangcang.base;

import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class MyBaseAdapter<T> extends BaseAdapter {

    private List<T> list = new ArrayList<T>( );

    public List<T> getDateSet() {
        return list;
    }

    public void add(T t) {
        if (t != null) {
            if (isClear) {
                this.list.clear( );
                isClear = false;
            }
            list.add( t );
        }
    }

    /**
     * 添加集合,并未调用notifyDataSetChanged
     * 
     * @param list
     */
    public void addAll(List<T> list) {
        if (list != null) {
            if (isClear) {
                this.list.clear( );
                isClear = false;
            }
            this.list.addAll( list );
        }
    }

    boolean isClear = false;

    public void clear() {
        isClear = true;
    }

    public void clearNow() {
        list.clear( );
    }

    @Override
    public int getCount() {
        return list.size( );
    }

    @Override
    public T getItem(int position) {
        return list.get( position );
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        return bindView( position, list.get( position ), convertView );
    }

    /**
     * 实现对应的position上的view
     * 
     * @param position
     * @param t
     * @param convertView
     * @return
     */
    public abstract View bindView(int position, T t, View view);

    public boolean removeAt(int index) {
        if (list.size( ) > index && index >= 0) {
            list.remove( index );
            return true;
        }
        return false;

    }

}
