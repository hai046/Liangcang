package com.liangcang.util;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;

public class Util {

    public static String saveTempFile(String content, Context mContext) {
        if (content == null || content.length( ) < 5) {
            try {
                mContext.getFileStreamPath( "temp_web" ).delete( );
            } catch (Exception e) {
                e.printStackTrace( );
            }
            // MyLog.e( "util", "content=" + content );
            return null;

        }
        try {
            FileOutputStream output = mContext.openFileOutput( "temp_web", Context.MODE_PRIVATE );
            BufferedOutputStream bos = new BufferedOutputStream( output );
            ByteArrayInputStream is = new ByteArrayInputStream( content.getBytes( ) );
            byte buffer[] = new byte[1024];
            int flag = -1;
            while ((flag = is.read( buffer )) != -1) {
                bos.write( buffer, 0, flag );
            }
            bos.flush( );
            bos.close( );
            is.close( );
            output.close( );
            // output.write( content.getBytes( ) );
            content = mContext.getFileStreamPath( "temp_web" ).getPath( );
            // MyLog.d( "Util", "path=" + content );
            return content;
        } catch (FileNotFoundException e) {
            e.printStackTrace( );
        } catch (IOException e) {
            e.printStackTrace( );
        }

        return null;
    }

    public static String getMD5Str(String str) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance( "MD5" );

            messageDigest.reset( );

            messageDigest.update( str.getBytes( "UTF-8" ) );
        } catch (NoSuchAlgorithmException e) {
        } catch (UnsupportedEncodingException e) {
        }

        byte[] byteArray = messageDigest.digest( );

        StringBuffer md5StrBuff = new StringBuffer( );

        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString( 0xFF & byteArray[i] ).length( ) == 1)
                md5StrBuff.append( "0" ).append( Integer.toHexString( 0xFF & byteArray[i] ) );
            else
                md5StrBuff.append( Integer.toHexString( 0xFF & byteArray[i] ) );
        }
        return md5StrBuff.substring( 8, 24 ).toString( ).toUpperCase( Locale.getDefault( ) );
    }



    public static boolean isConnectedNetWork(Context mContext) {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService( Context.CONNECTIVITY_SERVICE );
        NetworkInfo mobileInfo = connectivityManager.getNetworkInfo( ConnectivityManager.TYPE_MOBILE );
        NetworkInfo wifiInfo = connectivityManager.getNetworkInfo( ConnectivityManager.TYPE_WIFI )/* .getState( ) */;
        if (mobileInfo != null && (mobileInfo.getState( ) == State.CONNECTED || mobileInfo.getState( ) == State.CONNECTING)) {
            return true;
        }
        if (wifiInfo != null && (wifiInfo.getState( ) == State.CONNECTED || wifiInfo.getState( ) == State.CONNECTING)) {
            return true;
        }
        return false;
    }

}
