package com.dcdz.weiyue.webview.config;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.dcdz.weiyue.utils.BaseTools;
import com.dcdz.weiyue.utils.NetUtil;
import com.dcdz.weiyue.webview.WebViewActivity;

/**
 * Created by ljw on 2018/10/8.
 * 主要帮助WebView处理各种通知、请求事件（例如，
 * 点击链接时候如何显示界面，页面开始加载，加载完毕之后有何动作等）
 * 监听网页链接:
 * - 优酷视频直接跳到自带浏览器
 * - 根据标识:打电话、发短信、发邮件
 * - 进度条的显示
 * - 添加javascript监听
 */
public class MyWebViewClient extends WebViewClient {

    private IWebPageView mIWebPageView;
    private WebViewActivity mActivity;

    public MyWebViewClient(IWebPageView mIWebPageView) {
        this.mIWebPageView = mIWebPageView;
        mActivity = (WebViewActivity) mIWebPageView;
    }

    /**
     * 网页跳转：
     * 1.在当前的webview跳转到新连接
     * view.loadUrl(url);
     * 2.调用系统浏览器跳转到新网页
     * Intent i = new Intent(Intent.ACTION_VIEW);
     * i.setData(Uri.parse(url));
     * startActivity(i);
     */
    @SuppressWarnings("deprecation")
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (TextUtils.isEmpty(url)) {
            return false;
        }
        if (url.startsWith("http:") || url.startsWith("https:")) {
            // 可能有提示下载Apk文件
            if (url.contains(".apk")) {
                handleOtherwise(mActivity, url);
                return true;
            }
            return false;
        }

        handleOtherwise(mActivity, url);
        return true;
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        if (!NetUtil.isNetworkAvailable(mActivity)) {
            mIWebPageView.hindProgressBar();
        }
        // html加载完成之后，添加监听图片的点击js函数
        mIWebPageView.addImageClickListener();
        super.onPageFinished(view, url);
    }

    // 视频全屏播放按返回页面被放大的问题
    @Override
    public void onScaleChanged(WebView view, float oldScale, float newScale) {
        super.onScaleChanged(view, oldScale, newScale);
        if (newScale - oldScale > 7) {
            view.setInitialScale((int) (oldScale / newScale * 100)); //异常放大，缩回去。
        }
    }

    /**
     * 网页里可能唤起其他的app
     */
    private void handleOtherwise(Activity activity, String url) {
        String appPackageName = "";
        if (url.contains("alipays")) { //支付宝支付
            appPackageName = "com.eg.android.AlipayGphone";
        } else if (url.contains("weixin://wap/pay")) { //微信支付
            appPackageName = "com.tencent.mm";
        } else if (url.contains("openapp.jdmobile")) { //京东产品详情
            appPackageName = "com.jingdong.app.mall";
        } else {
            startActivity(url);
        }
        if (BaseTools.isApplicationAvilible(activity, appPackageName)) {
            startActivity(url);
        }
    }

    private void startActivity(String url) {
        try {
            Intent intent1 = new Intent();
            intent1.setAction("android.intent.action.VIEW");
            Uri uri = Uri.parse(url);
            intent1.setData(uri);
            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mActivity.startActivity(intent1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
