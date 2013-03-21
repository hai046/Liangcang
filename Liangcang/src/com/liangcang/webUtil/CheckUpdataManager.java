package com.liangcang.webUtil;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Build;

import com.liangcang.R;
import com.liangcang.util.MyToast;

/**
 * 更新管理
 * 
 * @author 邓海柱<br>
 *         E-mail:denghaizhu@brunjoy.com
 */
public class CheckUpdataManager {

    String TAG = "CheckUpdataManager";
    private int currentVersion;
    // private String url = null;
    private ProgressDialog dialog = null;
    private Context mContext;

    public CheckUpdataManager(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * 
     * @param isShow
     *            检查是否显示下载dialog
     */
    public void checkAndUpdata(final boolean isShow) {
        PackageManager pm = mContext.getPackageManager( );
        try {
            PackageInfo pi = pm.getPackageInfo( mContext.getPackageName( ), PackageManager.GET_SIGNATURES );
            // Signature[] signs = pi.signatures;
            // if (signs != null && signs.length > 0) {
            // String yourSign = signs[0].toCharsString( );
            // MyLog.D( "yourSign", "yourSign=" + yourSign );
            // }
            currentVersion = pi.versionCode;
        } catch (NameNotFoundException e) {

        }
//        TaobaokeDataManager.getInstance( mContext ).getServerApkVersion( new TaobaoDataCallBack<APKHolder>( ) {
//            @Override
//            public void failure(String msg) {
//
//                if (dialog != null) {
//                    dialog.cancel( );
//                    dialog = null;
//                }
//            }
//
//            public void success(APKHolder t) {
//
//                if (dialog != null) {
//                    dialog.cancel( );
//                    dialog = null;
//                }
//                // MyLog.D( "test", "resultMSG=" + t );
//
//                if (TextUtils.isEmpty( t.getDown_url( ) )) {
//                    if (isShow)
//                        MyToast.showMsgShort( mContext, "您已经是最新版本了，不需要更新了" );
//                    return;
//                }
//                Settings.getInSettings( mContext ).putInt( "serverversionCode", t.getVersionCode( ) );
//                Settings.getInSettings( mContext ).putInt( "currentversionCode", currentVersion );
//                showDialog( currentVersion < t.getVersionCode( ), t.getContent( ), mContext.getString( R.string.app_name ) + " V" + t.getVersionName( ), t.getDown_url( ), isShow );
//
//            }
//        } );

    }

    private void showDialog(boolean b, String msg, String versionNew, final String url, final boolean isShow) {
        if (b) {
            new AlertDialog.Builder( mContext ).setTitle( "检测到新版本：" + versionNew ).setMessage( msg ).setNegativeButton( R.string.cancel, null )
                    .setPositiveButton( "更新", new DialogInterface.OnClickListener( ) {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            showDownProgressBar( url );
                        }
                    } ).create( ).show( );
        } else {
            if (isShow) {
                MyToast.showMsgShort( mContext, "您已经是最新版本了，不需要更新了" );
            }
        }

    }

    private ProgressDialog mProgressDialog;

    private void showDownProgressBar(String downUrl) {
        mProgressDialog = new ProgressDialog( mContext );
        mProgressDialog.setTitle( "正在为您下载" );
        mProgressDialog.setProgressStyle( ProgressDialog.STYLE_HORIZONTAL );
        mProgressDialog.setMax( 100 );
        mProgressDialog.setIcon( R.drawable.ic_launcher );
        mProgressDialog.show( );

        downFile( downUrl, new ProgressInterface( ) {

            @Override
            public void success(String msg) {
                File file = new File( msg );
                if (file.exists( )) {
                    Intent intent = new Intent( Intent.ACTION_VIEW );
                    intent.setDataAndType( Uri.parse( "file://" + msg ), "application/vnd.android.package-archive" );
                    mContext.startActivity( intent );
                    if (mProgressDialog.isShowing( )) {

                        MyToast.showMsgShort( mContext, "如果本次安装失败，你可以先卸载再安装" );
                        MyToast.showMsgShort( mContext, "文件下载到：" + msg );
                    }
                } else {
                    if (mProgressDialog.isShowing( ))
                        MyToast.showMsgShort( mContext, "文件下载失败，请稍后再试" );
                }
                if (mProgressDialog.isShowing( )) {
                    mProgressDialog.dismiss( );
                }
                mProgressDialog = null;

            }

            @Override
            public void progresss(int progresss) {
                mProgressDialog.setProgress( progresss > 100 ? 100 : progresss );
                // MyLog.d( "progress", "progresss=" + progresss );

            }

            @Override
            public void failure(String failure) {
                mProgressDialog.dismiss( );
                mProgressDialog = null;
                MyToast.showMsgShort( mContext, "文件下载失败，请稍后再试" );
            }
        } );
    }

