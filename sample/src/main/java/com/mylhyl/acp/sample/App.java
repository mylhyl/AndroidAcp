package com.mylhyl.acp.sample;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by hupei on 2016/9/5.
 */
public class App extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
    }
}
