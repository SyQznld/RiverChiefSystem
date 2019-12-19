package com.appler.riverchiefsystem.base.convert;

import com.google.gson.Gson;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;


public class ResponseConvertFactory extends Converter.Factory {

    private final Gson gson;

    private ResponseConvertFactory(Gson gson) {
        if (gson == null) throw new NullPointerException("gson == null");
        this.gson = gson;
    }

    public static ResponseConvertFactory create() {
        return create(new Gson());
    }

    public static ResponseConvertFactory create(Gson gson) {
        return new ResponseConvertFactory(gson);
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
//        return super.responseBodyConverter(type, annotations, retrofit);
        return new GsonResponsebobyConverter<>(gson, type);
    }
}
