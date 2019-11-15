package com.jaymz.example.room.db.callback;

import androidx.annotation.MainThread;

import com.jaymz.example.room.db.room.User;

/**
 * Created by Mazen on 2019/11/15
 */
public interface LoadUserCallback {
    /**
     * 数据库中数据不为空时调用
     *
     * @param user
     */
    @MainThread
    void onUserLoaded(User user);

    /**
     * 数据库中数据为空时调用
     */
    @MainThread
    void onDataNotAvailable();
}
