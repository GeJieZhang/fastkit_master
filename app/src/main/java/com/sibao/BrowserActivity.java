package com.sibao;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;


import com.example.fastkit.views.webview_x5.X5WebView;
import com.tencent.smtt.sdk.CookieSyncManager;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebSettings.LayoutAlgorithm;
import com.tencent.smtt.utils.TbsLog;

public class BrowserActivity extends Activity {
    /**
     * 作为一个浏览器的示例展示出来，采用android+web的模式
     */
    private X5WebView mWebView;
    private ViewGroup mViewParent;
    private static final String mHomeUrl = "https://c.zhongan.com/insure/index.html?flowId=FJ18884602770e7841a4&i=e#/";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setFormat(PixelFormat.TRANSLUCENT);
//
//        try {
//            if (Integer.parseInt(android.os.Build.VERSION.SDK) >= 11) {
//                getWindow()
//                        .setFlags(
//                                android.view.WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
//                                android.view.WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
//            }
//        } catch (Exception e) {
//        }

        /*
         * getWindow().addFlags(
         * android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);
         */
        setContentView(R.layout.activity_browser);
        mViewParent = (ViewGroup) findViewById(R.id.webView1);

        init();

    }


    private void init() {

        mWebView = new X5WebView(this, null);

        mViewParent.addView(mWebView, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.FILL_PARENT,
                FrameLayout.LayoutParams.FILL_PARENT));


//        mWebView.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                return false;
//            }
//
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);
//
//            }
//        });
//
//        mWebView.setWebChromeClient(new WebChromeClient() {
//
//
//            @Override
//            public boolean onJsConfirm(WebView arg0, String arg1, String arg2,
//                                       JsResult arg3) {
//                return super.onJsConfirm(arg0, arg1, arg2, arg3);
//            }
//
//            View myVideoView;
//            View myNormalView;
//            CustomViewCallback callback;
//
//            // /////////////////////////////////////////////////////////
//            //
//
//            /**
//             * 全屏播放配置
//             */
//            @Override
//            public void onShowCustomView(View view,
//                                         CustomViewCallback customViewCallback) {
//
//            }
//
//            @Override
//            public void onHideCustomView() {
//                if (callback != null) {
//                    callback.onCustomViewHidden();
//                    callback = null;
//                }
//                if (myVideoView != null) {
//                    ViewGroup viewGroup = (ViewGroup) myVideoView.getParent();
//                    viewGroup.removeView(myVideoView);
//                    viewGroup.addView(myNormalView);
//                }
//            }
//
//            @Override
//            public boolean onJsAlert(WebView arg0, String arg1, String arg2,
//                                     JsResult arg3) {
//                /**
//                 * 这里写入你自定义的window alert
//                 */
//                return super.onJsAlert(null, arg1, arg2, arg3);
//            }
//        });


        WebSettings webSetting = mWebView.getSettings();
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(false);
        // webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(true);
        // webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setJavaScriptEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        webSetting.setAppCachePath(this.getDir("appcache", 0).getPath());
        webSetting.setDatabasePath(this.getDir("databases", 0).getPath());
        webSetting.setGeolocationDatabasePath(this.getDir("geolocation", 0)
                .getPath());
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        // webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        // webSetting.setPreFectch(true);
        long time = System.currentTimeMillis();
        mWebView.loadUrl(mHomeUrl);

        TbsLog.d("time-cost", "cost time: "
                + (System.currentTimeMillis() - time));
        CookieSyncManager.createInstance(this);
        CookieSyncManager.getInstance().sync();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mWebView != null && mWebView.canGoBack()) {
                mWebView.goBack();
                if (Integer.parseInt(android.os.Build.VERSION.SDK) >= 16)
                return true;
            } else
                return super.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onDestroy() {
        if (mWebView != null)
            mWebView.destroy();
        super.onDestroy();
    }



}
