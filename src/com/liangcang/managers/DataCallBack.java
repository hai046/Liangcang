package com.liangcang.managers;

import java.util.List;

public abstract class DataCallBack<T> {
    public  void success(List<T> list)
    {
        
    }
    public  void success(T t)
    {
        
    }
    public void countPage(int page)
    {
        
    }
    
    public void extraObject(Object ...obj)
    {
        
    }
    public abstract void failure(String msg);
}
