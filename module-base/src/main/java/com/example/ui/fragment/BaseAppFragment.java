package com.example.ui.fragment;

import android.os.Bundle;
import android.view.View;

import com.example.ui.fragment.kit.BaseFragment;


public abstract class BaseAppFragment extends BaseFragment {


    protected abstract void onCreateView(View view, Bundle savedInstanceState);

    protected abstract int getLayoutId();

}
