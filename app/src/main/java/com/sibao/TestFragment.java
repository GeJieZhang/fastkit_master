package com.sibao;

import android.os.Bundle;
import android.view.View;

import com.example.ui.fragment.BaseAppFragment;

import butterknife.ButterKnife;


public class TestFragment extends BaseAppFragment {




    @Override
    protected void onCreateView(View view, Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_test;
    }



}
