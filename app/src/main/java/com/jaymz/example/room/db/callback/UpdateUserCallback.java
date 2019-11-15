package com.jaymz.example.room.db.callback;

import androidx.annotation.MainThread;

import com.jaymz.example.room.db.room.User;

/**
 * Created by Mazen on 2019/11/15
 */
public interface UpdateUserCallback {
    /**
     * 更新user数据时调用的方法
     *
     * @param user
     */
    @MainThread
    void onUserUpdated(User user);
}
