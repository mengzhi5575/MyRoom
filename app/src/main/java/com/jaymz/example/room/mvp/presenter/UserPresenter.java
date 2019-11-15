package com.jaymz.example.room.mvp.presenter;

import androidx.annotation.Nullable;

import com.jaymz.example.room.base.IPresenter;
import com.jaymz.example.room.db.callback.DeleteUserCallback;
import com.jaymz.example.room.db.callback.LoadUserCallback;
import com.jaymz.example.room.db.callback.UpdateUserCallback;
import com.jaymz.example.room.db.repository.UserRepository;
import com.jaymz.example.room.db.room.User;
import com.jaymz.example.room.mvp.view.UserView;

public class UserPresenter implements IPresenter {
    @Nullable
    private UserView mView;

    private UserRepository mUserRepository;

    private UpdateUserCallback mUpdateUserCallback;
    private LoadUserCallback mLoadUserCallback;
    private DeleteUserCallback mDeleteUserCallback;

    public UserPresenter(UserRepository repository, UserView view) {
        mUserRepository = repository;
        mView = view;
        mUpdateUserCallback = createUpdateUserCallback();
        mLoadUserCallback = createLoadUserCallback();
        mDeleteUserCallback = createDeleteUserCallback();
    }

    /**
     * 插入或更新User信息
     *
     * @param userName
     * @param userAge
     */
    public void insetOrUpdateUser(final String userName, final int userAge) {
        mUserRepository.insertOrUpdateUser(userName, userAge, mUpdateUserCallback);
    }

    /**
     * 查询User缓存
     */
    public void queryUser() {
        mUserRepository.queryUser(mLoadUserCallback);
    }

    /**
     * 删除所有User缓存
     */
    public void deleteAllUser() {
        mUserRepository.deleteUser(mDeleteUserCallback);
    }

    /**
     * 创建更新回调
     *
     * @return
     */
    private UpdateUserCallback createUpdateUserCallback() {
        return new UpdateUserCallback() {
            @Override
            public void onUserUpdated(User user) {
                mView.showUserInfo(user);
            }
        };
    }

    /**
     * 创建加载回调
     *
     * @return
     */
    private LoadUserCallback createLoadUserCallback() {
        return new LoadUserCallback() {
            @Override
            public void onUserLoaded(User user) {
                mView.showUserInfo(user);
            }

            @Override
            public void onDataNotAvailable() {
                mView.hideUserInfo();
            }
        };
    }

    /**
     * 创建删除回调
     *
     * @return
     */
    private DeleteUserCallback createDeleteUserCallback() {
        return new DeleteUserCallback() {
            @Override
            public void onUserDelete() {
                mView.hideUserInfo();
            }
        };
    }
}
