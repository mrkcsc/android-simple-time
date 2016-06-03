package com.miguelgaeta.android_simple_time;

import android.app.Application;

import com.miguelgaeta.simple_time.SimpleTime;

public class AppApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        SimpleTime.init(this);
    }
}
