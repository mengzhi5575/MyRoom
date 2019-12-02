package com.jaymz.example.room.mvp.presenter;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.util.Log;

import com.jaymz.example.room.base.IPresenter;
import com.jaymz.example.room.db.repository.UserRxRepository;
import com.jaymz.example.room.db.room.User;
import com.jaymz.example.room.mvp.view.UserView;
import com.jaymz.example.room.utils.UserProviderUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public class UserRxPresenter implements IPresenter {
    private static String TAG = "UserRxPresenter";
    private UserView mUserView;
    private User mCacheUser;

    public UserRxPresenter(UserView userView) {
        this.mUserView = userView;
    }

    /**
     * 插入或更新用户信息
     *
     * @param tableName
     * @param values
     */
    public void insertOrUpdate(String tableName, ContentValues values) {
        UserProviderUtils.insertAccount(UserProviderUtils.getUri(tableName), values);
    }

    /**
     * 删除用户信息
     *
     * @param tableName
     * @param userId
     */
    public void deleteUser(String tableName, String userId) {
        UserProviderUtils.deleteAccount(UserProviderUtils.getUri(tableName), userId);
    }

    /**
     * 查询用户信息
     */
    @SuppressLint("CheckResult")
    public void queryUser() {
        UserRxRepository.getInstance().queryUserInfo()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<User>() {
                    @Override
                    public void accept(User user) throws Exception {
                        if (user != null) {
                            String userName = user.getUserName();
                            int userAge = user.getUserAge();
                            mUserView.showUserInfo(user);
                        } else {
                            mUserView.hideUserInfo();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mUserView.hideUserInfo();
                        Log.e(TAG, "throwable: " + throwable.getCause());
                    }
                });
    }
}
