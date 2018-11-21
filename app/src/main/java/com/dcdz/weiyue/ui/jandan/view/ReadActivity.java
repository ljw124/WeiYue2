package com.dcdz.weiyue.ui.jandan.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dcdz.weiyue.R;
import com.dcdz.weiyue.been.FreshNewsArticleBean;
import com.dcdz.weiyue.been.FreshNewsBean;
import com.dcdz.weiyue.component.ApplicationComponent;
import com.dcdz.weiyue.net.BaseObserver;
import com.dcdz.weiyue.net.JanDanApi;
import com.dcdz.weiyue.net.RxSchedulers;
import com.dcdz.weiyue.ui.base.BaseActivity;
import com.dcdz.weiyue.utils.DateUtil;
import com.dcdz.weiyue.utils.ImageLoaderUtil;
import com.dcdz.weiyue.utils.StatusBarUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 新鲜事详情
 */
public class ReadActivity extends BaseActivity {

    private static final String DATA = "data";
    @BindView(R.id.iv_logo)
    ImageView mIvTop;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_author)
    TextView mTvAuthor;
    @BindView(R.id.tv_excerpt)
    TextView mTvExcerpt;
    @BindView(R.id.wv_contnet)
    WebView mWebView;
    @BindView(R.id.progress_wheel)
    ProgressBar progressWheel;
    @BindView(R.id.ll_content)
    LinearLayout llContent;
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.iv_share)
    ImageView mIvShare;
    @BindView(R.id.iv_comment)
    ImageView mIvComment;

    JanDanApi mJanDanApi;
    FreshNewsBean.PostsBean postsBean; //文章内容
    FreshNewsArticleBean newsArticleBean; //新鲜事文章

    public static void launch(Context context, FreshNewsBean.PostsBean postsBean) {
        Intent intent = new Intent(context, ReadActivity.class);
        intent.putExtra(DATA, postsBean);
        context.startActivity(intent);
    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_read;
    }

    @Override
    public void initInjector(ApplicationComponent appComponent) {
        mJanDanApi = appComponent.getJanDanApi();
    }

    @Override
    public void initData() {
    }

    @Override
    public void bindView(View view, Bundle savedInstanceState) {
//        setStatusBarColor(android.graphics.Color.parseColor("#64B6D1"), 30);
        StatusBarUtil.setTranslucentForImageView(this, StatusBarUtil.DEFAULT_STATUS_BAR_ALPHA, getStateView());
        if (getIntent().getExtras() == null) return;
        postsBean = (FreshNewsBean.PostsBean) getIntent().getSerializableExtra(DATA);
        if (postsBean == null) return;

        mTvTitle.setText(postsBean.getTitle());
        mTvAuthor.setText(postsBean.getAuthor().getName()
                + "  "
                + DateUtil.getTimestampString(DateUtil.string2Date(postsBean.getDate(), "yyyy-MM-dd HH:mm:ss")));
        mTvExcerpt.setText(postsBean.getExcerpt());
        ImageLoaderUtil.LoadImage(this, postsBean.getCustom_fields().getThumb_c().get(0), mIvTop);
        showSuccess();
        setWebViewSetting();
    }

    private void setWebViewSetting() {
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setAppCacheEnabled(true);
        mWebView.getSettings().setAllowFileAccessFromFileURLs(true);
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebView.setVerticalScrollBarEnabled(false);
        mWebView.setVerticalScrollbarOverlay(false);
        mWebView.setHorizontalScrollBarEnabled(false);
        mWebView.setHorizontalScrollbarOverlay(false);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.loadUrl("file:///android_asset/jd/post_detail.html");
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                getData(postsBean.getId());
            }
        });
    }

    private void getData(int id) {
        mJanDanApi.getFreshNewsArticle(id)
                .compose(RxSchedulers.<FreshNewsArticleBean>applySchedulers())
                .compose(this.<FreshNewsArticleBean>bindToLifecycle())
                .subscribe(new BaseObserver<FreshNewsArticleBean>() {
                    @Override
                    public void onSuccess(final FreshNewsArticleBean articleBean) {
                        if (articleBean == null) {
                            showFaild();
                        } else {
                            newsArticleBean = articleBean;
                            mWebView.post(new Runnable() {
                                @Override
                                public void run() {
                                    progressWheel.setVisibility(View.GONE);
                                    final String content = articleBean.getPost().getContent();
                                    String url = "javascript:show_content(\'" + content + "\')";
                                    mWebView.loadUrl(url);
                                }
                            });
                        }
                    }

                    @Override
                    public void onFail(Throwable e) {
                        showFaild();
                    }
                });
    }

    @Override
    public void onRetry() {
        getData(postsBean.getId());
    }

    @Override
    public void onBackPressedSupport() {
        super.onBackPressedSupport();
    }

    @OnClick({R.id.iv_back, R.id.iv_share, R.id.iv_comment})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_share:
                break;
            case R.id.iv_comment:
                break;
        }
    }
}
