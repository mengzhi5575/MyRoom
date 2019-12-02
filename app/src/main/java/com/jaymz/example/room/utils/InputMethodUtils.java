package com.jaymz.example.room.utils;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.Nullable;

public class InputMethodUtils {
    /**
     * 隐藏输入法
     *
     * @param context
     * @param view
     */
    public static void hideSoftInputMethod(@Nullable Context context, View view) {
        Log.d("InputMethodUtils", "hideSoftInputMethod");
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null && view.getWindowToken() != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
