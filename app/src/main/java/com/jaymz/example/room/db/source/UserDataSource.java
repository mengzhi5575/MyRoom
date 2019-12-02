package com.jaymz.example.room.db.source;


import android.database.Cursor;

import com.jaymz.example.room.db.room.User;
import com.jaymz.example.room.db.room.UserDataBase;
import com.jaymz.example.room.db.room.dao.UserDao;
import com.jaymz.example.room.utils.AppUtils;

/**
 * Created by Mazen on 2019/11/15
 * 数据源提供类
 */
public class UserDataSource implements DataSource {
    private UserDao mUserDao;

    public UserDataSource() {
        this.mUserDao = UserDataBase.getInstance(AppUtils.getAppContext()).userDao();
    }

    /**
     * 单例模式
     *
     * @return
     */
    public static UserDataSource getInstance() {
        return Inner.instance;
    }

    private static class Inner {
        private static final UserDataSource instance = new UserDataSource();
    }

    @Override
    public Cursor getUser() {
        return mUserDao.getUser();
    }

    /**
     * 查询User数据库中的数据
     *
     * @return
     */
    @Override
    public User getUserInfo() {
        return mUserDao.getUserInfo();
    }

    @Override
    public User getUserInfo(int userId) {
        return mUserDao.getUserInfo(userId);
    }

    /**
     * User数据库中插入一条user数据
     *
     * @param user
     */
    @Override
    public Long insertOrUpdateUser(User user) {
        return mUserDao.insertUser(user);
    }

    @Override
    public int deleteAllUser() {
        return mUserDao.deleteAllUsers();
    }

    @Override
    public int deleteIdUser(User user) {
        return mUserDao.deleteIdUser(user);
    }

    /**
     * User数据库更新Age数据
     *
     * @param age
     * @param id
     */
    public void updateUserAge(int age, int id) {
        mUserDao.updateUserAge(age, id);
    }

    /**
     * User数据库更新Name数据
     *
     * @param name
     * @param id
     */
    public void updateUserName(String name, int id) {
        mUserDao.updateUserName(name, id);
    }

}
