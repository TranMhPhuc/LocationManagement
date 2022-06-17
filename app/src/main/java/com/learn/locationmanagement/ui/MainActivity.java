package com.learn.locationmanagement.ui;

import android.os.Bundle;

import com.learn.locationmanagement.databinding.ActivityMainBinding;
import com.learn.locationmanagement.ui.common.BaseActivity;

public class MainActivity extends BaseActivity<ActivityMainBinding> {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public ActivityMainBinding bindView() {
        return ActivityMainBinding.inflate(getLayoutInflater());
    }
}