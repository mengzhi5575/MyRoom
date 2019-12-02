package com.jaymz.example.room;

import android.content.ContentValues;
import android.database.ContentObserver;
import android.graphics.Rect;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.jaymz.example.room.base.BaseActivity;
import com.jaymz.example.room.databinding.ActivityMainBinding;
import com.jaymz.example.room.db.room.User;
import com.jaymz.example.room.mvp.presenter.UserRxPresenter;
import com.jaymz.example.room.mvp.view.UserView;
import com.jaymz.example.room.provider.config.UriConfig;
import com.jaymz.example.room.utils.InputMethodUtils;
import com.jaymz.example.room.utils.UserInfoCheckUtils;
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
        getContentResolver().registerContentObserver(UserProviderUtils.getUri(UriConfig.S_USER_TABLE_NAME), true, userObserver);
        mPresenter = new UserRxPresenter(this);
        mPresenter.queryUser();
        addTextWatcher();
    }

    @Override
    protected void onDestroy() {
        getContentResolver().unregisterContentObserver(userObserver);
        super.onDestroy();
    }

    @Override
    public void showUserInfo(User user) {
        mDataBinding.tvUserInfo.setText(String.format(getString(R.string.user_info), user.getUserName(), user.getUserAge()));
        setDeleteBtnStatus();
    }

    @Override
    public void hideUserInfo() {
        mDataBinding.tvUserInfo.setText("");
        setDeleteBtnStatus();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_insert_user:
                String userName = mDataBinding.etUserName.getText().toString().trim();
                int userAge = Integer.valueOf(mDataBinding.etUserAge.getText().toString().trim());
                if (UserInfoCheckUtils.validateUserName(userName) && UserInfoCheckUtils.validateUserAge(userAge)) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("user_name", userName);
                    contentValues.put("user_age", userAge);
                    mPresenter.insertOrUpdate(UriConfig.S_USER_TABLE_NAME, contentValues);
                } else {
                    Toast.makeText(this, getString(R.string.toast_tip_user_info_invalidate), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_delete_user:
                mPresenter.deleteUser(UriConfig.S_USER_TABLE_NAME, null);
                break;
            case R.id.cl_main:
                if (getCurrentSoftInputHeight() > 200) {
                    InputMethodUtils.hideSoftInputMethod(this, getCurrentFocus());
                }
                break;
        }
    }

    /**
     * 输入框监听器
     */
    private void addTextWatcher() {
        mDataBinding.etUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                setInsertBtnStatus();
            }
        });
        mDataBinding.etUserAge.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                setInsertBtnStatus();
            }
        });
    }

    /**
     * 设置插入按钮的状态
     */
    private void setInsertBtnStatus() {
        if (isInputNull()) {
            mDataBinding.tvInsertUser.setEnabled(false);
        } else {
            mDataBinding.tvInsertUser.setEnabled(true);
        }
    }

    /**
     * 设置删除按钮的状态
     */
    private void setDeleteBtnStatus() {
        if (mPresenter.getCacheUser() == null) {
            mDataBinding.tvDeleteUser.setEnabled(false);
        } else {
            mDataBinding.tvDeleteUser.setEnabled(true);
        }
    }

    /**
     * 检测注册输入框内容是否为空
     *
     * @return
     */
    private boolean isInputNull() {
        String userName = mDataBinding.etUserName.getText().toString().trim();
        String userAge = mDataBinding.etUserAge.getText().toString().trim();
        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(userAge)) {
            return true;
        }
        return false;
    }

    /**
     * 得到当前软键盘的高度
     *
     * @return 软键盘的高度
     */
    public int getCurrentSoftInputHeight() {
        final View decorView = getWindow().getDecorView();
        Rect rect = new Rect();
        decorView.getWindowVisibleDisplayFrame(rect);
        // 获取屏幕的高度(包括状态栏，导航栏)
        int screenHeight = decorView.getRootView().getHeight();
        int keySoftHeight = screenHeight - rect.bottom;
        return keySoftHeight;
    }
}
