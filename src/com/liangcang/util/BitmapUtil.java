package com.liangcang.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Environment;

/**
 * 图片处理以及管理
 * 
 * @author 邓海柱<br>
 *         E-mail:denghaizhu@brunjoy.com
 */
public class BitmapUtil {
    final static String dirName = "Taose";
    final static String TAG = "BitmapUtil";
    public static String rootCacheAppSavaPath = Environment.getExternalStorageDirectory( ).getPath( ) + "/." + dirName;
    final static String rootSavePath = Environment.getExternalStorageDirectory( ).getPath( ) + "/." + dirName + "/images";
    final static String rootSavePhotoPath = Environment.getExternalStorageDirectory( ).getPath( ) + "/." + dirName + "/photo";
    final static String rootSaveMyImagePath = Environment.getExternalStorageDirectory( ).getPath( ) + "/" + dirName + "/userSavePhoto";

    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable == null || drawable.getIntrinsicWidth( ) < 0 || drawable.getIntrinsicHeight( ) < 0)
            return null;
        Bitmap bitmap = null;
        try {
            bitmap = Bitmap.createBitmap( drawable.getIntrinsicWidth( ), drawable.getIntrinsicHeight( ), drawable.getOpacity( ) != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                    : Bitmap.Config.RGB_565 );
        } catch (OutOfMemoryError e) {
            return null;
        }
        Canvas canvas = new Canvas( bitmap );
        // canvas.setBitmap(bitmap);
        drawable.setBounds( 0, 0, drawable.getIntrinsicWidth( ), drawable.getIntrinsicHeight( ) );
        drawable.draw( canvas );
        return bitmap;
    }

    /**
     * 
     * @param bitmap
     * @param width
     * @param height
     * @return
     */
    public static Bitmap formatBitmap(Bitmap bitmap, int width, int height) {

        int wh = Math.max( bitmap.getWidth( ), bitmap.getHeight( ) );
        Bitmap mBitmap = Bitmap.createBitmap( wh, wh, Bitmap.Config.ARGB_8888 );
        Canvas canvas = new Canvas( mBitmap );
        canvas.drawBitmap( bitmap, -(bitmap.getWidth( ) - wh) / 2, -(bitmap.getHeight( ) - wh) / 2, null );
        bitmap = Bitmap.createScaledBitmap( mBitmap, width, height, true );
        return bitmap;
    }

    // 放大缩小图片
    public static Bitmap zoomBitmap(Bitmap bitmap, int w, int h) {
        int width = bitmap.getWidth( );
        int height = bitmap.getHeight( );
        Matrix matrix = new Matrix( );
        float scaleWidht = ((float) w / width);
        float scaleHeight = ((float) h / height);
        matrix.postScale( scaleWidht, scaleHeight );
        Bitmap newbmp = Bitmap.createBitmap( bitmap, 0, 0, width, height, matrix, true );
        return newbmp;
    }

    public static Bitmap formatBitmap(Bitmap bitmap, int size) {

        float width = bitmap.getWidth( );
        float height = bitmap.getHeight( );
        float rota = size / height;

        int widthnew = (int) ((size * width) / height);
        // int wh = Math.max(bitmap.getWidth(), bitmap.getHeight());
        Bitmap mBitmap = Bitmap.createBitmap( widthnew, size, Bitmap.Config.ARGB_8888 );
        Matrix matrix = new Matrix( );
        matrix.postScale( rota, rota );
        Canvas canvas = new Canvas( mBitmap );
        canvas.drawBitmap( bitmap, matrix, null );

        // bitmap = Bitmap.createScaledBitmap(mBitmap, width, height, true);
        return mBitmap;
    }

    public static Bitmap formatScaleBitmap(Bitmap bitmap, int width, int height) {

        int bmpWidth = bitmap.getWidth( );

        int bmpHeight = bitmap.getHeight( );

        float scaleHeight = (float) height / bmpHeight; //

        // float scaleWidth = (float) width / bmpWidth;
        Matrix matrix = new Matrix( );

        matrix.postScale( scaleHeight, scaleHeight );

        Bitmap resizeBitmap = Bitmap.createBitmap(

        bitmap, 0, 0, bmpWidth, bmpHeight, matrix, true );

        bitmap.recycle( );

        return resizeBitmap;
    }

    public static Bitmap getBitmap(String fileName) {
        if (fileName == null)
            return null;

        File file = new File( getFileName( fileName ) );
        // //MyLog.D(TAG, file.getPath());
        if (file.exists( )) {
            //
            // //MyLog.D(TAG, "file exites");
            try {
                return BitmapFactory.decodeFile( file.getPath( ) );
            } catch (OutOfMemoryError e) {
                // MyLog.E( TAG, "OutOfMemoryError  " + e.getMessage( ) + "   " + file.getPath( ) );
            }

        }
        // //MyLog.D(TAG, "file!!!!!!!! exites");
        return null;
    }

    public static void deleteBitmap(String fileName) {
        String status = Environment.getExternalStorageState( );
        if (!status.equals( Environment.MEDIA_MOUNTED )) {// 判断是否有SD�?
            return;
        }

        File file = new File( getFileName( fileName ) );
        if (file.exists( ))
            file.delete( );
    }

    /**
     * 
     * @param mBitmap
     *            要保存的bitmap对象
     * @param fileName
     *            要保存到 /mnt/sdcard/DuanLuo/images 里面的文件名，但不包括后�?��
     * @return
     */
    public static boolean savaBitmap(Bitmap mBitmap, String fileName) {

        return savaBitmap( mBitmap, fileName, Bitmap.CompressFormat.PNG );
    }

    private static String getFormat(Bitmap.CompressFormat format) {
        if (format == Bitmap.CompressFormat.JPEG) {
            return ".jpg";
        } else {
            return ".png";
        }
    }

    public static boolean savaBitmap(Bitmap mBitmap, String fileName, Bitmap.CompressFormat format) {
        if (mBitmap == null) {
            return false;
        }
        String status = Environment.getExternalStorageState( );
        if (!status.equals( Environment.MEDIA_MOUNTED )) {// 判断是否有SD卡
            return false;
        }
        File file = new File( getFileName( fileName, format ) );
        if (file.exists( )) {
            try {
                file.delete( );
                file.createNewFile( );
            } catch (IOException e) {
                return false;
            }
        } else {
            File parentFile = file.getParentFile( );
            parentFile.mkdirs( );
            try {
                file.createNewFile( );
            } catch (IOException e) {
                return false;
            }

        }
        try {
            FileOutputStream fos = new FileOutputStream( file );
            mBitmap.compress( format, 100, fos );
            fos.flush( );
            fos.close( );
        } catch (FileNotFoundException e) {
            // e.printStackTrace();
            return false;
        } catch (IOException e) {
            // e.printStackTrace();
            return false;
        }
        return true;
    }

    public static String getFileName(String fileName) {

        fileName = getSonFileName( fileName );
        fileName = rootSavePath + "/" + fileName + ".png";
        return fileName;
    }

    public static String getFileName(String fileName, Bitmap.CompressFormat format) {

        fileName = getSonFileName( fileName );
        fileName = rootSavePath + "/" + fileName + getFormat( format );
        return fileName;
    }

    private static String getSonFileName(String fileName) {
        return getMD5Str( fileName );
    }

    private static String getMD5Str(String str) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance( "MD5" );

            messageDigest.reset( );

            messageDigest.update( str.getBytes( "UTF-8" ) );
        } catch (NoSuchAlgorithmException e) {
            System.out.println( "NoSuchAlgorithmException caught!" );
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

    public static String getPhotoName(String fileName) {
        fileName = getSonFileName( fileName );
        fileName = rootSavePhotoPath + "/" + fileName + ".png";
        return fileName;
    }

    public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap( bitmap.getWidth( ), bitmap.getHeight( ), Config.ARGB_8888 );
        Canvas canvas = new Canvas( output );
        final int color = 0xffffffff;
        final Paint paint = new Paint( );
        final Rect rect = new Rect( 0, 0, bitmap.getWidth( ), bitmap.getHeight( ) );
        final RectF rectF = new RectF( rect );
        final float roundPx = pixels;
        paint.setAntiAlias( true );
        canvas.drawARGB( 0, 255, 255, 255 );
        paint.setColor( color );
        canvas.drawRoundRect( rectF, roundPx, roundPx, paint );
        paint.setXfermode( new PorterDuffXfermode( PorterDuff.Mode.SRC_IN ) );
        canvas.drawBitmap( bitmap, rect, rect, paint );
        bitmap.recycle( );
        return output;
    }

   


    /**
     * 压缩文件
     * 
     * @param filePath
     */
    public static String compressPhotoFile(String filePath) {
        BitmapFactory.Options opts = new BitmapFactory.Options( );
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile( filePath, opts );
        opts.inSampleSize = BitmapUtil.computeSampleSize( opts, -1, 640 * 640 );
        opts.inJustDecodeBounds = false;
        try {
            Bitmap bmp = BitmapFactory.decodeFile( filePath, opts );

            if (savaBitmap( bmp, filePath )) {
                bmp.recycle( );
                return getFileName( filePath );
            }

        } catch (OutOfMemoryError err) {

        }
        return null;
    }

    public static Bitmap compressPhotoFileToBitmap(String filePath) {
        BitmapFactory.Options opts = new BitmapFactory.Options( );
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile( filePath, opts );
        //压缩到640x640
        opts.inSampleSize = BitmapUtil.computeSampleSize( opts, -1, 640 * 640 );
        opts.inJustDecodeBounds = false;
        try {
            return BitmapFactory.decodeFile( filePath, opts );
        } catch (OutOfMemoryError err) {
            // MyLog.D( TAG, err.getMessage( ) );
        }
        return null;
    }
    public static int computeSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize( options, minSideLength, maxNumOfPixels );

        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }

        return roundedSize;
    }

    public static int computeInitialSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;
        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil( Math.sqrt( w * h / maxNumOfPixels ) );
        int upperBound = (minSideLength == -1) ? 128 : (int) Math.min( Math.floor( w / minSideLength ), Math.floor( h / minSideLength ) );
        if (upperBound < lowerBound) {
            // return the larger one when there is no overlapping zone.
            return lowerBound;
        }
        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }
    /**
     * ==========================================<BR>
     * 功能： 删除一天前的缓存 <BR>
     * 时间：2013-1-31 下午3:05:57 <BR>
     * ========================================== <BR>
     * 参数：
     */
    public static void clearLoacalCache() {

        new Thread( new Runnable( ) {

            @Override
            public void run() {
                long start = System.currentTimeMillis( );
                File file = new File( rootSavePath );
                if (!file.exists( )) {
                    return;
                }
                long duration = 24 * 60 * 60 * 1000;// 图片保存时间
                for (File f : file.listFiles( )) {
                    if ((start - f.lastModified( )) > duration) {
                        // MyLog.D( TAG, "删除" + f.getPath( ) );
                        f.delete( );
                    } else {
                        // MyLog.D( TAG, "保留" + f.getPath( ) );
                    }
                }
                // MyLog.D( TAG, "耗时 " + (System.currentTimeMillis( ) - start) );
            }
        } ).start( );

    }

    /**
     * ==========================================<BR>
     * 功能：输出所有缓存 <BR>
     * 时间：2013-1-31 下午3:06:25 <BR>
     * ========================================== <BR>
     * 参数：
     */
    public static void clearAllLoacalCache() {

        new Thread( new Runnable( ) {

            @Override
            public void run() {
//                long start = System.currentTimeMillis( );
                File file = new File( rootSavePath );
                if (!file.exists( )) {
                    return;
                }
                for (File f : file.listFiles( )) {
                    f.delete( );
                }
                // MyLog.D( TAG, "耗时 " + (System.currentTimeMillis( ) - start) );
            }
        } ).start( );

    }

    public static String saveUserName(Bitmap mBitmap) {
        if (mBitmap == null) {
            return null;
        }
        String status = Environment.getExternalStorageState( );
        if (!status.equals( Environment.MEDIA_MOUNTED )) {// 判断是否有SD�?
            return null;
        }

        String fileName = System.currentTimeMillis( ) + "";
        fileName = rootSaveMyImagePath + "/" + fileName + ".png";
        File file = new File( fileName );
        if (!file.exists( )) {
            File parentFile = file.getParentFile( );
            parentFile.mkdirs( );
            try {
                file.createNewFile( );
            } catch (IOException e) {
                return null;
            }

        }
        try {
            FileOutputStream fos = new FileOutputStream( file );
            mBitmap.compress( Bitmap.CompressFormat.PNG, 100, fos );
            fos.flush( );
            fos.close( );
            return file.getPath( );
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            return null;
        }
    }

    public static String savaBitmap(InputStream inputStream, String url) {
        String status = Environment.getExternalStorageState( );
        if (!status.equals( Environment.MEDIA_MOUNTED )) {// 判断是否有SD
            return null;
        }
        File file = new File( getFileName( url, Bitmap.CompressFormat.PNG ) );
        if (!file.exists( )) {
            if (file.getParentFile( ).exists( ) == false) {
                file.getParentFile( ).mkdirs( );
            }
        } else {
            file.delete( );
        }
        try {
            FileOutputStream fos = new FileOutputStream( file );
            BufferedOutputStream bos = new BufferedOutputStream( fos );
            BufferedInputStream bis = new BufferedInputStream( inputStream );
            byte buffer[] = new byte[8 * 1024];
            int lg = -1;
            while ((lg = bis.read( buffer )) != -1) {
                bos.write( buffer, 0, lg );
                bos.flush( );
            }
            bos.close( );
            bis.close( );
            fos.close( );

        } catch (FileNotFoundException e) {

            e.printStackTrace( );
            return null;
        } catch (IOException e) {
            e.printStackTrace( );
            return null;
        }
        return file.getPath( );

    }

    public static boolean canStore() {
        // TODO Auto-generated method stub
        return false;
    }
}
