package com.appler.riverchiefsystem.utils;

import android.os.Handler;
import android.os.Looper;

public class MainHandler extends Handler {
    private static volatile MainHandler instance;

    private MainHandler() {
        super(Looper.getMainLooper());
    }

    public static MainHandler getInstance() {
        if (null == instance) {
            synchronized (MainHandler.class) {
                if (null == instance) {
                    instance = new MainHandler();
                }
            }
        }
        return instance;
    }
}
