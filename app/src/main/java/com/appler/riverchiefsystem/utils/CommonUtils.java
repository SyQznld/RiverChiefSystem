package com.appler.riverchiefsystem.utils;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.appler.riverchiefsystem.R;
import com.appler.riverchiefsystem.api.IGetFilterCity;
import com.appler.riverchiefsystem.base.BaseApplication;
import com.appler.riverchiefsystem.global.Global;
import com.appler.riverchiefsystem.utils.customView.SpinerPopWindow;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okio.BufferedSink;
import okio.Okio;
import okio.Sink;


public class CommonUtils {


    private final static String[][] MIME_MapTable = {
            //{后缀名，MIME类型}
            {".3gp", "video/3gpp"},
            {".apk", "application/vnd.android.package-archive"},
            {".asf", "video/x-ms-asf"},
            {".avi", "video/x-msvideo"},
            {".bin", "application/octet-stream"},
            {".bmp", "image/bmp"},
            {".c", "text/plain"},
            {".class", "application/octet-stream"},
            {".conf", "text/plain"},
            {".cpp", "text/plain"},
            {".doc", "application/msword"},
            {".docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"},
            {".xls", "application/vnd.ms-excel"},
            {".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"},
            {".exe", "application/octet-stream"},
            {".gif", "image/gif"},
            {".gtar", "application/x-gtar"},
            {".gz", "application/x-gzip"},
            {".h", "text/plain"},
            {".htm", "text/html"},
            {".html", "text/html"},
            {".jar", "application/java-archive"},
            {".java", "text/plain"},
            {".jpeg", "image/jpeg"},
            {".jpg", "image/jpeg"},
            {".js", "application/x-javascript"},
            {".log", "text/plain"},
            {".m3u", "audio/x-mpegurl"},
            {".m4a", "audio/mp4a-latm"},
            {".m4b", "audio/mp4a-latm"},
            {".m4p", "audio/mp4a-latm"},
            {".m4u", "video/vnd.mpegurl"},
            {".m4v", "video/x-m4v"},
            {".mov", "video/quicktime"},
            {".mp2", "audio/x-mpeg"},
            {".mp3", "audio/x-mpeg"},
            {".mp4", "video/mp4"},
            {".mpc", "application/vnd.mpohun.certificate"},
            {".mpe", "video/mpeg"},
            {".mpeg", "video/mpeg"},
            {".mpg", "video/mpeg"},
            {".mpg4", "video/mp4"},
            {".mpga", "audio/mpeg"},
            {".msg", "application/vnd.ms-outlook"},
            {".ogg", "audio/ogg"},
            {".pdf", "application/pdf"},
            {".png", "image/png"},
            {".pps", "application/vnd.ms-powerpoint"},
            {".ppt", "application/vnd.ms-powerpoint"},
            {".pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation"},
            {".prop", "text/plain"},
            {".rc", "text/plain"},
            {".rmvb", "audio/x-pn-realaudio"},
            {".rtf", "application/rtf"},
            {".sh", "text/plain"},
            {".tar", "application/x-tar"},
            {".tgz", "application/x-compressed"},
            {".txt", "text/plain"},
            {".wav", "audio/x-wav"},
            {".wma", "audio/x-ms-wma"},
            {".wmv", "audio/x-ms-wmv"},
            {".wps", "application/vnd.ms-works"},
            {".xml", "text/plain"},
            {".z", "application/x-compress"},
            {".zip", "application/x-zip-compressed"},
            {"", "*/*"}
    };
    public static String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + Global.APPNAME + File.separator + "file";
    static SpinerPopWindow<String> spinerPopWindow;

    public static String getSDPath() {
        String sdPath = null;
        // 判断sd卡是否存在
        boolean sdCardExit = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (sdCardExit) {
            // 获取根目录
            sdPath = Environment.getExternalStorageDirectory().toString();
        }
        return sdPath;
    }

