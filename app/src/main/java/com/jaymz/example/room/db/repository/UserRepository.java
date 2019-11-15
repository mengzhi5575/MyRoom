package com.jaymz.example.room.db.repository;

import com.jaymz.example.room.db.callback.DeleteUserCallback;
import com.jaymz.example.room.db.callback.LoadUserCallback;
import com.jaymz.example.room.db.callback.UpdateUserCallback;
import com.jaymz.example.room.db.executor.AppExecutors;
import com.jaymz.example.room.db.room.User;
import com.jaymz.example.room.db.source.DataSource;
import com.jaymz.example.room.db.source.UserDataSource;

import java.lang.ref.WeakReference;

/**
 * Created by Mazen on 2019/11/15
 */
public class UserRepository {
    private DataSource mDataSource;
    private AppExecutors mAppExecutors;
    private User mCacheUser;

    public UserRepository(AppExecutors appExecutors, UserDataSource dataSource) {
        this.mAppExecutors = appExecutors;
        this.mDataSource = dataSource;
    }

    /**
     * 从数据源中获取用户数据，对其进行缓存，并通过回调方式通知已检索到的用户数据
     * 查询数据库数据不能在主线程中操作，所以调用线程池中的io线程
     *
     * @param callback
     */
    public void queryUser(final LoadUserCallback callback) {
        final WeakReference<LoadUserCallback> loadUserCallback = new WeakReference<>(callback);
        mAppExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                final User user = mDataSource.getUserInfo();
                mAppExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        final LoadUserCallback userCallback = loadUserCallback.get();
                        if (userCallback == null) {
                            return;
                        }
                        if (user == null) {
                            userCallback.onDataNotAvailable();
                        } else {
                            mCacheUser = user;
                            userCallback.onUserLoaded(user);
                        }
                    }
                });
            }
        });
    }

    /**
     * 向数据源中插入或者更新一条用户数据
     * 插入或者更新数据库数据不能在主线程中操作，所以调用线程池中的io线程
     *
     * @param userName
     * @param userAge
     * @param callback
     */
    public void insertOrUpdateUser(String userName, int userAge, UpdateUserCallback callback) {
        final WeakReference<UpdateUserCallback> updateUserCallback = new WeakReference<>(callback);
        //todo:当前缓存数据为null时,新建一个用户，否则更新当前缓存数据，通过保持ID相同的方式
        final User user = mCacheUser == null ?
                new User(userName, userAge) : new User(mCacheUser.getId(), userName, userAge);
        mAppExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDataSource.insertOrUpdateUser(user);
                mCacheUser = user;
                mAppExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        final UpdateUserCallback userCallback = updateUserCallback.get();
                        if (userCallback != null) {
                            userCallback.onUserUpdated(user);
                        }
                    }
                });
            }
        });
    }

    /**
     * 删除数据源中所有用户数据
     * 删除数据库数据不能在主线程中操作，所以调用线程池中的io线程
     */
    public void deleteUser(DeleteUserCallback callback) {
        final WeakReference<DeleteUserCallback> deleteUserCallback = new WeakReference<>(callback);
        mAppExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDataSource.deleteAllUser();
                mAppExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        final DeleteUserCallback userCallback = deleteUserCallback.get();
                        if (userCallback != null) {
                            userCallback.onUserDelete();
                        }
                    }
                });
            }
        });
    }
}
