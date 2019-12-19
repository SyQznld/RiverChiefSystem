package com.appler.riverchiefsystem.ui.activity;


import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.appler.riverchiefsystem.R;
import com.appler.riverchiefsystem.base.BaseActivity;
import com.appler.riverchiefsystem.global.Global;
import com.appler.riverchiefsystem.utils.CommonUtils;
import com.appler.riverchiefsystem.utils.ToastUtils;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 通知公告详情
 */
public class NoticeDetailActivity extends BaseActivity {
    private String TAG = getClass().getSimpleName();
    @BindView(R.id.iv_toolbar_tv_back)
    ImageView ivBack;
    @BindView(R.id.tv_toolbar_tv_title)
    TextView tvTitle;
    @BindView(R.id.wv_notification)
    WebView webView;
    @BindView(R.id.pb_notification)
    ProgressBar progressBar;
    private String noticeId = "";  //通知公告id，拼接完整url

    @Override
    protected int bindLayout() {
        return R.layout.activity_notice_detail;
    }

    @Override
    protected void doBuissness(Context context) {
        tvTitle.setText("通知详情");
        noticeId = getIntent().getStringExtra("noticeId");
//  http://192.168.1.15:8003/lib/Map/hezhangzhi/News/Detail.html?id=6011c937-f9f8-469f-adfb-cb8ad97f3e95
        initWebView(Global.NOTICE_Url + noticeId);

    }

    @OnClick({R.id.iv_toolbar_tv_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_toolbar_tv_back:
                finish();
                break;
        }
    }

    private void initWebView(String url) {
        WebSettings settings = webView.getSettings();
        webView.requestFocusFromTouch();
        settings.setJavaScriptEnabled(true);   //支持js
        settings.setAllowFileAccess(true);     //设置可以访问文件
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setUseWideViewPort(true); //将图片调整到适合webview的大小
//        settings.setSupportZoom(true); //支持缩放
        // 1、LayoutAlgorithm.NARROW_COLUMNS ： 适应内容大小
        // 2、LayoutAlgorithm.SINGLE_COLUMN:适应屏幕，内容将自动缩放
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); //支持内容重新布
        settings.supportMultipleWindows(); //多窗口
        //  settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存

        settings.setNeedInitialFocus(true); //当webview调用requestFocus时为webview设置节点 webview
        settings.setBuiltInZoomControls(true); //设置支持缩放
        settings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        settings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        settings.setLoadsImagesAutomatically(true); //支持自动加载图片


        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                //显示进度条
                progressBar.setProgress(newProgress);
                if (newProgress == 100) {
                    //加载完毕隐藏进度条
                    progressBar.setVisibility(View.GONE);
                }
                super.onProgressChanged(view, newProgress);
            }
        });


        // 加载需要显示的网页
        webView.loadUrl(url);


        //  使用
        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimeType, long contentLength) {

                if (!"".equals(url) && url.contains("/")) {
                    String[] split = url.split("/");
                    String filename = split[split.length - 1];
                    final File file = new File(CommonUtils.filePath + File.separator + filename);
                    if (!file.exists()) {
                        CommonUtils.downloadNetFile(url);
                    }
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            CommonUtils.openFile(file, NoticeDetailActivity.this);
                        }
                    }, 1000);
                } else {
                    ToastUtils.showShortToast(NoticeDetailActivity.this, "暂无文件");
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //清空所有Cookie
        CookieSyncManager.createInstance(getApplicationContext());  //Create a singleton CookieSyncManager within a context
        CookieManager cookieManager = CookieManager.getInstance(); // the singleton CookieManager instance
        cookieManager.removeAllCookie();// Removes all cookies.
        CookieSyncManager.getInstance().sync(); // forces sync manager to sync now

        webView.setWebChromeClient(null);
        webView.setWebViewClient(null);
        webView.getSettings().setJavaScriptEnabled(false);
        webView.clearCache(true);
    }


}
