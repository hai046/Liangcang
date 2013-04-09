package com.liangcang.stores;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class Settings {
    private  String PASSWORD="pass_word";
    private static final String SettingName = "settingsName";
    private SharedPreferences setting;
    private static Settings mSettings;
    
    public void setPassWord(String value)
    {
        
        putString( PASSWORD, value );
       
    }
    
    public String getPassWord()
    {
        return getString( PASSWORD, null );
    }
    public static synchronized Settings getInSettings(Context mContext)
    {
        if(mSettings==null)
            mSettings=new Settings( mContext );
        return mSettings;
    }
    
    public Settings(Context mContext) {
        setting = mContext.getApplicationContext( ).getSharedPreferences( SettingName, 0 );
    }

    public String getString(String key,String defValue) {
        return setting.getString( key, defValue );
    }
    public boolean contains(String key)
    {
        return setting.contains( key );
    }
    public Boolean getBoolean(String key,Boolean defValue) {
        return setting.getBoolean( key, defValue );
    }
    public int getInt(String key,int defValue) {
        return setting.getInt( key, defValue );
    }
    public long getLong(String key,long defValue) {
        return setting.getLong( key, defValue );
    }
    public float getFloat(String key,float defValue) {
        return setting.getFloat( key, defValue );
    }
    

    public void putString(String key, String value) {
        Editor editor = setting.edit( );
        editor.putString( key, value );
        editor.commit( );
    }

    public void putBoolean(String key, boolean value) {
        Editor editor = setting.edit( );
        editor.putBoolean( key, value );
        editor.commit( );
    }

    public void putInt(String key, int value) {
        Editor editor = setting.edit( );
        editor.putInt( key, value );
        editor.commit( );
    }

    public void putFloat(String key, float value) {
        Editor editor = setting.edit( );
        editor.putFloat( key, value );
        editor.commit( );
    }

    public void putLong(String key, long value) {
        Editor editor = setting.edit( );
        editor.putLong( key, value );
        editor.commit( );
    }
    
}
