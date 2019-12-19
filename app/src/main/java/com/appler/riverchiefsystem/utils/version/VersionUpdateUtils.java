package com.appler.riverchiefsystem.utils.version;

import android.app.ProgressDialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.content.FileProvider;

import com.appler.riverchiefsystem.base.BaseActivity;
import com.appler.riverchiefsystem.base.BaseApplication;
import com.appler.riverchiefsystem.global.Global;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 页面描述:
 */
public class VersionUpdateUtils {


    //获取设备id
    public static String getDeviceId() {
        String str = "";
        String ANDROID_ID = Settings.System.getString(BaseApplication.getBaseApplication().getContentResolver(), Settings.System.ANDROID_ID);
        copy(ANDROID_ID);
        return ANDROID_ID;
    }

    //将内容复制到系统粘贴板上
    public static void copy(String str) {
        // 从API11开始android推荐使用android.content.ClipboardManager
        // 为了兼容低版本我们这里使用旧版的android.text.ClipboardManager，虽然提示deprecated，但不影响使用。
        ClipboardManager cm = (ClipboardManager) BaseApplication.getBaseApplication().getSystemService(Context.CLIPBOARD_SERVICE);
        // 将文本内容放到系统剪贴板里。
        cm.setText(str);
    }

    /**
     * 获取当前系统版本号
     */
    public static String getVersionName() {
        int versioncode;
        String versionName = "";
        PackageManager pm = BaseApplication.getBaseApplication().getPackageManager();
        try {
            PackageInfo packageInfo = pm.getPackageInfo(BaseApplication.getBaseApplication().getPackageName(), 0);
            versioncode = packageInfo.versionCode;
            versionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }


    /**
     * 下载apk
     */
    public static void downFile(final Context context, final String url) {
        final Handler m_mainHandler = new Handler();
        final DownLoadProgressDialog mDialog = new DownLoadProgressDialog(context);
        mDialog.setCancelable(false);
        mDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mDialog.setIndeterminate(true);
        mDialog.show();
        new Thread() {
            public void run() {
                HttpClient client = new DefaultHttpClient();
                HttpGet get = new HttpGet(url);
                HttpResponse response;
                try {
                    response = client.execute(get);
                    HttpEntity entity = response.getEntity();
                    long length = entity.getContentLength();

                    mDialog.setMax((int) length);// 设置进度条的最大值

                    InputStream is = entity.getContent();
                    FileOutputStream fileOutputStream = null;
                    if (is != null) {
                        File file = new File(
                                context.getExternalFilesDir(null),
                                Global.APKNAME);
//                        File file = new File(AppConstant.ROOTDIR,
//                                AppConstant.APKNAME);
                        if (file.exists()) {
                            file.delete();
                        }
                        fileOutputStream = new FileOutputStream(file);

                        byte[] buf = new byte[1024];
                        int ch = -1;
                        int count = 0;
                        while ((ch = is.read(buf)) != -1) {
                            fileOutputStream.write(buf, 0, ch);
                            count += ch;
                            if (length > 0) {
                                mDialog.setProgress(count);
                            }
                        }
                    }
                    fileOutputStream.flush();
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }
                    m_mainHandler.post(new Runnable() {
                        public void run() {
                            mDialog.cancel();
                            update(context);
                        }
                    });
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                    mDialog.cancel();
                } catch (IOException e) {
                    e.printStackTrace();
                    mDialog.cancel();
                }
            }
        }.start();
    }

    /**
     * 启动app
     *
     * @param context
     */
    public static void update(Context context) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri data;
        File file = new File(context.getExternalFilesDir(null), Global.APKNAME);
        // 判断版本大于等于7.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // "com.ysy.assistant.fileprovider"即是在清单文件中配置的authorities
            data = FileProvider.getUriForFile(context, "com.appler.riverchiefsystem.fileprovider", file);
            // 给目标应用一个临时授权
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            data = Uri.fromFile(file);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }

        intent.setDataAndType(data, "application/vnd.android.package-archive");
        context.startActivity(intent);

        if (context instanceof BaseActivity) {
            ((BaseActivity) context).finish();
        }
    }

}
