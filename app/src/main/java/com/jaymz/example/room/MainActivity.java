package com.jaymz.example.room;

import android.content.ContentValues;
import android.database.ContentObserver;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.jaymz.example.room.base.BaseActivity;
import com.jaymz.example.room.databinding.ActivityMainBinding;
import com.jaymz.example.room.db.room.User;
import com.jaymz.example.room.mvp.presenter.UserRxPresenter;
import com.jaymz.example.room.mvp.view.UserView;
import com.jaymz.example.room.utils.UserProviderUtils;

public class MainActivity extends BaseActivity<ActivityMainBinding> implements UserView {
    private static final String TAG = "MainActivity";
    private UserRxPresenter mPresenter;

    ContentObserver userObserver = new ContentObserver(new Handler()) {
        @Override
        public void onChange(boolean selfChange) {
            Log.d(TAG, "accountObserver onChange selfChange: " + selfChange);
            super.onChange(selfChange);
            mPresenter.queryUser();

        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        getContentResolver().registerContentObserver(UserProviderUtils.getUri("user_info"), true, userObserver);
        mPresenter = new UserRxPresenter(this);
        mPresenter.queryUser();
    }

    @Override
    protected void onDestroy() {
        getContentResolver().unregisterContentObserver(userObserver);
        super.onDestroy();
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
                String userAge = mDataBinding.etUserAge.getText().toString().trim();
                if (userName.equals("") || userAge.equals("")) {
                    return;
                }
                int age = Integer.valueOf(userAge);
                ContentValues contentValues = new ContentValues();
                contentValues.put("user_name", userName);
                contentValues.put("user_age", age);
                mPresenter.insertOrUpdate("user_info", contentValues);
                break;
            case R.id.tv_delete_user:
                mPresenter.deleteUser("user_info", null);
                break;
        }
    }

}
