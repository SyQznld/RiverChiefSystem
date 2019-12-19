package com.appler.riverchiefsystem.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

/**
 * 定位 工具类
 */
public class LocationUtils {
    private String TAG = getClass().getSimpleName();
    private double longitude, latitude;
    private LocationManager lm;

    private String lnglat;  //点击后获取到的经纬度坐标

    /**
     * 定位基本设置
     */
    public String initGpsSetting(final Activity activity) {
        lm = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        if (!isGpsAble(lm)) {
            Toast.makeText(activity, "请打开GPS~", Toast.LENGTH_SHORT).show();
            openGPS2(activity);
        }
        //从GPS获取最近的定位信息
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            return true;

//        return false;
            return null;
        }

        Location lc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        lnglat = updateShow(lc);
        //设置间隔两秒获得一次GPS定位信息
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 5, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                // 当GPS定位信息发生改变时，更新定位
                lnglat = updateShow(location);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {
                // 当GPS LocationProvider可用时，更新定位
                if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                lnglat = updateShow(lm.getLastKnownLocation(provider));
            }

            @Override
            public void onProviderDisabled(String provider) {
                lnglat = updateShow(null);
            }
        });
        return lnglat;
    }


    //定义一个更新显示的方法
    public String updateShow(Location location) {
        if (location != null) {
            longitude = location.getLongitude();
            latitude = location.getLatitude();
            return longitude + "," + latitude;
        } else {
            longitude = 0.0;
            latitude = 0.0;
            return "";
        }
    }

    //gps是否可用
    private boolean isGpsAble(LocationManager lm) {
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }


    //打开设置页面让用户自己设置
    private void openGPS2(Activity activity) {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        activity.startActivityForResult(intent, 0);
    }
}
