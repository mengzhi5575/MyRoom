package com.jaymz.example.room;

import android.app.Application;

import com.jaymz.example.room.utils.AppUtils;

public class RoomApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AppUtils.init(this);
    }
}
