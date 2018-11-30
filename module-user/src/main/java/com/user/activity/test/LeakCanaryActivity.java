package com.user.activity.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.user.R;

public class LeakCanaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.login_activity);

        ActivityPool.getActivity().addActivity(this);
    }
}