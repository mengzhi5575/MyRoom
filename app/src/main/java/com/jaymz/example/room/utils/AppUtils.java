package com.jaymz.example.room.utils;

import android.content.Context;

public class AppUtils {
    private static Context mContext;

    public static void init(Context context) {
        mContext = context;
    }

    public static Context getAppContext() {
        return mContext;
    }
}
