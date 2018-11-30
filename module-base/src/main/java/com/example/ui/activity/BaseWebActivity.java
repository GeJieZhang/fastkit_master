package com.example.ui.activity;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.base.R;
import com.example.fastkit.utils.network.NetUtils;
import com.example.ui.activity.kit.BaseActivity;


public abstract class BaseWebActivity extends BaseActivity {

    private LinearLayout webLinearLayout;

    private ProgressBar progressBar;
    private WebView webView;

    private Handler handler = new Handler();

    @Override
    protected void onCreateView() {

        setContentView(getWebLayout());
        //webLinearLayout = findViewById(getWebId());
        progressBar = new ProgressBar(this);
        //webView = new WebView(this);
        //webLinearLayout.addView(progressBar);
        //webLinearLayout.addView(webView);
        webView=findViewById(getWebId());
        initView();
        initWebView();

    }


    public WebView getWebView() throws Exception {

        if (webView != null) {

            return webView;
        } else {
            throw new Exception("WebVeiw does not exist!");
        }

    }

    protected abstract void initView();

    protected abstract int getWebLayout();

    protected abstract int getWebId();


    protected abstract String getUrl();

    @Override
    protected int setStatusBarColor() {
        return R.color.colorPrimaryDark;
    }

    /**
     * 初始化WebView的配置
     */
    @SuppressWarnings("deprecation")
    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {
        webView.requestFocus();
        webView.setHorizontalScrollBarEnabled(false);
        webView.setVerticalScrollBarEnabled(false);
        webView.getSettings().setJavaScriptEnabled(true);
        // 设置 缓存模式
        if (NetUtils.isNetworkAvailable(this)) {
            webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        } else {
            webView.getSettings().setCacheMode(
                    WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
        // webView.getSettings().setBlockNetworkImage(true);// 把图片加载放在最后来加载渲染
        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        // 支持多窗口
        webView.getSettings().setSupportMultipleWindows(true);
        // 开启 DOM storage API 功能
        webView.getSettings().setDomStorageEnabled(true);
        // 开启 Application Caches 功能
        webView.getSettings().setAppCacheEnabled(true);
        onLoad();
    }

    /**
     * 开始加载Url
     */
    @SuppressWarnings("deprecation")
    @SuppressLint("SetJavaScriptEnabled")
    public void onLoad() {

        try {

            webView.setWebChromeClient(new WebChromeClient() {

                @Override
                public void onProgressChanged(WebView view, int newProgress) {
                    super.onProgressChanged(view, newProgress);

                    if (newProgress >= 100) {

                        progressBar.setProgress(100);


                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {


                                progressBar.setVisibility(View.GONE);
                            }
                        }, 250);//3秒后执行Runnable中的run方法
                    } else {
                        progressBar.setVisibility(View.VISIBLE);
                        progressBar.setProgress(newProgress);
                    }

                }
            });
            /**
             * WebViewClient
             */
            webView.setWebViewClient(new WebViewClient() {

                @Override
                public void onLoadResource(WebView view, String url) {


                    super.onLoadResource(view, url);
                }

                @Override
                public boolean shouldOverrideUrlLoading(WebView webview,
                                                        String url) {

                    webview.loadUrl(url);

                    return true;
                }

                @Override
                public void onPageFinished(WebView view, String url) {


                }

                @Override
                public void onReceivedError(WebView view, int errorCode,
                                            String description, String failingUrl) {
                }
            });

            webView.loadUrl(getUrl());

        } catch (Exception e) {
            return;
        }
    }


    /**
     * 点击返回按钮如果有浏览记录就返回上一个页面，没有就结束Actiity
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        } else {
            finish();
            return true;
        }
    }


    @Override
    protected void onDestroy() {
        if (webView != null) {

            // 如果先调用destroy()方法，则会命中if (isDestroyed()) return;这一行代码，需要先onDetachedFromWindow()，再
            // destory()
            ViewParent parent = webView.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(webView);
            }

            webView.stopLoading();
            // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
            webView.getSettings().setJavaScriptEnabled(false);
            webView.clearHistory();
            webView.clearView();
            webView.removeAllViews();
            webView.destroy();

        }
        super.onDestroy();
    }


}
