package com.dcdz.weiyue.ui.news;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.dcdz.weiyue.R;
import com.dcdz.weiyue.been.NewsArticleBean;
import com.dcdz.weiyue.component.ApplicationComponent;
import com.dcdz.weiyue.component.DaggerHttpComponent;
import com.dcdz.weiyue.ui.base.BaseActivity;
import com.dcdz.weiyue.ui.news.contract.ArticleReadContract;
import com.dcdz.weiyue.ui.news.presenter.ArticleReadPresenter;
import com.dcdz.weiyue.utils.DateUtil;
import com.dcdz.weiyue.widget.ObservableScrollView;
import com.trello.rxlifecycle2.LifecycleTransformer;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 文章新闻浏览页面
 * WebView与js交互：https://blog.csdn.net/carson_ho/article/details/64904691
 */
public class ArticleReadActivity extends BaseActivity<ArticleReadPresenter> implements ArticleReadContract.View  {

    private static final String TAG = "ArticleReadActivity";
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.iv_logo)
    ImageView mIvLogo;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_updateTime)
    TextView mTvUpdateTime;
//    @BindView(R.id.tv_content)
//    TextView mTvContent;
    @BindView(R.id.webview)
    WebView mWebView;
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.ScrollView)
    ObservableScrollView mScrollView;
    @BindView(R.id.ConstraintLayout)
    RelativeLayout mConstraintLayout;
    //下面这几个控件用来控制标题栏内容的显隐
    @BindView(R.id.rl_top)
    RelativeLayout mRlTop;
    @BindView(R.id.iv_topLogo)
    ImageView mIvTopLogo;
    @BindView(R.id.tv_topname)
    TextView mTvTopName;
    @BindView(R.id.tv_TopUpdateTime)
    TextView mTvTopUpdateTime;

    @Override
    public int getContentLayout() {
        return R.layout.activity_article_read;
    }

    @Override
    public void initInjector(ApplicationComponent appComponent) {
        DaggerHttpComponent.builder()
                .applicationComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public void bindView(View view, Bundle savedInstanceState) {
        setWebViewSetting();
        setStatusBarColor(Color.parseColor("#64B6D1"), 30);
        //给ObservableScrollView设置滚动监听，控制标题栏上的内容显隐
        mScrollView.setScrollViewListener(new ObservableScrollView.ScrollViewListener() {
            @Override
            public void onScrollChanged(ObservableScrollView scrollView, int x, int scrollY, int oldx, int oldy) {
                //当Y方法滑动距离大于标题栏的高度，显示（说明：scrollY表示的是view相对于window窗口滑动的Y距离）
                if (scrollY > mConstraintLayout.getHeight()) {
                    mRlTop.setVisibility(View.VISIBLE);
                } else {
                    mRlTop.setVisibility(View.GONE);
                }
            }
        });
    }

    private void setWebViewSetting() {
        addjs(mWebView);
        //设置允许JS弹窗
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setAppCacheEnabled(true); //开启 Application Caches 功能
        //设置可以访问文件
        mWebView.getSettings().setAllowFileAccessFromFileURLs(true);
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        //缓存模式如下：
        //LOAD_CACHE_ONLY: 不使用网络，只读取本地缓存数据
        //LOAD_DEFAULT: （默认）根据cache-control决定是否从网络上取数据。
        //LOAD_NO_CACHE: 不使用缓存，只从网络获取数据.
        //LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//优先使用缓存
        mWebView.getSettings().setDomStorageEnabled(true); // 开启 DOM storage API 功能

        mWebView.setVerticalScrollBarEnabled(false);
        mWebView.setVerticalScrollbarOverlay(false);
        mWebView.setHorizontalScrollBarEnabled(false);
        mWebView.setHorizontalScrollbarOverlay(false);
        /**
         * 重点：先载入JS代码。格式规定为:file:///android_asset/文件名.html
         */
        mWebView.loadUrl("file:///android_asset/ifeng/post_detail.html");
        //WebViewClient作用：处理各种通知 & 请求事件
        mWebView.setWebViewClient(new WebViewClient() {
            //作用：开始载入页面调用的，我们可以设定一个loading的页面，告诉用户程序在等待网络响应
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            //作用：在页面加载结束时调用。我们可以关闭loading条，切换程序动作
            //特别注意：JS代码调用一定要在 onPageFinished（） 回调之后才能调用，否则不会调用
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                String aid = getIntent().getStringExtra("aid");
                mPresenter.getData(aid);
            }
        });
    }

    //Javascript对象
    @SuppressLint("JavascriptInterface")
    private void addjs(final WebView webview) {
        class JsObject {
            // 定义JS需要调用的方法，被JS调用的方法必须加入@JavascriptInterface注解
            @JavascriptInterface
            public void jsFunctionimg(final String i) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.i(TAG, "run: " + i);
                    }
                });
            }
        }
        /**
         * JS调用Android代码（进行对象映射），通过addJavascriptInterface()将Java对象映射到JS对象
         * 参数1：Javascript对象名
         * 参数2：Java对象名
         */
        webview.addJavascriptInterface(new JsObject(), "jscontrolimg");//JsObject类对象映射到js的jscontrolimg对象
    }

    @Override
    public void initData() {
        //本应该在此调用ArticleReadPresenter中的方法获取数据，但这一步在setWebViewSetting()中做过了
    }

    //presenter获取数据之后，会把数据回调给此方法
    @Override
    public void loadData(final NewsArticleBean articleBean) {
        mTvTitle.setText(articleBean.getBody().getTitle());
        mTvUpdateTime.setText(DateUtil.getTimestampString(DateUtil.string2Date(articleBean.getBody().getUpdateTime(), "yyyy/MM/dd HH:mm:ss")));
        if (articleBean.getBody().getSubscribe() != null) {
            Glide.with(this).load(articleBean.getBody().getSubscribe().getLogo())
                    .apply(new RequestOptions()
                            .transform(new CircleCrop())
                            //.placeholder()
                            .diskCacheStrategy(DiskCacheStrategy.ALL))
                    .into(mIvLogo);
            Glide.with(this).load(articleBean.getBody().getSubscribe().getLogo())
                    .apply(new RequestOptions()
                            .transform(new CircleCrop())
                            //.placeholder()
                            .diskCacheStrategy(DiskCacheStrategy.ALL))
                    .into(mIvTopLogo);
            mTvTopName.setText(articleBean.getBody().getSubscribe().getCateSource());
            mTvName.setText(articleBean.getBody().getSubscribe().getCateSource());
            mTvTopUpdateTime.setText(articleBean.getBody().getSubscribe().getCatename());
        } else {
            mTvTopName.setText(articleBean.getBody().getSource());
            mTvName.setText(articleBean.getBody().getSource());
            mTvTopUpdateTime.setText(!TextUtils.isEmpty(articleBean.getBody().getAuthor()) ? articleBean.getBody().getAuthor() : articleBean.getBody().getEditorcode());
        }
        mWebView.post(new Runnable() {
            @Override
            public void run() {
                /**
                 * android调用js代码。注意调用的JS方法名要对应上。调用javascript的show_content()方法
                 */
                final String content = articleBean.getBody().getText();
                String url = "javascript:show_content(\'" + content + "\')";
                mWebView.loadUrl(url);
                showSuccess();
            }
        });
    }

    @Override
    public void onRetry() {
        String aid = getIntent().getStringExtra("aid");
        mPresenter.getData(aid);
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }

}
