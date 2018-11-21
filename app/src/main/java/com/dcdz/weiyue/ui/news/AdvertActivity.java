package com.dcdz.weiyue.ui.news;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.dcdz.weiyue.R;
import com.dcdz.weiyue.component.ApplicationComponent;
import com.dcdz.weiyue.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 广告页面
 */
public class AdvertActivity extends BaseActivity {

    private static final String Url = "url";

    @BindView(R.id.pb_progress)
    ProgressBar mPbProgress;
    @BindView(R.id.webview_advert)
    WebView mWebviewAdvert;

    public static void launch(Context context, String url) {
        Intent intent = new Intent(context, AdvertActivity.class);
        intent.putExtra(Url, url);
        context.startActivity(intent);
    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_advert;
    }

    @Override
    public void initInjector(ApplicationComponent appComponent) {

    }

    @Override
    public void bindView(View view, Bundle savedInstanceState) {
        setStatusBarColor(Color.parseColor("#64B6D1"),30);
        getSetting(mWebviewAdvert);
        mWebviewAdvert.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            //复写shouldOverrideUrlLoading()方法，使得打开网页时不调用系统浏览器， 而是在本WebView中显示
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }
        });
        // WebChromeClient作用：辅助 WebView 处理 Javascript 的对话框,网站图标,网站标题等等
        // webview只是载体，内容的渲染需要使用webviewChro去实现
        mWebviewAdvert.setWebChromeClient(new WebChromeClient() {
            //作用：获得网页的加载进度并显示
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (mPbProgress == null) {
                    return;
                }
                if (newProgress == 100) {
                    mPbProgress.setVisibility(View.GONE);
                } else {
                    mPbProgress.setVisibility(View.VISIBLE);
                    mPbProgress.setProgress(newProgress);
                }
            }
        });
    }

    @Override
    public void initData() {
        if (getIntent() == null) return;
        String url = getIntent().getStringExtra("url");
        if (!TextUtils.isEmpty(url)) {
            mWebviewAdvert.loadUrl(url);
        }
    }

    @Override
    public void onRetry() {

    }

    private void getSetting(WebView webview) {
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setSupportZoom(true);
        webview.getSettings().setBuiltInZoomControls(true);
        webview.getSettings().setDisplayZoomControls(false);
        webview.getSettings().setUseWideViewPort(false);
        webview.getSettings().setAppCacheEnabled(true);
        webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webview.getSettings().setAllowFileAccessFromFileURLs(true);
        webview.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//LOAD_CACHE_ELSE_NETWORK模式下，无论是否有网络，只要本地有缓存，都使用缓存。本地没有缓存时才从网络上获取
        webview.getSettings().setDomStorageEnabled(true);
        webview.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return true;
            }
        });
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}