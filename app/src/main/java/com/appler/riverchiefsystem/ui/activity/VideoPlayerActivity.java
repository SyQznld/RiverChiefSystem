package com.appler.riverchiefsystem.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.DownloadListener;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.appler.riverchiefsystem.R;
import com.appler.riverchiefsystem.base.BaseActivity;
import com.appler.riverchiefsystem.utils.MainHandler;
import com.appler.riverchiefsystem.utils.MyTimeTask;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class VideoPlayerActivity extends BaseActivity {

    private static final int TIMER = 999;
    private static ValueCallback<Uri> mFileUploadCallbackLowVersion;
    private static ValueCallback<Uri[]> mFileUploadCallbackHighVersion;
    @BindView(R.id.iv_toolbar_tv_back)
    ImageView iv_back;
    @BindView(R.id.tv_toolbar_tv_title)
    TextView tv_title;
    @BindView(R.id.wv_video)
    WebView webView;
    private String TAG = getClass().getSimpleName();
    private String txtPath = Environment.getExternalStorageDirectory() + File.separator + "video" + File.separator;
    private MyTimeTask task;

    // 生成文件夹
    public static void makeRootDirectory(String filePath) {
        File file = null;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdir();
            }
        } catch (Exception e) {
            Log.i("error:", e + "");
        }
    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_video_player;
    }

    @Override
    protected void doBuissness(Context context) {
        tv_title.setText("视频播放");


        String url = "http://192.168.2.119:8999/?port=8082";   //测试视频接口

        WebSettings settings = webView.getSettings();
        webView.requestFocusFromTouch();
        settings.setJavaScriptEnabled(true);   //支持js
        settings.setAllowFileAccess(true);     //设置可以访问文件
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setUseWideViewPort(false); //将图片调整到适合webview的大小
        settings.setSupportZoom(true); //支持缩放
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); //支持内容重新布
        settings.supportMultipleWindows(); //多窗口
        //  settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存

        settings.setNeedInitialFocus(true); //当webview调用requestFocus时为webview设置节点 webview
        settings.setBuiltInZoomControls(true); //设置支持缩放
        settings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        settings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        settings.setLoadsImagesAutomatically(true); //支持自动加载图片


        webView.setWebChromeClient(new MyWebChromeClient());
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });


        Log.i("", "doAfterGrand: " + url);
        // 加载需要显示的网页
        webView.loadUrl(url);

        //  使用
        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimeType, long contentLength) {
                String fileName = URLUtil.guessFileName(url, contentDisposition, mimeType);
//                String destPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + File.separator + fileName;
                String destPath = txtPath + fileName;
                // new DownloadTask().execute(url, destPath);

                // 使用
                DownloadCompleteReceiver receiver = new DownloadCompleteReceiver();
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
                registerReceiver(receiver, intentFilter);

                downloadBySystem(url, contentDisposition, mimeType);

            }
        });
    }

    @OnClick({R.id.iv_toolbar_tv_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_toolbar_tv_back:
                exitVideo(1);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        exitVideo(2);
                    }
                }, 1000);
                break;
        }
    }

    private void downloadBySystem(String url, String contentDisposition, String mimeType) {
        // 指定下载地址
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        // 允许媒体扫描，根据下载的文件类型被加入相册、音乐等媒体库
        request.allowScanningByMediaScanner();
        // 设置通知的显示类型，下载进行时和完成后显示通知
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        // 设置通知栏的标题，如果不设置，默认使用文件名
//        request.setTitle("This is title");
        // 设置通知栏的描述
//        request.setDescription("This is description");
        // 允许在计费流量下下载
        request.setAllowedOverMetered(false);
        // 允许该记录在下载管理界面可见
        request.setVisibleInDownloadsUi(false);
        // 允许漫游时下载
        request.setAllowedOverRoaming(true);
        // 允许下载的网路类型
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
        // 设置下载文件保存的路径和文件名
        String fileName = URLUtil.guessFileName(url, contentDisposition, mimeType);
        Log.i("fileName:{}", fileName);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, fileName);

