package com.appler.riverchiefsystem.base;


import com.appler.riverchiefsystem.BuildConfig;
import com.appler.riverchiefsystem.api.ApiService;
import com.appler.riverchiefsystem.base.convert.ResponseConvertFactory;
import com.appler.riverchiefsystem.global.Global;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;


public class BaseRetrofit {

    private static ApiService apiService;
    private long TIMEOUT_CONNECT = 1000 * 10 * 3;
    private String url = Global.URL;

    public BaseRetrofit() {
    }

    public Retrofit getRetrofit() {
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(url)
                .client(getHttpClient())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())   //rxjava适配器
                .addConverterFactory(ResponseConvertFactory.create()); //json转换器
        return builder.build();
    }

    public ApiService getApiService() {
        if (apiService == null) {
            apiService = getRetrofit().create(ApiService.class);
        }
        return apiService;
    }


    private OkHttpClient getHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(TIMEOUT_CONNECT, TimeUnit.MILLISECONDS)
                .addInterceptor(new HttpLoggingInterceptor()
                        .setLevel(BuildConfig.DEBUG ?
                                HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE));
        return builder.build();
    }


}
