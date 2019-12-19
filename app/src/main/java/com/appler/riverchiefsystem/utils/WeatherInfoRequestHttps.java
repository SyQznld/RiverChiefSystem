package com.appler.riverchiefsystem.utils;

import android.util.Log;

import com.appler.riverchiefsystem.global.Global;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * 接口交互 数据请求
 */
public class WeatherInfoRequestHttps {

    private String TAG = getClass().getSimpleName();

    public static WeatherInfoRequestHttps instance;

    public static WeatherInfoRequestHttps getInstance() {
        if (null == instance) {
            synchronized (WeatherInfoRequestHttps.class) {
                if (null == instance) {
                    instance = new WeatherInfoRequestHttps();
                }
            }
        }
        return instance;
    }

    private WeatherInfoRequestHttps() {
    }


    /**
     * 根据GPS坐标自动定位城市，并返回该城市的天气信息
     * http://v.juhe.cn/weather/geo?format=2&key=您申请的KEY&lon=116.39277&lat=39.933748
     */
    public interface GetCityWeatherInfoByGpsCallBack {
        void onFailure(String string);

        void getCityWeatherInfo(String string);
    }


    public void requestCityWeatherByGps(String longitude, String latitude, final GetCityWeatherInfoByGpsCallBack getCityWeatherInfoByGpsCallBack) {

        final OkHttpClient client = new OkHttpClient();
        Request.Builder reqBuild = new Request.Builder();
        HttpUrl.Builder urlBuilder = HttpUrl.parse("http://v.juhe.cn/weather/geo?format=2")
                .newBuilder();
        urlBuilder.addQueryParameter("key", Global.WEATHER_APPKEY);
        urlBuilder.addQueryParameter("lon", longitude);
        urlBuilder.addQueryParameter("lat", latitude);
        reqBuild.url(urlBuilder.build());
        client.newCall(reqBuild.build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getCityWeatherInfoByGpsCallBack.onFailure(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                getCityWeatherInfoByGpsCallBack.getCityWeatherInfo(string);

            }
        });
    }


    /**
     * 根据城市名查询天气
     * http://v.juhe.cn/weather/index?format=2&cityname=%E8%8B%8F%E5%B7%9E&key=您申请的KEY
     */
    public interface GetWeatherInfoByCityNameCallBack {
        void onFailure(String string);

        void getInfoByCityName(String string);
    }

    public void requestWeatherByCityName(String cityName, final GetWeatherInfoByCityNameCallBack getWeatherInfoByCityNameCallBack) {
        Log.i(TAG, "requestWeatherByCityName: " + cityName);
        final OkHttpClient client = new OkHttpClient();
        Request.Builder reqBuild = new Request.Builder();
        HttpUrl.Builder urlBuilder = HttpUrl.parse("http://v.juhe.cn/weather/geo?format=2")
                .newBuilder();
        urlBuilder.addQueryParameter("cityname", cityName);
        urlBuilder.addQueryParameter("key", Global.WEATHER_APPKEY);
        reqBuild.url(urlBuilder.build());

        client.newCall(reqBuild.build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getWeatherInfoByCityNameCallBack.onFailure(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();

                getWeatherInfoByCityNameCallBack.getInfoByCityName(string);

            }
        });
    }


    /**
     * 获取城市信息
     * http://v.juhe.cn/weather/citys?key=612d9c53c4ddf4a76746270eab7ccf8a
     */
    public interface GetAllCitysCallBack {
        void onFailure(String string);

        void getAllCitys(String string);
    }

    public void requestAllCitys(final GetAllCitysCallBack getAllCitysCallBack) {
        final OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://v.juhe.cn/weather/citys?key=" + Global.WEATHER_APPKEY) //url网址配置
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getAllCitysCallBack.onFailure(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();

                getAllCitysCallBack.getAllCitys(string);

            }
        });
    }


    /**
     * 标注图层  返回图层类型
     */
    public interface MarkersTypeDataBack {
        void onFailure();

        void getMarkersType(String string);
    }

    public void requestMarkersType(final MarkersTypeDataBack markersTypeDataBack) {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        requestBody.addFormDataPart("flag", "TreeType");

        Request request = new Request.Builder()
                .url(Global.URL)
                .post(requestBody.build())
                .build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i(TAG, " requestMarkersType onFailure: " + e.getMessage());
                markersTypeDataBack.onFailure();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Log.i(TAG, "requestMarkersType onResponse: " + string);
                if ("".equals(string) || "error".equals(string) || "Error".equals(string)) {
                    markersTypeDataBack.onFailure();
                } else {
                    markersTypeDataBack.getMarkersType(string);
                }

            }
        });
    }


    /**
     * 标注图层  返回数据坐标及id
     */
    public interface MarkersLayerDataBack {
        void onFailure();

        void getMarkersList(String string);
    }

    public void requestMarkersList(String bz_type, final MarkersLayerDataBack markersLayerDataBack) {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        requestBody.addFormDataPart("flag", "showMarkerLayer");
        requestBody.addFormDataPart("bz_type", bz_type);

        Request request = new Request.Builder()
                .url(Global.URL)
                .post(requestBody.build())
                .build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i(TAG, " requestMarkersList onFailure: " + e.getMessage());
                markersLayerDataBack.onFailure();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Log.i(TAG, "requestMarkersList onResponse: " + string);
                if ("".equals(string) || "error".equals(string) || "Error".equals(string)) {
                    markersLayerDataBack.onFailure();
                } else {
                    markersLayerDataBack.getMarkersList(string);
                }

            }
        });
    }


    /**
     * 标注图层  点击后查看详情
     */
    public interface ShpLayerDetailDataBack {
        void onFailure();

        void getShpLayerDetail(String string);
    }


    public void requestShpLayerDetail(String bz_type, String ogr_fid, final ShpLayerDetailDataBack shpLayerDetailDataBack) {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        requestBody.addFormDataPart("flag", "shpDetail");
        requestBody.addFormDataPart("bz_type", bz_type);
        requestBody.addFormDataPart("ogr_fid", ogr_fid);

        Request request = new Request.Builder()
                .url(Global.URL)
                .post(requestBody.build())
                .build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i(TAG, " requestShpLayerDetail onFailure: " + e.getMessage());
                shpLayerDetailDataBack.onFailure();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Log.i(TAG, "requestShpLayerDetail onResponse: " + string);
                if ("".equals(string) || "error".equals(string) || "Error".equals(string)) {
                    shpLayerDetailDataBack.onFailure();
                } else {
                    shpLayerDetailDataBack.getShpLayerDetail(string);
                }

            }
        });
    }


    /**
     * 根据登录权限显示不同图层列表
     */
    public interface ZxLayersByRoleCallBack {
        void onFailure();

        void ZxLayersByRoleListBack(String string);
    }


    public void requestZxLayersListByRole(String UserId, final ZxLayersByRoleCallBack zxLayersByRoleCallBack) {
        Log.i(TAG, "requestZxLayersListByRole Layers: " + UserId);
        OkHttpClient mOkHttpClient = new OkHttpClient();
        MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        requestBody.addFormDataPart("flag", "showLayers");
        requestBody.addFormDataPart("UserId", UserId);
//        requestBody.addFormDataPart("Layers", Layers);

        Request request = new Request.Builder()
                .url(Global.URL)
                .post(requestBody.build())
                .build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i(TAG, "requestZxLayersListByRole onFailure: " + e.getMessage());
                zxLayersByRoleCallBack.onFailure();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Log.i(TAG, "requestZxLayersListByRole onResponse: " + string);
                if ("".equals(string) || "error".equals(string) || "Error".equals(string)) {
                    zxLayersByRoleCallBack.onFailure();
                } else {
                    zxLayersByRoleCallBack.ZxLayersByRoleListBack(string);
                }

            }
        });
    }


    /**
     * 修改密码
     */
    public interface ResetPasswordCallBack {
        void onFailure();

        void resetPassword(String string);
    }


    public void resetPwd(String username, String O_password, String N_password, final ResetPasswordCallBack resetPasswordCallBack) {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        requestBody.addFormDataPart("flag", "updatePW");
        requestBody.addFormDataPart("username", username);
        requestBody.addFormDataPart("O_password", O_password);
        requestBody.addFormDataPart("N_password", N_password);

        Request request = new Request.Builder()
                .url(Global.URL)
                .post(requestBody.build())
                .build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i(TAG, "resetPwd onFailure: " + e.getMessage());
                resetPasswordCallBack.onFailure();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Log.i(TAG, "resetPwd onResponse: " + string);
                if ("".equals(string) || "error".equals(string) || "Error".equals(string)) {
                    resetPasswordCallBack.onFailure();
                } else {
                    resetPasswordCallBack.resetPassword(string);
                }

            }
        });
    }


}
