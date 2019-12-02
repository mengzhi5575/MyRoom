package com.jaymz.example.room.db.repository;

import android.nfc.Tag;
import android.util.Log;

import com.jaymz.example.room.db.room.User;
import com.jaymz.example.room.db.source.DataSource;
import com.jaymz.example.room.db.source.UserDataSource;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Create bt Mazen on2019/11/29
 */
public class UserRxRepository {
    private static final String TAG = "UserRxRepository";
    private DataSource mDataSource;
    private User mCacheUser;

    public static UserRxRepository getInstance() {
        return Inner.instance;
    }

    private static class Inner {
        private static final UserRxRepository instance = new UserRxRepository();
    }

    public UserRxRepository() {
        this.mDataSource = UserDataSource.getInstance();
    }

    /**
     * 插入或更新一条用户数据
     *
     * @param user
     * @return
     */
    public Flowable<Long> insertOrUpdate(User user) {
        return Flowable.just(user)
                .subscribeOn(Schedulers.io())
                .map(new Function<User, Long>() {
                    @Override
                    public Long apply(User user) throws Exception {
                        final User userInfo = mCacheUser == null ?
                                new User.Builder()
                                        .setFirst(true)
                                        .setUserName(user.getUserName())
                                        .setUserAge(user.getUserAge())
                                        .build() :
                                new User.Builder()
                                        .setUserId(mCacheUser.getId())
                                        .setUserName(user.getUserName())
                                        .setUserAge(user.getUserAge())
                                        .build();
                        mCacheUser = userInfo;
                        Log.d(TAG, "insertOrUpdate mCacheUser: " + mCacheUser);
                        return mDataSource.insertOrUpdateUser(userInfo);
                    }
                });
    }

    /**
     * 查询数据库中用户数据
     *
     * @return
     */
    public Flowable<User> queryUserInfo() {
        return Flowable.just(1)
                .subscribeOn(Schedulers.io())
                .map(new Function<Integer, User>() {
                    @Override
                    public User apply(Integer integer) throws Exception {
                        mCacheUser = mDataSource.getUserInfo();
                        Log.d(TAG, "queryUserInfo mCacheUser: " + mCacheUser);
                        return mCacheUser;
                    }
                });

    }

    /**
     * 根据用户ID查询用户信息
     *
     * @param userId
     * @return
     */
    public Flowable<User> queryUserInfo(int userId) {
        return Flowable.just(userId)
                .subscribeOn(Schedulers.io())
                .map(new Function<Integer, User>() {
                    @Override
                    public User apply(Integer integer) throws Exception {
                        mCacheUser = mDataSource.getUserInfo(integer);
                        return mCacheUser;
                    }
                });
    }

    /**
     * 删除数据库中用户数据
     *
     * @return
     */
    public Flowable<Integer> deleteUsers() {
        return Flowable.just(1)
                .subscribeOn(Schedulers.io())
                .map(new Function<Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer) throws Exception {
                        mCacheUser = null;
                        return mDataSource.deleteAllUser();
                    }
                });
    }

    /**
     * 根据用户ID删除指定用户
     *
     * @return
     */
    public Flowable<Integer> deleteIdUser() {
        return Flowable.just(1)
                .subscribeOn(Schedulers.io())
                .map(new Function<Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer) throws Exception {
                        return mDataSource.deleteIdUser(mCacheUser);
                    }
                });
    }
}
