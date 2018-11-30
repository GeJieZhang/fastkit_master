package com.example.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.base.BuildConfig;
import com.example.base.R;
import com.example.ui.activity.kit.BaseActivity;


import timber.log.Timber;

/**
 * 继承BaseActivity
 * 方便日后切换使用其他的通用Activity
 */
public abstract class BaseAppActivity extends BaseActivity {

    protected abstract void onCreateView();


    @Override
    protected void onDestroy() {
        super.onDestroy();


    }

    @Override
    protected int setStatusBarColor() {
        return R.color.colorPrimaryDark;
    }

}
