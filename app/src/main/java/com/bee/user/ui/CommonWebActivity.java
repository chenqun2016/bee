package com.bee.user.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bee.user.Constants;
import com.bee.user.R;
import com.bee.user.ui.base.SwipeRefreshBaseActivity;
import com.bee.user.ui.base.activity.BaseActivity;
import com.bee.user.utils.LogUtil;
import com.bee.user.widget.MyWebView;

import butterknife.BindView;

/**
 * 创建人：进京赶考
 * 创建时间：2020/11/06  14：41
 * 描述：
 */
public class CommonWebActivity extends SwipeRefreshBaseActivity {
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.progressbar)
    ProgressBar progressbar;

    @BindView(R.id.webview)
    MyWebView mWebView;

    boolean innertitle;
    private int i;

    private WebChromeClient mWebChromeClient =  new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            // 进度发生变化
            progressbar.setProgress(newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);

//            if (!TextUtils.isEmpty(title) && innertitle) {
//                toolbar_title.setText(title);
//            } else {
//                if (!TextUtils.isEmpty(title)) {
//                    toolbar_title.setText(title);
//                } else {
//                    toolbar_title.setText("趣鲜蜂");
//                }
//            }
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.activity_common_web;
    }

    @Override
    public void initViews() {
        Intent intent = getIntent();
        //        是否使用内部标题
        innertitle = intent.getBooleanExtra("innertitle", true);
        //6.是否刷新
        boolean reflush = intent.getBooleanExtra("reflush", false);
        mSwipeRefreshLayout.setEnabled(reflush);
        //-1时不能在页面内跳转
        i = getIntent().getIntExtra("type", 0);
        initWebView();
        // 适用于自动埋点版本，用于对webview加载的h5页面进行自动统计；需要在载入页面前调用，
// 建议在webview初始化时刻调用
// chromeClient，如果有设置的WebChromeClient，则需要将对象传入，否则影响本身回调
//        StatService.trackWebView(this, mWebView, mWebChromeClient);

        //.普通url
        String url = intent.getStringExtra("url");
        if(!TextUtils.isEmpty(url)){
            mWebView.loadUrl(url);
        }else{
            String data = intent.getStringExtra("data");
            mWebView.loadDataWithBaseURL(null,data,"text/html", "utf-8",null);
        }
    }


    @Override
    public void onReflush() {
        super.onReflush();
        mWebView.reload();
        setRefresh(false);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {
        WebSettings settings = mWebView.getSettings();
        if (getIntent().getBooleanExtra("scale", false)) {
            settings.setBuiltInZoomControls(true);// 显示缩放按钮(wap网页不支持)
        }
        settings.setUseWideViewPort(true);// 支持双击缩放(wap网页不支持)
        settings.setLoadWithOverviewMode(true);
        settings.setSavePassword(false);
        settings.setJavaScriptEnabled(true);// 支持js功能
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setDomStorageEnabled(true);//H5使用了在浏览器本地存储功能就必须加这句
        mWebView.setWebViewClient(new WebViewClient() {

            // 网页加载结束
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressbar.setVisibility(View.GONE);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                progressbar.setVisibility(View.GONE);
//                ll_webcontainer.removeAllViews();
//                ll_webcontainer.addView(errorView, 0);
                super.onReceivedError(view, errorCode, description, failingUrl);
            }

            // 所有链接跳转会走此方法
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                LogUtil.e(url);
                if (i != -1) {
//TODO                     打开pdf文件
                    if (!TextUtils.isEmpty(url) && url.contains(".pdf")) {
//                        PDFUtils.showPDFAuto(CommonWebActivity.this, url, Constants.PDF_Common);
                    } else if (!TextUtils.isEmpty(url) && url.contains("tel:")) {
//                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + getResources().getString(R.string.telphone)));
//                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        startActivity(intent);
                    } else {
                        view.loadUrl(url);// 在跳转链接时强制在当前webview中加载
                    }
                }
                //此方法还有其他应用场景, 比如写一个超链接<a href="tel:110">联系我们</a>, 当点击该超链接时,可以在此方法中获取链接地址tel:110, 解析该地址,获取电话号码, 然后跳转到本地打电话页面, 而不是加载网页, 从而实现了webView和本地代码的交互
                return true;
            }

//            @Override
//            public void onReceivedSslError(WebView view,
//                                           SslErrorHandler handler, SslError error) {
//                handler.cancel();
//            }
        });
        mWebView.setWebChromeClient(mWebChromeClient);
    }

    @Override
    public void onBackPressed() {
        if (null != mWebView && mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
