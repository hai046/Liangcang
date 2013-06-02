package com.liangcang;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebBackForwardList;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.liangcang.base.BaseActivity;
import com.liangcang.util.MyLog;
import com.umeng.analytics.MobclickAgent;

/**
 * 设置传递 value 的key
 */
public class BuyWebActivity extends BaseActivity {

	/**
	 * 设置传递 value 的key
	 */
	public static final String PATH = "path";
	String TAG = "BuyWebActivity";
	private WebView mWebView;
	private ProgressBar mProgressBar;
	// private TextView tvUrl;
	private int firstStep = 0;
	private boolean isFrist = true;
	private String firstUrl = "####";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.web_buy);
		mWebView = (WebView) findViewById(R.id.webViewBuy);
		// tvUrl = (TextView) findViewById( R.id.webUrl );
		mProgressBar = (ProgressBar) findViewById(R.id.progressWebLoad);
		String path = getIntent().getStringExtra(PATH);
		// MyLog.i( TAG, "get web path=" + path );
		if (path == null) {
			finish();
			return;
		}
		Toast.makeText(this, "正在跳转到淘宝手机页面，淘宝认证，请放心购买！", Toast.LENGTH_LONG)
				.show();
		WebSettings settings = mWebView.getSettings();
		settings.setPluginsEnabled(true);
		settings.setSupportZoom(true);
		settings.setJavaScriptEnabled(true);
		settings.setBuiltInZoomControls(true);
		// settings.setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);

		mWebView.setWebViewClient(new WebViewClient() {

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				mWebView.loadUrl(url);
				WebBackForwardList mWebBackForwardList = mWebView
						.copyBackForwardList();
				if (MyLog.isDEBUG) {
					MyLog.d(TAG,
							" getCurrentIndex ="
									+ mWebBackForwardList.getCurrentIndex());

					for (int i = 0; i < mWebBackForwardList.getSize(); i++) {
						MyLog.d(TAG, i
								+ "=index, item="
								+ mWebBackForwardList.getItemAtIndex(i)
										.getUrl());
					}
					MyLog.d(TAG, "shouldOverrideUrlLoading=" + url);
					MyLog.i(TAG, " firstStep =" + firstStep);
				}
				mProgressBar.setVisibility(View.VISIBLE);
				// tvUrl.setText( url );

				if (isFrist) {
					firstStep++;
				}

				if (url.contains("http://a.m.taobao.com")) {
					isFrist = false;
					MyLog.e(TAG, " firstStep =" + firstStep);
					firstUrl = url;
				}
				return true;
			}

			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				MyLog.e(TAG, "failingurl = " + failingUrl);
				if (failingUrl.contains("#")) {
					String[] temp;
					temp = failingUrl.split("#");
					view.loadUrl(temp[0]); // load page without internal
					try {
						Thread.sleep(400);
					} catch (InterruptedException e) {

						e.printStackTrace();
					}
					view.goBack();
					view.goBack();
				}
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				mProgressBar.setVisibility(View.GONE);

			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				mProgressBar.setVisibility(View.VISIBLE);

			}

		});
		mWebView.loadUrl(path + "&TTID=400000_21267618@TaoSe_Android_1.0");
		mWebView.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				mProgressBar.setProgress(newProgress);
				if (newProgress > 90) {

				}
				// MyLog.d( TAG, "newProgress=" + newProgress );
			}

			public void onReceivedTitle(WebView view, String title) {
				setTitle(title);
			}

		});
		MobclickAgent.onEvent(this, "buy_item", path);
	}

	@Override
	protected void onResume() {
		MobclickAgent.onResume(this);
		super.onResume();
	}

	@Override
	protected void onPause() {
		MobclickAgent.onPause(this);
		super.onPause();
	}

	@Override
	protected void onDestroy() {

		/*
		 * fix bug java.lang.IllegalArgumentException: Receiver not registered:
		 * android.widget.ZoomButtonsController$1@43907880
		 * http://blog.csdn.net/a345017062/article/details/6838449
		 * WebView中包含一个ZoomButtonsController
		 * ，当使用web.getSettings().setBuiltInZoomControls
		 * (true);启用后，用户一旦触摸屏幕，就会出现缩放控制图标
		 * 。这个图标过上几秒会自动消失，但在3.X系统上，如果图标自动消失前退出当前Activity的话，就会报上面的这些异常。
		 */
		mWebView.setVisibility(View.GONE);
		mWebView.clearHistory();
		mWebView.clearFormData();
		mWebView.destroyDrawingCache();
		mWebView.destroy();

		super.onDestroy();
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getAction() == KeyEvent.ACTION_DOWN) {
			switch (event.getKeyCode()) {
			case KeyEvent.KEYCODE_BACK:
				if (mWebView.canGoBack()) {
					if (!isFrist && mWebView.getUrl().contains(firstUrl)) {
						finish();
						return true;
					}
					MyLog.d(TAG, "mWebView=" + mWebView.getUrl());
					mWebView.goBack();
					return true;
				}
				break;

			default:
				break;
			}
		}
		return super.dispatchKeyEvent(event);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		return super.onKeyDown(keyCode, event);
	}

	public void onClickLeftButton() {
		finish();

	}

	@Override
	public void onClickRightButton() {

	}

//	@Override
//	public void setCurrentTitleString() {
//		// setRightTitleString( getString( R.string.help ) );
//		setTopBtnBackground(R.drawable.selector_back,
//				R.drawable.selector_naviation_help);
//	}

	@Override
	public void onRefresh() {

	}

	@Override
	public String getNavigationLeftText() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isShowRightClose() {
		// TODO Auto-generated method stub
		return false;
	}
}