    private void downFile(String url, ProgressInterface progressInterface) {
        new MyAsynDownFileTask( progressInterface ).execute( url );
    }

    private interface ProgressInterface {
        void success(String msg);

        void failure(String failure);

        void progresss(int progress);
    }

    private class MyAsynDownFileTask extends AsyncTask<String, Integer, String> {
        private ProgressInterface progressInterface;

        MyAsynDownFileTask(ProgressInterface progressInterface) {
            this.progressInterface = progressInterface;

        }

        @Override
        protected void onProgressUpdate(Integer... values) {

            super.onProgressUpdate( values );
            if (progressInterface == null) {
                return;
            }
            progressInterface.progresss( values[0] );
        }

        @SuppressLint("NewApi")
        @Override
        protected String doInBackground(String... params) {
            String url = params[0];
            if (url == null)
                return null;
            HttpGet httpRequest = new HttpGet( url );
            boolean hasAndroidClinet = Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
            final HttpClient httpclient = hasAndroidClinet ? AndroidHttpClient.newInstance( "Android" ) : new DefaultHttpClient( );
            try {
                synchronized (httpclient) {
                    HttpResponse httpResponse = httpclient.execute( httpRequest );
                    // 请求成功
                    if (httpResponse.getStatusLine( ).getStatusCode( ) == HttpStatus.SC_OK) {
                        HttpEntity entity = httpResponse.getEntity( );
                        if (entity != null) {
                            long lg = entity.getContentLength( );
                            lg = lg > 0 ? lg : 3400000;
                            publishProgress( 0 );
                            InputStream is = entity.getContent( );
                            // GZIPInputStream gis = new GZIPInputStream(is);
                            BufferedInputStream bis = new BufferedInputStream( is );
                            File file = new File( "/mnt/sdcard/download/apk/" + url.substring( url.lastIndexOf( '/' ) ) );
                            File fp = file.getParentFile( );
                            if (!fp.exists( )) {
                                fp.mkdirs( );
                            }
                            BufferedOutputStream bos = new BufferedOutputStream( new FileOutputStream( file ) );
                            int temp = -1;
                            long currentLog = 0;
                            byte[] buffer = new byte[8 * 1024];
                            while ((temp = bis.read( buffer )) != -1) {
                                bos.write( buffer, 0, temp );
                                bos.flush( );
                                currentLog += temp;
                                int progress = (int) (((currentLog * 1.0) / lg) * 100f);

                                publishProgress( progress );
                            }
                            bos.close( );
                            bis.close( );
                            is.close( );
                            return file.getPath( );
                        }

                    } else {
                    }
                }
            } catch (ClientProtocolException e) {
                // MyLog.I( TAG, "ClientProtocolException" + e.getMessage( ) );
            } catch (IOException e) {
                // MyLog.I( TAG, "IOException" + e.getMessage( ) );
            } catch (Exception e) {
                // MyLog.I( TAG, "Exception" + e.getMessage( ) );
            } finally {
                if ((httpclient instanceof AndroidHttpClient)) {
                    ((AndroidHttpClient) httpclient).close( );
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            if (progressInterface == null) {
                return;
            }
            if (result != null) {
                progressInterface.success( result );
            } else {
                progressInterface.failure( "" );
            }
        }

    }

}
