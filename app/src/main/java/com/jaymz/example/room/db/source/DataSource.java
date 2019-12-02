package com.jaymz.example.room.db.source;

import android.database.Cursor;

import com.jaymz.example.room.db.room.User;

/**
 * Created by Mazen on 2019/11/15
 * 数据源提供接口
 */
public interface DataSource {
    Cursor getUser();

    User getUserInfo();

    User getUserInfo(int userId);

    Long insertOrUpdateUser(User user);

    int deleteAllUser();

    int deleteIdUser(User user);
}
