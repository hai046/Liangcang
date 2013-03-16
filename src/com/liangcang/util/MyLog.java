package com.liangcang.util;

import android.util.AttributeSet;
import android.util.Log;

/**
 * log管理
 * 
 * @author 邓海柱<br>
 *         E-mail:denghaizhu@brunjoy.com
 */
public class MyLog {
    public static boolean isDEBUG = true;

    public static void E(String tag, String msg) {
        if (tag == null || msg == null) {
            return;
        }
        if (isDEBUG)
            Log.e( tag, msg );
    }

    public static void D(String tag, String msg) {
        if (tag == null || msg == null) {
            return;
        }
        if (isDEBUG)
            Log.d( tag, msg );
    }

    public static void I(String tag, String msg) {
        if (tag == null || msg == null) {
            return;
        }
        if (isDEBUG)
            Log.i( tag, msg );
    }

    public static void W(String tag, String msg) {
        if (tag == null || msg == null) {
            return;
        }
        if (isDEBUG)
            Log.w( tag, msg );
    }

    public static void V(String tag, String msg) {
        if (tag == null || msg == null) {
            return;
        }
        if (isDEBUG)
            Log.v( tag, msg );

    }

    public static void e(String tag, String msg) {
        if (tag == null || msg == null) {
            return;
        }
        if (isDEBUG)
            Log.e( tag, msg );
    }

    public static void d(String tag, String msg) {
        if (tag == null || msg == null) {
            return;
        }
        if (isDEBUG)
            Log.d( tag, msg );
    }

    public static void d(String tag, Object... msg) {
        if (tag == null || msg == null) {
            return;
        }

        if (isDEBUG) {
            String str = "";
            for (int i = 0; i < msg.length; i++) {
                str += msg[i];
            }
            Log.d( tag, str );
        }
    }

    public static void i(String tag, String msg) {
        if (tag == null || msg == null) {
            return;
        }
        if (isDEBUG)
            Log.i( tag, msg );
    }

    public static void w(String tag, String msg) {
        if (tag == null || msg == null) {
            return;
        }
        if (isDEBUG)
            Log.w( tag, msg );
    }

    public static void v(String tag, String msg) {
        if (tag == null || msg == null) {
            return;
        }
        if (isDEBUG)
            Log.v( tag, msg );

    }

    public static void e(String tag, AttributeSet attrs) {
        if (!isDEBUG) {
            return;
        }
        Log.e( tag, "=====================================" + attrs.getIdAttribute( ) );
        for (int i = 0; i < attrs.getAttributeCount( ); i++) {
            if ("background".equalsIgnoreCase( attrs.getAttributeName( i ) )) {
                int resId=attrs.getAttributeResourceValue( i, 0 ) ;
                
                Log.i( tag, "getAttributeResourceValue=" + attrs.getAttributeResourceValue( i, 0 ) );
            }
            Log.e( tag, "getAttributeName=" + attrs.getAttributeName( i ) + "  getAttributeValue=" + attrs.getAttributeValue( i ) );
        }
        Log.e( tag, "=====================================" );
    }

}
