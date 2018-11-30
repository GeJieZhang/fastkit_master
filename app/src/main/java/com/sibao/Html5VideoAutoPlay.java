package com.sibao;

import android.app.Activity;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Html5VideoAutoPlay extends Activity {
		WebView webview = null;
		@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
 
 
			setContentView(R.layout.html5video);





 
			webview = (WebView)findViewById(R.id.webview);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                webview.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            }
			webview.getSettings().setMediaPlaybackRequiresUserGesture(false);
            webview.getSettings().setDefaultTextEncodingName("utf-8") ;
			webview.getSettings().setJavaScriptEnabled(true);
            webview.getSettings().setDomStorageEnabled(true);
			webview.setWebViewClient(new WebViewClient(){
				/**
				 * 当前网页的链接仍在webView中跳转
				 */
				@Override
				public boolean shouldOverrideUrlLoading(WebView view, String url) {
					view.loadUrl(url);
					return true;
				}
 
 
				/**
				 * 处理ssl请求
				 */
				@Override
				public void onReceivedSslError(WebView view,
											   SslErrorHandler handler, SslError error) {
					handler.proceed();
				}
 
 
				/**
				 * 页面载入完成回调
				 */
				@Override
				public void onPageFinished(WebView view, String url) {
					super.onPageFinished(view, url);
					view.loadUrl("javascript:try{autoplay();}catch(e){}");
				}
			});
 
 
			webview.setWebChromeClient(new WebChromeClient() {
				/**
				 * 显示自定义视图，无此方法视频不能播放
				 */
				@Override
				public void onShowCustomView(View view, CustomViewCallback callback) {
					super.onShowCustomView(view, callback);
				}
			});

            //webview.loadDataWithBaseURL(null, s, "text/html", "utf-8", null);
			webview.loadUrl("file:///android_asset/index.html");
		}
 
 
		@Override
		protected void onPause() {
			if(null != webview) {
				webview.onPause();
			}
			super.onPause();
		}
    }
