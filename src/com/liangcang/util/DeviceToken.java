package com.liangcang.util;

import java.util.Locale;
import java.util.UUID;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;

import com.liangcang.stores.Settings;

/**
 * 获取设备的唯一标示
 * 
 * @author 邓海柱<br>
 *         E-mail:denghaizhu@brunjoy.com
 */
public class DeviceToken {

    /**
     * ==========================================<BR>
     * 功能：通过 MAC,DeviceID,AndroidID来确定<BR>
     * 时间：2013-1-30 下午4:16:53 <BR>
     * ========================================== <BR>
     * 参数：
     * 
     * @param mContext
     * @return
     */
    public static String getToken(Context mContext) {
        String TAG = "DeviceToken";
        String storeKey = "taose_device_token";
        String token = Settings.getInSettings( mContext ).getString( storeKey, null );
        if (token != null) {
            return token;
        }
        StringBuilder sb = new StringBuilder( );
        WifiManager wifi = (WifiManager) mContext.getSystemService( Context.WIFI_SERVICE );
        if (wifi != null) {
            WifiInfo info = wifi.getConnectionInfo( );
            if (info != null) {
//                MyLog.e( TAG, "getMacAddress=" + info.getMacAddress( ) );
                String mac = info.getMacAddress( );
                if (mac != null) {
                    sb.append( mac.replaceAll( ":", "" ) );
                }

            }
        }

        TelephonyManager telephonyManager = (TelephonyManager) mContext.getSystemService( Context.TELEPHONY_SERVICE );
        if (telephonyManager != null) {
            MyLog.e( TAG, "getDeviceId=" + telephonyManager.getDeviceId( ) );
            sb.append( telephonyManager.getDeviceId( ) );
        }

        String androidId = Secure.getString( mContext.getContentResolver( ), Secure.ANDROID_ID );
        if (androidId != null) {
            MyLog.e( TAG, "androidId=" + androidId );
            sb.append( androidId );
        }
        MyLog.e( TAG, "sb=" + sb.toString( ) );
        if (sb.length( ) < 1) {
            sb.append( UUID.randomUUID( ).toString( ).replaceAll( "-", "" ) );
        } else {
            MyLog.e( TAG, "randomUUID=" + UUID.randomUUID( ).toString( ).replaceAll( "-", "" ) );
        }
        Settings.getInSettings( mContext ).putString( storeKey, sb.toString( ) );
        return sb.toString( ).toUpperCase( Locale.getDefault( ) );
    }
}
