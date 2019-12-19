package com.appler.riverchiefsystem.base;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.appler.riverchiefsystem.global.Global;

import butterknife.ButterKnife;


public abstract class BaseActivity extends FragmentActivity implements BaseView {

    private String TAG = this.getClass().getSimpleName();
    private BaseMvpPresenter baseMvpPresenter;
    private Toast toast;
    private MaterialDialog loadDialog;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(bindLayout());
        ButterKnife.bind(this);
        baseMvpPresenter = new BaseMvpPresenter();
        doBuissness(this);
    }


    /**
     * 绑定布局
     * 抽象方法
     * 必须实现的方法
     */
    protected abstract int bindLayout();


    /**
     * 业务操作
     */
    protected abstract void doBuissness(Context context);


    @Override
    public void setState(int state) {
        switch (state) {
            case Global.LOADING_DOING:  //正在加载
                loadDialog = new MaterialDialog.Builder(this)
                        .content("正在加载")
                        .progress(true, 0)
                        .progressIndeterminateStyle(false)
                        .show();
                break;
            case Global.LOADING_SUCCESS:  //加载成功
                if (loadDialog != null) {
                    loadDialog.setContent("加载成功");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            loadDialog.dismiss();
                        }
                    }, 500);
                }
                break;
            case Global.LOADING_FAIL:  //加载失败
                if (loadDialog != null) {
                    loadDialog.setContent("加载失败,请点击重试");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            loadDialog.dismiss();
                        }
                    }, 1000);
                }
                break;
        }
    }

    public String getIMEI(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return "null";
        }
        String imei = telephonyManager.getDeviceId();

        return imei;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        baseMvpPresenter.detachView();
    }

    public void showToast(String message) {
        if (toast != null) {
            toast.cancel();
            toast = null;
        }
        toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }


}
