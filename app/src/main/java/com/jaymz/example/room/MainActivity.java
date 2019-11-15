package com.jaymz.example.room;

import android.view.View;

import com.jaymz.example.room.base.BaseActivity;
import com.jaymz.example.room.databinding.ActivityMainBinding;
import com.jaymz.example.room.db.executor.AppExecutors;
import com.jaymz.example.room.db.repository.UserRepository;
import com.jaymz.example.room.db.room.User;
import com.jaymz.example.room.db.source.UserDataSource;
import com.jaymz.example.room.mvp.presenter.UserPresenter;
import com.jaymz.example.room.mvp.view.UserView;

public class MainActivity extends BaseActivity<ActivityMainBinding> implements UserView {

    private UserPresenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        UserRepository userRepository = new UserRepository(new AppExecutors(), UserDataSource.getInstance());
        mPresenter = new UserPresenter(userRepository, this);
        mPresenter.queryUser();
    }

    @Override
    public void showUserInfo(User user) {
        mDataBinding.tvUserInfo.setText(String.format(getString(R.string.user_info), user.getUserName(), user.getUserAge()));
    }

    @Override
    public void hideUserInfo() {
        mDataBinding.tvUserInfo.setText("");
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_insert_user:
                String userName = mDataBinding.etUserName.getText().toString().trim();
                String userAge = mDataBinding.etUserAge.getText().toString();
                if (userName.equals("") || userAge.equals("")) {
                    return;
                }
                int age = Integer.valueOf(userAge);
                mPresenter.insetOrUpdateUser(userName, age);
                break;
            case R.id.tv_delete_user:
                mPresenter.deleteAllUser();
                break;
        }
    }
}
