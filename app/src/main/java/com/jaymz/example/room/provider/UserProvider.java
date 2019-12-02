package com.jaymz.example.room.provider;

import android.annotation.SuppressLint;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jaymz.example.room.db.repository.UserRepository;
import com.jaymz.example.room.db.repository.UserRxRepository;
import com.jaymz.example.room.db.room.User;
import com.jaymz.example.room.provider.config.UriConfig;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public class UserProvider extends ContentProvider {
    private static final String TAG = "UserProvider";
    private static UriMatcher mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        mUriMatcher.addURI(UriConfig.S_USER_AUTHORITY, UriConfig.S_USER_TABLE_NAME, UriConfig.S_USER_TABLE_CODE);
    }

    @Override
    public boolean onCreate() {
        Log.d(TAG, "onCreate.");
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        Log.d(TAG, "query.");
        Cursor cursor = null;
        switch (getType(uri)) {
            case UriConfig.S_USER_TABLE_NAME:
                cursor = UserRepository.getInstance().queryUser();
                break;
            default:
                break;
        }
        Log.d(TAG, "query: cursor: " + cursor);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        Log.d(TAG, "getType.");
        String tableName = "";
        switch (mUriMatcher.match(uri)) {
            case UriConfig.S_USER_TABLE_CODE:
                tableName = UriConfig.S_USER_TABLE_NAME;
                break;
        }
        Log.d(TAG, "tableName: " + tableName);
        return tableName;
    }

    @SuppressLint("CheckResult")
    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        Log.d(TAG, "insert.");
        switch (getType(uri)) {
            case UriConfig.S_USER_TABLE_NAME:
                User user = new User.Builder()
                        .setUserName(contentValues.getAsString("user_name"))
                        .setUserAge(contentValues.getAsInteger("user_age"))
                        .build();
                UserRxRepository.getInstance().insertOrUpdate(user)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Long>() {
                            @Override
                            public void accept(Long aLong) throws Exception {
                                Log.d(TAG, "insert success aLong: " + aLong.intValue());
                                getContext().getContentResolver().notifyChange(uri, null);
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Log.e(TAG, "insert throwable: " + throwable.getCause());
                            }
                        });
                break;
        }
        return null;
    }

    @SuppressLint("CheckResult")
    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        Log.d(TAG, "delete.");
        switch (getType(uri)) {
            case UriConfig.S_USER_TABLE_NAME:
                if (s == null) {
                    UserRxRepository.getInstance().deleteUsers()
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<Integer>() {
                                @Override
                                public void accept(Integer integer) throws Exception {
                                    Log.d(TAG, "delete deleteUsers success " + integer.intValue());
                                    getContext().getContentResolver().notifyChange(uri, null);
                                }
                            }, new Consumer<Throwable>() {
                                @Override
                                public void accept(Throwable throwable) throws Exception {
                                    Log.e(TAG, "delete throwable: " + throwable.getCause());
                                }
                            });
                } else {
                    UserRxRepository.getInstance().deleteIdUser()
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<Integer>() {
                                @Override
                                public void accept(Integer integer) throws Exception {
                                    Log.d(TAG, "delete deleteIdUser success " + integer.intValue());
                                    getContext().getContentResolver().notifyChange(uri, null);
                                }
                            }, new Consumer<Throwable>() {
                                @Override
                                public void accept(Throwable throwable) throws Exception {
                                    Log.e(TAG, "delete throwable: " + throwable.getCause());
                                }
                            });
                }
                break;
        }
        return 0;
    }

    @SuppressLint("CheckResult")
    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        Log.d(TAG, "update.");
        switch (getType(uri)) {
            case UriConfig.S_USER_TABLE_NAME:
                User user = new User.Builder()
                        .setUserName(contentValues.getAsString("user_name"))
                        .setUserAge(contentValues.getAsInteger("user_age"))
                        .build();
                UserRxRepository.getInstance().insertOrUpdate(user)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Long>() {
                            @Override
                            public void accept(Long aLong) throws Exception {
                                Log.d(TAG, "update success " + aLong.intValue());
                                getContext().getContentResolver().notifyChange(uri, null);
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Log.e(TAG, "update throwable: " + throwable.getCause());
                            }
                        });
                break;
        }
        return 0;
    }
}
