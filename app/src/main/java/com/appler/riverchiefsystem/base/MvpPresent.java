package com.appler.riverchiefsystem.base;

import android.support.annotation.UiThread;


public interface MvpPresent<V> {


    /**
     * 绑定视图
     */
    @UiThread
    void attachView(V view);


    /**
     * 解除绑定（每个v使用完之后解绑，防止内存泄漏）
     */
    @UiThread
    void detachView();
}