//        Uri uriForFile;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            uriForFile = FileProvider.getUriForFile(MainActivity.this, BuildConfig.APPLICATION_ID + ".fileprovider", new File(txtPath + fileName));
//        } else {
//            uriForFile = Uri.fromFile(new File(txtPath + fileName));
//        }
//        Log.i("", "downloadBySystem: " + uriForFile.getPath());
//        request.setDestinationUri(uriForFile);
//        request.setDestinationInExternalFilesDir(MainActivity.this,Environment.getExternalStorageDirectory() + File.separator + "dianwu",fileName);
        final DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        // 添加一个下载任务
        long downloadId = downloadManager.enqueue(request);
        Log.i("downloadId:{}", downloadId + "");
    }

    private String getMIMEType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        Log.i("extension:{}", extension);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }


    public boolean onKeyDown(int keyCoder, KeyEvent event) {
        if (webView.canGoBack() && keyCoder == KeyEvent.KEYCODE_BACK) {
            webView.goBack();   //goBack()表示返回webView的上一页面
            return true;
        }

        return false;
    }

    // 生成文件
    public File makeFilePath(String filePath, String fileName) {
        File file = null;
        makeRootDirectory(filePath);
        try {
            file = new File(filePath + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    @SuppressLint("NewApi")
    protected void openFileInput(final ValueCallback<Uri> fileUploadCallbackFirst, final ValueCallback<Uri[]> fileUploadCallbackSecond, final boolean allowMultiple) {
        //Android 5.0以下版本
        if (mFileUploadCallbackLowVersion != null) {
            mFileUploadCallbackLowVersion.onReceiveValue(null);
        }
        mFileUploadCallbackLowVersion = fileUploadCallbackFirst;

        //Android 5.0及以上版本
        if (mFileUploadCallbackHighVersion != null) {
            mFileUploadCallbackHighVersion.onReceiveValue(null);
        }
        mFileUploadCallbackHighVersion = fileUploadCallbackSecond;

        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);

        if (allowMultiple) {
            if (Build.VERSION.SDK_INT >= 18) {
                i.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            }
        }
        i.setType("image/*");
        startActivityForResult(Intent.createChooser(i, "选择文件"), 1001);
    }

    public void onActivityResult(final int requestCode, final int resultCode, final Intent intent) {
        if (requestCode == 1001) {
            if (resultCode == Activity.RESULT_OK) {
                if (intent != null) {
                    //Android 5.0以下版本
                    if (mFileUploadCallbackLowVersion != null) {
                        mFileUploadCallbackLowVersion.onReceiveValue(intent.getData());
                        mFileUploadCallbackLowVersion = null;
                    } else if (mFileUploadCallbackHighVersion != null) {//Android 5.0及以上版本
                        Uri[] dataUris = null;

                        try {
                            if (intent.getDataString() != null) {
                                dataUris = new Uri[]{Uri.parse(intent.getDataString())};
                            } else {
                                if (Build.VERSION.SDK_INT >= 16) {
                                    if (intent.getClipData() != null) {
                                        final int numSelectedFiles = intent.getClipData().getItemCount();

                                        dataUris = new Uri[numSelectedFiles];

                                        for (int i = 0; i < numSelectedFiles; i++) {
                                            dataUris[i] = intent.getClipData().getItemAt(i).getUri();
                                        }
                                    }
                                }
                            }
                        } catch (Exception ignored) {
                        }
                        mFileUploadCallbackHighVersion.onReceiveValue(dataUris);
                        mFileUploadCallbackHighVersion = null;
                    }
                }
            } else {
                //这里mFileUploadCallbackFirst跟mFileUploadCallbackSecond在不同系统版本下分别持有了
                //WebView对象，在用户取消文件选择器的情况下，需给onReceiveValue传null返回值
                //否则WebView在未收到返回值的情况下，无法进行任何操作，文件选择器会失效
                if (mFileUploadCallbackLowVersion != null) {
                    mFileUploadCallbackLowVersion.onReceiveValue(null);
                    mFileUploadCallbackLowVersion = null;
                } else if (mFileUploadCallbackHighVersion != null) {
                    mFileUploadCallbackHighVersion.onReceiveValue(null);
                    mFileUploadCallbackHighVersion = null;
                }
            }
        }
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

    private void exitVideo(final int time) {
        //在此执行定时操作
        //1.okhttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        //2构造Request,
        //builder.get()代表的是get请求，url方法里面放的参数是一个网络地址
        Request.Builder requestBuilder = new Request.Builder();
        Request request = requestBuilder.get().url("http://192.168.2.119:3000/?name=admin&passwd=123456789.&url=192.168.2.129&&port1=8081&port2=8082&kill=true").build();

        //3将Request封装成call
        Call call = okHttpClient.newCall(request);
        //4，执行call，这个方法是异步请求数据
        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i(TAG, "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String string = response.body().string();
                Log.i(TAG, "onResponse: " + string);
                MainHandler.getInstance().post(new Runnable() {
                    @Override
                    public void run() {
                        if (string.contains("网站名")) {
                            if (time == 2) {
                                finish();
                            }
                        }
                    }
                });
            }
        });
    }

    private class DownloadCompleteReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i("onReceive. intent:{}", intent != null ? intent.toUri(0) : null);
            if (intent != null) {
                if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(intent.getAction())) {
                    long downloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                    Log.i("downloadId", downloadId + "");
                    DownloadManager downloadManager = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
                    String type = downloadManager.getMimeTypeForDownloadedFile(downloadId);
                    Log.i("getMimeTypeFo", type);
                    if (TextUtils.isEmpty(type)) {
                        type = "*/*";
                    }
                    Uri uri = downloadManager.getUriForDownloadedFile(downloadId);
                    if (uri != null) {
                        Intent handlerIntent = new Intent(Intent.ACTION_VIEW);
                        handlerIntent.setDataAndType(uri, type);
                        context.startActivity(handlerIntent);
                    }
                }
            }
        }
    }

    private class MyWebChromeClient extends WebChromeClient {

        //  Android 3.0 以下
        @SuppressWarnings("unused")
        public void openFileChooser(ValueCallback<Uri> uploadMsg) {
            openFileChooser(uploadMsg, null);
        }

        // Android 3.0以上
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
            openFileChooser(uploadMsg, acceptType, null);
        }

        // Androi 4.4以下
        @SuppressWarnings("unused")
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
            openFileInput(uploadMsg, null, false);
        }

        //  Android 5.0以上
        @SuppressWarnings("all")
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
            if (Build.VERSION.SDK_INT >= 21) {
                final boolean allowMultiple = fileChooserParams.getMode() == WebChromeClient.FileChooserParams.MODE_OPEN_MULTIPLE;//是否支持多选
                openFileInput(null, filePathCallback, allowMultiple);
                return true;
            } else {
                return false;
            }
        }
    }

}
