package com.jaymz.example.room.db.source;

import com.jaymz.example.room.db.room.User;

/**
 * Created by Mazen on 2019/11/15
 * 数据源提供类
 */
public interface DataSource {
    User getUserInfo();

    void insertOrUpdateUser(User user);

    void deleteAllUser();
}
