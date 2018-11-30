package com.example.fastkit.views.webview_x5;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;

import com.tencent.smtt.sdk.CookieSyncManager;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebSettings.LayoutAlgorithm;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

public class X5WebView extends WebView {
    private Context context;
    private WebViewClient client = new WebViewClient() {
        /**
         * 防止加载网页时调起系统浏览器
         */
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    };

    @SuppressLint("SetJavaScriptEnabled")
    public X5WebView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        //去掉右侧方块
        //this.getX5WebViewExtension().setScrollBarFadingEnabled(false);
        this.context = context;
        this.setWebViewClient(client);
        // this.setWebChromeClient(chromeClient);
        // WebStorage webStorage = WebStorage.getInstance();
        initWebViewSettings();
        this.getView().setClickable(true);
        this.getView().setScrollbarFadingEnabled(false);
        this.setInitialScale(100);
    }

    private void initWebViewSettings() {
        WebSettings webSetting = this.getSettings();

        //支持JS
        webSetting.setJavaScriptEnabled(true);
        //支持通过JS打开新窗口
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        //设置可访问文件
        webSetting.setAllowFileAccess(true);
        /**
         * 1.NARROW_COLUMNS：可能的话使所有列的宽度不超过屏幕宽度

         2.NORMAL：正常显示不做任何渲染

         3.SINGLE_COLUMN：把所有内容放大webview等宽的一列中
         */
        webSetting.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
        //缩放开关
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);

        //不显示webview缩放按钮
        webSetting.setDisplayZoomControls(false);
        //设置适应屏幕
        webSetting.setUseWideViewPort(true);
        //支持多窗口
        webSetting.setSupportMultipleWindows(true);
        // webSetting.setLoadWithOverviewMode(true);
        //设置缓存
        webSetting.setAppCacheEnabled(true);
        // webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        // webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);

        // this.getSettingsExtension().setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);//extension
        // settings 的设计


        /**
         * 附加
         */

        webSetting.setAppCachePath(context.getDir("AppCache", 0).getPath());
        webSetting.setDatabasePath(context.getDir("DataBases", 0).getPath());
        webSetting.setGeolocationDatabasePath(context.getDir("Geolocation", 0)
                .getPath());
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        // webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        // webSetting.setPreFectch(true);
        CookieSyncManager.createInstance(context);
        CookieSyncManager.getInstance().sync();
    }


    public X5WebView(Context context) {
        super(context);
        setBackgroundColor(85621);
    }

}
