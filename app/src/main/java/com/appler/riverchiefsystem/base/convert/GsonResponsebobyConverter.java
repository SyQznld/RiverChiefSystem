package com.appler.riverchiefsystem.base.convert;

import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;


public class GsonResponsebobyConverter<T> implements Converter<ResponseBody, T> {

    private String TAG = this.getClass().getSimpleName();
    private Gson gson;
    private Type type;

    public GsonResponsebobyConverter(Gson gson, Type type) {
        this.gson = gson;
        this.type = type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String string = value.toString();
        String substring = string.substring(1, string.length() - 1);
        return gson.fromJson(substring, type);
    }
}
