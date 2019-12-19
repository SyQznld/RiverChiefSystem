package com.appler.riverchiefsystem.base;

import rx.Subscriber;


public abstract class BaseResponseObserver<T> extends Subscriber<T> {

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onNext(T t) {

    }
}
