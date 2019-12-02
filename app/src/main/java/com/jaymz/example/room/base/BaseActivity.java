package com.jaymz.example.room.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.trello.rxlifecycle3.components.support.RxAppCompatActivity;

public abstract class BaseActivity<T extends ViewDataBinding> extends RxAppCompatActivity {
    protected T mDataBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataBinding = DataBindingUtil.setContentView(this, getLayoutId());
        init();
    }

    protected abstract int getLayoutId();

    protected void init() {
    }
}