    /**
     * 下拉框选择
     */
    public static void SetSinnper(final List<String> list, final TextView textView,
                                  final Context context, final boolean states) {
        String s;
        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                textView.setText(list.get(position));
                spinerPopWindow.dismiss();
                if (states)
                    CommonUtils.setTextImage(R.drawable.arrow, textView, context);

            }
        };
        spinerPopWindow = new SpinerPopWindow<>(context, list, itemClickListener);
        spinerPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (states)
                    CommonUtils.setTextImage(R.drawable.arrow, textView, context);
            }
        });
        spinerPopWindow.setWidth(textView.getWidth());
        spinerPopWindow.showAsDropDown(textView);
        if (states)
            CommonUtils.setTextImage(R.drawable.arrow2, textView, context);
    }

    /**
     * 下拉框选择  天气预报城市
     */
    public static void SetCitySinnper(final List<String> list, final TextView textView,
                                  final Context context, final boolean states, final IGetFilterCity iGetFilterCity) {
        String s;
        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                textView.setText(list.get(position));
                spinerPopWindow.dismiss();
                if (states)
                    CommonUtils.setTextImage(R.drawable.arrow, textView, context);
                String string = list.get(position);
                iGetFilterCity.getFilterCities(string);

            }
        };
        spinerPopWindow = new SpinerPopWindow<>(context, list, itemClickListener);
        spinerPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (states)
                    CommonUtils.setTextImage(R.drawable.arrow, textView, context);
            }
        });
        spinerPopWindow.setWidth(textView.getWidth());
        spinerPopWindow.showAsDropDown(textView);
        if (states)
            CommonUtils.setTextImage(R.drawable.arrow2, textView, context);
    }



    public static void setTextImage(int resId, TextView textView, Context context) {
        Drawable drawable = context.getResources().getDrawable(resId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());// 必须设置图片大小，否则不显示
        textView.setCompoundDrawables(null, null, drawable, null);
    }


    //读取assets中的json文件
    public static String getJson(Context context, String fileName) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            AssetManager assetManager = context.getAssets();
            InputStream inputStream = assetManager.open(fileName);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = bufferedInputStream.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return baos.toString();
    }

    /**
     * 判断网络
     */
    public static boolean isNetConnected(Context context) {
        // 得到连接管理器对象
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            return isWifiConnected(context) || isMobileConnected(context);
        } else {
            return false;
        }
    }

    /**
     * 判断WIFI网络是否可用
     */
    public static boolean isWifiConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWiFiNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (mWiFiNetworkInfo != null) {
                return mWiFiNetworkInfo.isAvailable();

            }
        }
        return false;
    }

    /**
     * 判断MOBILE网络是否可用
     */
    public static boolean isMobileConnected(Context context) {

        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mMobileNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mMobileNetworkInfo != null) {
                return mMobileNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    // startActivity
    public static void startActivity_(Context context, Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(context, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }

    // Activity跳转
    public static void startActivity(Context context, Class<?> cls) {
        startActivity_(context, cls, null);
    }

    /**
     * 绘制图标右上角的未读消息数量显示
     *
     * @param context 上下文
     * @param icon    需要被添加的icon的资源ID
     * @param news    未读的消息数量
     * @return drawable
     */
    @SuppressWarnings("unused")
    public static Drawable displayNewsNumber(Context context, int icon, int news) {
        // 初始化画布
        int iconSize = (int) context.getResources().getDimension(
                android.R.dimen.app_icon_size);
        // Bitmap contactIcon = Bitmap.createBitmap(iconSize, iconSize,
        // Config.ARGB_8888);
        Bitmap iconBitmap = BitmapFactory.decodeResource(
                context.getResources(), icon);
        Canvas canvas = new Canvas(iconBitmap);
        // 拷贝图片
        Paint iconPaint = new Paint();
        iconPaint.setDither(true);// 防抖动
        iconPaint.setFilterBitmap(true);// 用来对Bitmap进行滤波处理
        Rect src = new Rect(0, 0, iconBitmap.getWidth(), iconBitmap.getHeight());
        Rect dst = new Rect(0, 0, iconBitmap.getWidth(), iconBitmap.getHeight());
        canvas.drawBitmap(iconBitmap, src, dst, iconPaint);
        // 启用抗锯齿和使用设备的文本字距
        Paint countPaint = new Paint(Paint.ANTI_ALIAS_FLAG
                | Paint.DEV_KERN_TEXT_FLAG);
        countPaint.setColor(Color.RED);
        canvas.drawCircle(iconSize - 13, 20, 10, countPaint);

        Paint textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        // textPaint.setTypeface(Typeface.DEFAULT_BOLD);
        textPaint.setTextSize(19f);
        canvas.drawText(String.valueOf(news), iconSize - 18, 27, textPaint);
        return new BitmapDrawable(iconBitmap);
    }

    /**
     * 打开文件
     */
    public static void openFile(File file, Context context) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);  //设置intent的Action属性
        String type = getMIMEType(file);  //获取文件file的MIME类型
        Log.i("", "onDownloadStart openFile: " + type + "   " + file.getAbsolutePath());
        intent.setDataAndType(/*uri*/Uri.fromFile(file), type);   //设置intent的data和Type属性。

        context.startActivity(intent);     //比如说你的MIME类型是打开邮箱，但是你手机里面没装邮箱客户端，就会报错。
    }

    private static String getMIMEType(File file) {

        String type = "*/*";
        String fName = file.getName();
        int dotIndex = fName.lastIndexOf(".");  //获取后缀名前的分隔符"."在fName中的位置。
        if (dotIndex < 0) {
            return type;
        }

        String end = fName.substring(dotIndex).toLowerCase();  /* 获取文件的后缀名*/
        if (end == "") return type;
        //在MIME和文件类型的匹配表中找到对应的MIME类型。
        for (int i = 0; i < MIME_MapTable.length; i++) { //MIME_MapTable??在这里你一定有疑问，这个MIME_MapTable是什么？
            if (end.equals(MIME_MapTable[i][0]))
                type = MIME_MapTable[i][1];
        }
        return type;
    }

    /**
     * 下载文件
     */
    public static void downloadNetFile(final String url) {
        //下载路径，如果路径无效了，可换成你的下载路径
        // final String url = "http://192.168.2.193:8002/Upload/2018年08月01日 08时37分32秒/【离】员工离职交接手续表.xlsx";
        Log.i("downloadNetFile", "DOWNLOAD url: " + url);
        Log.i("downloadNetFile", "DOWNLOAD filePath: " + filePath);
        if (!new File(filePath).exists()) {
            makeRootDirectory(filePath);
        }
        final long startTime = System.currentTimeMillis();
        Log.i("DOWNLOAD", "startTime=" + startTime);
        Request request = new Request.Builder().url(url).build();
        new OkHttpClient().newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                // 下载失败
                e.printStackTrace();
                Log.i("", "downloadNetFile onFailure: " + e.getMessage());
                ToastUtils.showToast(BaseApplication.getBaseApplication().getContext(), "获取文件失败~");
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                Log.i("", "downloadNetFile onResponse: " + response.body().string());
                Sink sink = null;
                BufferedSink bufferedSink = null;
                try {
                    String substring;
//                    if (url.contains(" ")) {
//                        substring = url.substring(url.lastIndexOf(" ") + 1).trim();
//                    } else {
//                        substring = url.substring(url.lastIndexOf("/") + 1).trim();
//                    }
                    substring = url.substring(url.lastIndexOf("/") + 1).trim();
                    File dest = new File(filePath, substring);
//                    File dest = new File(filePath, url.substring(url.lastIndexOf("/") + 1));
                    Log.i("downloadNetFile", "dest.getAbsolutePath(): " + dest.getAbsolutePath());


                    sink = Okio.sink(dest);
                    bufferedSink = Okio.buffer(sink);
                    bufferedSink.writeAll(response.body().source());
                    bufferedSink.close();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bufferedSink != null) {
                        bufferedSink.close();
                    }
                }
            }
        });


    }

    //保存字符串
    public static void SaveString(String path, String str) {
        try {
            if (new File(path).exists()) {
                new File(path).delete();
            }
            FileWriter fw = new FileWriter(path);
            fw.flush();
            fw.write(str);
            fw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 向某一路径下保存文件  txt
     */
    public static void saveStrToTxt(String filePath, String fileName, String string) {
        File file = new File(filePath);
        if (!file.exists() || !file.isDirectory()) {
            file.mkdirs();
        }
        String JsonPath = filePath + fileName;
        final File Jsonfile = new File(JsonPath);
        if (!Jsonfile.getParentFile().exists()) {//判断父文件是否存在，如果不存在则创建
            Jsonfile.getParentFile().mkdirs();
        }
        PrintStream out = null;
        try {
            out = new PrintStream(new FileOutputStream(Jsonfile));
            out.print(string);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.i("", "saveStrToTxt: " + e.getMessage());
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    // 生成文件夹
    public static void makeRootDirectory(String filePath) {
        File file = null;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdir();
            }
        } catch (Exception e) {
            Log.i("downloadNetFile 生成文件夹:", e + "");
        }
    }

    public static boolean dealImage(String sourceFilePath, String targetFilePath) {
        try {
            int dh = 1024;
            int dw = 1024;
            BitmapFactory.Options factory = new BitmapFactory.Options();
            factory.inJustDecodeBounds = true; //当为true时  允许查询图片不为 图片像素分配内存
            InputStream is = new FileInputStream(sourceFilePath);
            Bitmap bmp = BitmapFactory.decodeStream(is, null, factory);
            int hRatio = (int) Math.ceil(factory.outHeight / (float) dh); //图片是高度的几倍
            int wRatio = (int) Math.ceil(factory.outWidth / (float) dw); //图片是宽度的几倍
            System.out.println("hRatio:" + hRatio + "  wRatio:" + wRatio);
            //缩小到  1/ratio的尺寸和 1/ratio^2的像素
            if (hRatio > 1 || wRatio > 1) {
                if (hRatio > wRatio) {
                    factory.inSampleSize = hRatio;
                } else
                    factory.inSampleSize = wRatio;
            }
            factory.inJustDecodeBounds = false;
            is.close();
            is = new FileInputStream(sourceFilePath);
            bmp = BitmapFactory.decodeStream(is, null, factory);
            OutputStream outFile = new FileOutputStream(targetFilePath);
            bmp.compress(Bitmap.CompressFormat.JPEG, 60, outFile);
            outFile.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }




    /**
     * 拨打电话
     * */
    /**
     * 拨打电话（跳转到拨号界面，用户手动点击拨打）
     *
     * @param phoneNum 电话号码
     */
    public static void callPhone(Context context,String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        context.startActivity(intent);
    }
}
