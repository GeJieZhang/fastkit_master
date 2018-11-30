package com.sibao;

import android.net.Uri;
import android.webkit.WebView;

import com.example.ui.activity.BaseWebActivity;

public class MyWebActivity extends BaseWebActivity {

    private String url = "";

    private WebView webView;


    @Override
    protected void initView() {

        url = "https://wap.baidu.com/";

        try {
            webView = getWebView();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected int getWebLayout() {
        return R.layout.my_web_activity;
    }

    @Override
    protected int getWebId() {
        return R.id.web_view;
    }

    @Override
    protected String getUrl() {
        return url;
    }
}
