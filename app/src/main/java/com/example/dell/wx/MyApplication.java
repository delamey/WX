package com.example.dell.wx;

import android.app.Application;
import android.content.Context;

import org.greenrobot.eventbus.EventBus;
import org.litepal.LitePal;

public class MyApplication extends Application {
private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        EventBus.builder().addIndex(new MyEventBusIndex()).installDefaultEventBus();
        NetworkManager.getInstance().init(context);
        LitePal.initialize(context);
    }
    public static Context getContext(){
        return context;
    }
}
