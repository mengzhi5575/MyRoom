package com.jaymz.example.room.utils;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.jaymz.example.room.provider.config.UriConfig;

public class UserProviderUtils {
    public static Uri getUri(String tableName) {
        return Uri.parse("content://" + UriConfig.S_USER_AUTHORITY + "/"
                + tableName);
    }

    /**
     * 查询表中账号数据
     *
     * @param uri
     * @return
     */
    public static Cursor queryAccount(Uri uri) {
        ContentResolver resolver = AppUtils.getAppContext().getContentResolver();
        Cursor cursor = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            cursor = resolver.query(uri, null, null, null);
        }
        return cursor;
    }

    /**
     * 向表中插入账号数据
     *
     * @param uri
     * @param values
     */
    public static void insertAccount(Uri uri, ContentValues values) {
        ContentResolver resolver = AppUtils.getAppContext().getContentResolver();
        resolver.insert(uri, values);
    }

    /**
     * 删除表中账号数据
     *
     * @param uri
     */
    public static void deleteAccount(Uri uri, String userId) {
        ContentResolver resolver = AppUtils.getAppContext().getContentResolver();
        resolver.delete(uri, userId, null);
    }

    /**
     * 更新表中账号全部数据
     *
     * @param uri
     * @param values
     */
    public static void updateAccount(Uri uri, ContentValues values) {
        ContentResolver resolver = AppUtils.getAppContext().getContentResolver();
        resolver.update(uri, values, null, null);
    }
}
